# 简单的订餐系统
通过现有技术，实现一个简单的在线订餐系统，用于解决公司内部订餐仍然人工统计的问题。

## 技术选型
* 项目基于Maven构建
* 主要技术使用SpringBoot
* 持久化使用自定义json文件。

## 前端踩坑记录

1、 数组操作
```js
// 定义数组
var arr = [];
// 向数组添加元素
arr[0] = '第一个数据';
arr.push('第二个数据');
// 数组访问迭代1
for(var i = 0; i < arr.length; i++) {
    var ele = arr[i];
}
// 数组访问2
for(var i in arr) {
    var  ele = arr[i];
}
// 检查数组是否存在 indexOf(e) 返回 -1 不存在
arr.indexOf('检查该元素是否存在，存在返回下标，不存在返回-1')
```
2、键值对类型的数组操作
```js
var arr = [];
// put
arr['k1'] = 'v1';
// get
var v = arr['k1'];
```
3、字符串转数字
```js
var str = '12365412';
// 整形转换
var intNum = parseInt(str);
// 浮点型转换
var floatNum = parseFloat(str);
```
4、日期类型操作
```js
var num = 12545663332;
// 创建日期类型
var date = new Date(num);
// 获取日期对象的值
date.getFullYear();  // 获取完整的年份(4位,1970)
date.getMonth();  // 获取月份(0-11,0代表1月,用的时候记得加上1)
date.getDate();  // 获取日(1-31)
date.getTime();  // 获取时间(从1970.1.1开始的毫秒数)
date.getHours();  // 获取小时数(0-23)
date.getMinutes();  // 获取分钟数(0-59)
date.getSeconds();  // 获取秒数(0-59)
```

## SpringBoot 踩坑记录
1、Swagger配置指定扫描包

参考`Application`类的配置

2、自定义错误页面即错误处理

参考`GlobalErrorExceptionHandler`类的配置

3、自定义MVC参数解析器

参考`UserArgumentResolver`类的实现

4、自定义拦截器

参考`UserMvcInterceptor`类的实现

5、取消SpringBoot默认配置的MVC

参考`org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration`类

该类中提到可以提供`org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport`类的实现来终止SpringBoot的自动配置

6、自定义jackson

参考`JsonUtil`类的实现，该类配置了禁止序列化`null`值，以及对指定对象的序列化配置

7、配置资源映射、拦截器、参数处理器、MVC序列化

参考`WebMvcConfigurer`类，该类继承自`org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport`，并将当前对象交于Spring管理，从而终止了SpringBoot对SpringMVC的默认配置。

同时，此类中也对资源映射、拦截器、参数解析器、自定义序列化进行了配置。

**注意**配置拦截器的拦截规则时，需要主要配置的拦截规则和资源映射的规则不能冲突。否则资源映射的规则会无效。

8、使用自定义配置文件属性

如果需要使用自定义配置文件，可以通过指定Bean上添加`@ConfigurationProperties()`注解，同时在任意一个使用了`@Configuration`注解的类上，
添加`@EnableConfigurationProperties()`注解，使当前配置的bean生效。如果需要在`application.yml`文件中书写时有提示，则需要在`pom.xml`文件中添加如下配置：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

## 数据持久层

1、数据持久层使用了`JPA`规范，但也只是简单的使用了几个注解

2、数据存储格式使用JSON字符串来存储。

3、数据通过指定过期时间和定时刷库的方式，来操作缓存和持久化数据

4、数据持久层核心代码在`com.domain.food.core`包下。

5、`com.domain.food.core.help`包中的`EntityBeanAnalyser`实现了对实体对象的解析。

6、`com.domain.food.core.help`包中的`AbstractContainer`实现了缓存管理及定时刷库策略

7、`com.domain.food.core`包中的`AbstractDao`提供了一些操作实体的简单方法

## 命令行监听器

1、`com.domain.food.core.listener`包主要用于监听命令行参数以及部分监听器的实现。

2、`CommandLineListener`实现了对命令行的监听，用于分发命令到监听器

3、`ICommandLineProcessor`为所有监听器的父接口，监听器需要实现该接口中的方法，同时需要将自身注册到Spring IOC中。当有指定的命令获取到以后，该监听器的方法会被调用。

4、`DaoCommandLineProcessor`和`ApplicationExitCommandProcessor`为监听器的实现类。

## 项目发布

1、`Nginx`配置
```
// 配置反向代理
location / {
    proxy_pass http://127.0.0.1:8080;
}
// 配置静态资源转发

location ~* \.(htm|html|gif|jpg|jpeg|png|css|js|icon)$ {
    root  /opt/res/web/;
    autoindex off;
}
```

2、静态资源拷贝
```
将html和static文件夹下的内容拷贝到 /opt/res/web/ (注：该目录为nginx静态资源代理目录，可在nginx配置文件中修改) 目录下。
```

3、js配置
```js
// 配置静态资源的请求路径
// 配置 /opt/res/web/js/base.js
var assets = '/image';
var default_image_url = '/default.jpg';
```

4、java程序配置
```yml
// 修改application.yml, 该yml文件位于jar包以外的目录
# 业务数据配置
business:
  web:
    showStack: false
    imagePath: file:/opt/res/web/image   // 修改为nginx静态资源的目录，这里修改为 /opt/res/web/image
  db:
    interval: 5
    expireTime: 10
    path: file:/opt/res/food/db   // 修改为个人数据文件的存放地址
```

5、启动应用并指定配置文件的路径
```shell
java -jar //自己jar包的位置 --spring.config.location=//个人外置的application.yml文件的位置
```