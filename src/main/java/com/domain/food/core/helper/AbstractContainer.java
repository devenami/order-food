package com.domain.food.core.helper;

import com.domain.food.config.ConfigProperties;
import com.domain.food.consts.Constant;
import com.domain.food.utils.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 抽象容器
 *
 * @param <K> 主键类型
 * @param <E> 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public abstract class AbstractContainer<K, E> extends EntityBeanAnalyser<K, E> implements Closeable, ApplicationContextAware {

    private ConfigProperties properties;

    // 上次从磁盘加载数据的时间 -- 必须
    private volatile AtomicLong lastLoadDataFromDiskTime = new AtomicLong(0);

    // 任务调度
    protected ScheduledExecutorService executor = null;

    // 数据持久化缓存
    private Map<K, EntityWrap<E>> entityCacheMap = new ConcurrentHashMap<>();

    // 实体操作类型分组
    private final Map<EntityWrap.Type, List<E>> entityGroupMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        // 初始化分组集合
        initEntityGroupMap();
        // 读取已存在的数据，仅读取当天
        loadDataFromDisk();

        // 启动定时器，定时刷库
        executor = Executors.newScheduledThreadPool(1);

        int interval = properties.getDb().getInterval();
        log.debug("启动定时器, 循环时间：" + interval + "秒/次");

        executor.scheduleWithFixedDelay(() -> {
            try {
                writeDataToDisk();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }, interval, interval, TimeUnit.MINUTES);
    }

    /**
     * 初始化分组集合
     */
    private synchronized void initEntityGroupMap() {
        log.debug("正在初始化分组集合");
        entityGroupMap.put(EntityWrap.Type.DEFAULT, Collections.synchronizedList(new ArrayList<>()));
        entityGroupMap.put(EntityWrap.Type.ADD, Collections.synchronizedList(new ArrayList<>()));
        entityGroupMap.put(EntityWrap.Type.UPDATE, Collections.synchronizedList(new ArrayList<>()));
        entityGroupMap.put(EntityWrap.Type.DELETE, Collections.synchronizedList(new ArrayList<>()));
    }

    /**
     * 重置分组集合
     */
    private synchronized void resetEntityGroupMap() {
        entityGroupMap.get(EntityWrap.Type.DEFAULT).clear();
        entityGroupMap.get(EntityWrap.Type.ADD).clear();
        entityGroupMap.get(EntityWrap.Type.UPDATE).clear();
        entityGroupMap.get(EntityWrap.Type.DELETE).clear();
    }

    /**
     * 从磁盘加载指定日期的数据
     */
    protected void loadDataFromDisk() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastLoadDataFromDiskTime.get() < properties.getDb().getExpireTime()) {
            return;
        }
        lastLoadDataFromDiskTime.set(currentTimeMillis);

        // 加载数据
        String filePathAtDisk = getDiskFilePath();

        log.debug("获取文件数据, [path : " + filePathAtDisk + "]");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePathAtDisk)));
            String line;
            while (!StringUtil.isBlank(line = br.readLine())) {
                E entity = convertStringToEntity(line);
                addToEntityCacheMap(entity);
            }
        } catch (FileNotFoundException ignore) {
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IoUtil.close(br);
        }
    }

    /**
     * 将处理好的实体添加到map中
     */
    private void addToEntityCacheMap(E entity) {
        if (!ObjectUtil.isNull(entity)) {
            EntityWrap<E> entityWrap = new EntityWrap<>();
            entityWrap.setExpireTime(getExpireTime());
            entityWrap.setEntity(entity);
            entityWrap.setType(EntityWrap.Type.DEFAULT);
            getEntityCacheMap().put(getIdValue(entity), entityWrap);
        }
    }

    /**
     * 获取缓存的过期时间
     */
    protected long getExpireTime() {
        int expireTime = properties.getDb().getExpireTime();
        return System.currentTimeMillis() + expireTime * 60 * 1000;
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
    private String getDiskFilePath() {
        String persistFileName = getEntityHolder().getEntityAnnotation().name();
        return properties.getDb().getFilePath(persistFileName.concat(Constant.DEFAULT_SUFFIX_JSON));
    }

    /**
     * 将数据写出到磁盘
     */
    protected void writeDataToDisk() throws IOException {
        log.debug("写出数据到磁盘……");

        long time = System.currentTimeMillis();

        Iterator<EntityWrap<E>> entityWraps = getEntityCacheMap().values().iterator();
        while (entityWraps.hasNext()) {
            EntityWrap<E> entityWrap = entityWraps.next();

            // 处理操作类型
            EntityWrap.Type type = entityWrap.getType();
            E entity = entityWrap.getEntity();
            entityGroupMap.get(type).add(entity);
            // 将实体状态置为正常
            entityWrap.setType(EntityWrap.Type.DEFAULT);

            // 处理过期时间
            long expireTime = entityWrap.getExpireTime();
            if (expireTime < time) {
                entityWraps.remove();
            }
        }

        // 处理分组集合
        // 这里的顺序一定不能反
        removeFromDisk();
        appendToDisk();

        // 重置分组器
        resetEntityGroupMap();
    }

    /**
     * 追加资源到文件
     */
    private void appendToDisk() throws IOException {
        List<E> update = entityGroupMap.get(EntityWrap.Type.UPDATE);
        List<E> add = entityGroupMap.get(EntityWrap.Type.ADD);
        add.addAll(update);
        if (add.isEmpty()) {
            return;
        }
        String filepath = getDiskFilePath();
        try {
            appendFile(filepath, add);
        } catch (FileNotFoundException e) {
            log.debug("文件[" + filepath + "]未找到, 创建新文件后重试");
            IoUtil.createFile(filepath);
            appendFile(filepath, add);
        }
    }

    /**
     * 将处理好的实体列表，写出到磁盘
     */
    private synchronized void appendFile(String file, List<E> list) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(file, true), Charset.forName(Constant.DEFAULT_CHARSET)))) {
            list.forEach(e -> pw.println(JsonUtil.toJson(e)));
        }
    }

    /**
     * 从文件中移除
     */
    private void removeFromDisk() throws IOException {
        List<E> update = entityGroupMap.get(EntityWrap.Type.UPDATE);
        List<E> delete = entityGroupMap.get(EntityWrap.Type.DELETE);
        if (!update.isEmpty()) {
            delete.addAll(update);
        }
        if (!delete.isEmpty()) {
            copyFile(getDiskFilePath(), delete);
        }
    }

    @SuppressWarnings("unchecked")
    private synchronized void copyFile(String filepath, List<E> list) throws IOException {
        Set<K> idSet = list.stream()
                .map(this::getIdValue)
                .collect(Collectors.toSet());
        String tmpFile = filepath.concat(".tmp");
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(tmpFile, true), Charset.forName(Constant.DEFAULT_CHARSET)));
             BufferedReader br = new BufferedReader(new InputStreamReader(
                     new FileInputStream(filepath), Charset.forName(Constant.DEFAULT_CHARSET)))) {
            String line;
            while (!StringUtil.isBlank(line = br.readLine())) {
                E entity = JsonUtil.fromJson(line, getEntityHolder().getEntityClazz());
                K id = getIdValue(entity);
                // 写出所有未被删除的记录
                if (!idSet.contains(id)) {
                    pw.println(line);
                }
            }
        }
        // 删除原文件，并将临时文件替换成原文件
        IoUtil.delete(filepath);
        IoUtil.rename(tmpFile, filepath);
    }

    /**
     * 获取id的值
     *
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    protected K getIdValue(E entity) {
        Object id = ReflectUtil.get(getEntityHolder().getIdField(), entity);
        Assert.notNull(id, "主键不能为null");
        return (K) id;
    }

    @Override
    public void close() throws IOException {

        log.debug("开始持久化数据,将内存数据写入磁盘……");

        writeDataToDisk();

        log.debug("内存数据持久化成功, 清空缓存……");
        entityCacheMap.clear();
        executor.shutdownNow();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        properties = applicationContext.getBean(ConfigProperties.class);
        Assert.notNull(properties, "配置不能不能为空, type[" + ConfigProperties.class.getName() + "]");
    }

    /**
     * 获取缓存数据
     */
    public Map<K, EntityWrap<E>> getEntityCacheMap() {
        return entityCacheMap;
    }

}
