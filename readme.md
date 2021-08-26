# Getting Started

容器环境启动：运行 src/main/java/com/sensorsdata/demo/DemoApplication.java 这个类。容器启动之后，可以访问controller里面的所有的接口

* 访问方式 http://localhost:8088/XXX
* XXX 对应 TestController 类中的 @GetMapping 内的值
* 表示 接口访问后端方法

非容器环境： 运行 test 目录下 对象的Java 文件 HelloSensors 是同步模式调用 sa 生成日志; 
SensorsDemo 是异步模式调用 sa 生成日志； 提供 SensorsLogsUtil/SensorsLogUtil 两种工具类。

ab 试验 如果要修改 ab 试验，在 src/main/java/com/sensorsdata/demo/config/SensorsConfig.java 这个类中 找到 initSensorsABTest 方法，替换里面的
url即可。 