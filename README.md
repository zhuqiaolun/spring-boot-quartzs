# spring-boot-quartzs

### 初始化
初始机制采取项目启动初始化Quartz表结构，同步任务表到Quartz表
由 spring.quartz.jdbc.initialize-schema=always/never
配置为 always 则表示 格式化同步

### 日志
采取 logback-spring.xml

### swagger
采取静态页面加载 json 文件显示
路径：resources/static/swagger-ui/api/quartz.json
访问：http://localhost:8055/spring-boot-quartz/swagger-ui.html

### 任务执行
采用反射机制 在类 MyQuartzJob 执行,默认方法名：execute，默认参数：Map
```html
//反射 - 执行类
Class<?> cls = Class.forName(jobDataMap.getString("classPath"));
Method execute = cls.getMethod("execute", Map.class);
execute.invoke(cls.newInstance(), jobDataMap.getWrappedMap());
```
### 任务列表
quartz-task-hello：测试任务

