package com.domain.food.dao.core;

import com.domain.food.config.ConfigProperties;
import com.domain.food.consts.Constant;
import com.domain.food.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 抽象容器
 *
 * @param K     主键类型
 * @param E 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public abstract class AbstractContainer<K, E> implements Closeable, InitializingBean, ApplicationContextAware {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private ConfigProperties properties;

    // 任务调度
    protected ScheduledExecutorService executor = null;
    // 创建同步 list 的函数
    protected Function<LocalDate, List<E>> createListFunction = k -> Collections.synchronizedList(new ArrayList<>());

    // 用于存储所有持久化的文件名
    protected static Object EMPTY_OBJECT = new Object();
    protected static Map<String, Object> fileNameSet = new ConcurrentHashMap<>();

    protected Map<LocalDate, String> timeToPathCache = new ConcurrentHashMap<>();
    // 存在Id的缓存
    protected Map<LocalDate, Map<K, E>> cacheWithId = new ConcurrentHashMap<>();
    // 不存在id的缓存
    protected Map<LocalDate, List<E>> cacheWithoutId = new ConcurrentHashMap<>();

    /**
     * 获取实体管理器
     */
    protected abstract EntityHolder<K, E> getEntityHolder();

    /**
     * 解析实体
     */
    protected abstract void analyseEntity();

    /**
     * 获取id类型
     *
     * @return
     */
    protected abstract Class<K> getIdClass();

    /**
     * 获取实体类型
     *
     * @return
     */
    protected abstract Class<E> getEntityClass();

    @Override
    public void afterPropertiesSet() throws Exception {
        // 处理实体
        analyseEntity();
        // 读取已存在的数据，仅读取当天
        loadDataFromDisk(LocalDate.now());

        // 启动定时器，定时刷库
        executor = Executors.newScheduledThreadPool(1);
        executor.schedule(() -> {
            try {
                writeDataToDisk();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }, properties.getDb().getInterval(), TimeUnit.SECONDS);
    }


    /**
     * 从磁盘加载指定日期的数据
     */
    protected void loadDataFromDisk(LocalDate localDate) {
        // 加载数据
        String filePathAtDisk = getFilePathAtDisk(localDate);

        synchronized (localDate) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePathAtDisk)));
                String line = null;
                while (!StringUtil.isBlank(line = br.readLine())) {
                    E entity = convertStringToEntity(line);
                    addToMap(localDate, entity);
                }
            } catch (FileNotFoundException ignore) {
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                IoUtil.close(br);
            }
        }
    }

    /**
     * 将处理好的实体添加到map中
     */
    private void addToMap(LocalDate localDate, E entity) throws IllegalAccessException {
        if (!ObjectUtil.isNull(entity)) {
            if (getEntityHolder().isIdExists()) {
                addToWithIdMap(localDate, entity);
            } else {
                addToWithoutIdMap(localDate, entity);
            }
        }
    }

    /**
     * 将实体添加到存在主键的缓存中
     */
    @SuppressWarnings("unchecked")
    private void addToWithIdMap(LocalDate localDate, E entity) throws IllegalAccessException {
        K id = (K) getEntityHolder().getIdField().get(entity);
        Map<K, E> map = cacheWithId.get(localDate);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            cacheWithId.put(localDate, map);
        }
        map.put(id, entity);
    }

    /**
     * 将实体添加到不存在主键的缓存中
     */
    private void addToWithoutIdMap(LocalDate localDate, E entity) {
        List<E> list = cacheWithoutId.computeIfAbsent(localDate, createListFunction);
        list.add(entity);
    }

    /**
     * 将字符串转换为实体
     */
    private E convertStringToEntity(String line) {
        E entity = null;
        try {
            entity = JsonUtil.fromJson(line, getEntityClass());
        } catch (Exception ignore) {
        }
        return entity;
    }

    /**
     * 生成指定日期存储数据的持久化文件名称
     */
    private String getFilePathAtDisk(LocalDate localDate) {
        String retPath = null;
        if (!StringUtil.isBlank(retPath = timeToPathCache.get(localDate))) {
            return retPath;
        }
        String formatDate = DateUtil.formatDate(localDate, DateUtil.FORMAT_DATA_SIMPLE);
        String persistFileName = getEntityHolder().getEntityAnnotation().name();
        String filePath = properties.getDb().getFilePath(formatDate + Constant.DEFAULT_LINE_CENTER + persistFileName);
        timeToPathCache.put(localDate, filePath);
        return filePath;
    }

    /**
     * 将数据写出到磁盘
     */
    protected void writeDataToDisk() throws IOException {
        Map<LocalDate, List<E>> writeMap = new HashMap<>();

        LocalDate now = LocalDate.now();

        // 处理不存在id的实体
        Iterator<LocalDate> withoutIdKey = cacheWithoutId.keySet().iterator();
        while (withoutIdKey.hasNext()) {
            LocalDate localDate = withoutIdKey.next();
            List<E> list = writeMap.computeIfAbsent(localDate, createListFunction);
            list.addAll(cacheWithoutId.get(localDate));
            if (!localDate.equals(now)) {
                withoutIdKey.remove();
            }
        }

        // 处理存在id的实体
        Iterator<LocalDate> withIdKey = cacheWithId.keySet().iterator();
        while (withIdKey.hasNext()) {
            LocalDate localDate = withIdKey.next();
            Map<K, E> map = cacheWithId.get(localDate);
            List<E> list = writeMap.computeIfAbsent(localDate, createListFunction);
            list.addAll(map.values());
            if (!localDate.equals(now)) {
                withIdKey.remove();
            }
        }

        // 写出到磁盘
        entityToDisk(writeMap);
    }

    /**
     * 将实体写出到磁盘
     */
    private void entityToDisk(Map<LocalDate, List<E>> map) throws IOException {
        Set<LocalDate> container = new HashSet<>();
        for (LocalDate localDate : map.keySet()) {
            String filePathAtDisk = getFilePathAtDisk(localDate).concat(Constant.DEFAULT_SUFFIX_JSON);
            if (!container.contains(localDate)) {
                // 删除文件
                IoUtil.delete(filePathAtDisk);
            }
            container.add(localDate);

            // 写出文件
            List<E> list = map.get(localDate);
            try {
                writeListToDisk(filePathAtDisk, list);
            } catch (FileNotFoundException e) {
                IoUtil.createFile(filePathAtDisk);
                writeListToDisk(filePathAtDisk, list);
            }
        }
    }

    /**
     * 将处理好的实体列表，写出到磁盘
     */
    private void writeListToDisk(String file, List<E> list) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(file), Charset.forName(Constant.DEFAULT_CHARSET)))) {
            list.forEach(e -> pw.println(JsonUtil.toJson(e)));
        }
    }

    @Override
    public void close() throws IOException {
        writeDataToDisk();
        cacheWithId.clear();
        cacheWithoutId.clear();
        executor.shutdownNow();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        properties = applicationContext.getBean(ConfigProperties.class);
        Assert.notNull(properties, "配置不能不能为空, type[" + ConfigProperties.class.getName() + "]");
    }
}
