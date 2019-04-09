
## Part IV. Spring Boot features

<https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#boot-features>

### 23. SpringApplication

The SpringApplication class provides a convenient way to bootstrap a Spring application that is started from a main() method. In many situations, you can delegate to the static SpringApplication.run method, as shown in the following example:  ->  SpringApplication类提供了一种方便的方法来引导从main()方法启动的Spring应用程序。在许多情况下，您可以委托静态SpringApplication.run方法，如以下示例所示：

```java
public static void main(String[] args) {
    SpringApplication.run(MySpringConfiguration.class, args);
}
```

### 23.1 Startup Failure

`org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener`

For instance, if you are running your application by using `java -jar`, you can enable the `debug` property as follows:

```sh
$ java -jar myproject-0.0.1-SNAPSHOT.jar --debug
```

### 23.2 Customizing the Banner

`springBootBanner`

```sh
spring.main.banner-mode=log
spring.main.banner-mode=off
```

### 23.5 Application Events and Listeners

Some events are actually triggered before the ApplicationContext is created, so you cannot register a listener on those as a @Bean. You can register them with the SpringApplication.addListeners(…​) method or the SpringApplicationBuilder.listeners(…​) method.  ->  有些事件实际上是在创建ApplicationContext之前触发的，因此您不能在这些事件上将侦听器注册为@Bean。您可以使用SpringApplication.addListener(…​)方法或SpringApplicationBuilder.Listener(…​)法。

If you want those listeners to be registered automatically, regardless of the way the application is created, you can add a META-INF/spring.factories file to your project and reference your listener(s) by using the org.springframework.context.ApplicationListener key, as shown in the following example:  ->  如果希望自动注册这些侦听器(无论应用程序是以何种方式创建的)，可以将META-INF/Spring.Factory文件添加到项目中，并使用org.springframework.context.ApplicationListener键引用侦听器，如以下示例所示：

```sh
org.springframework.context.ApplicationListener=com.example.project.MyListener
```

> You often need not use application events, but it can be handy to know that they exist. Internally, Spring Boot uses events to handle a variety of tasks.  ->  您通常不需要使用应用程序事件，但是知道它们的存在是很方便的。在内部，SpringBoot使用事件来处理各种任务。

Application events are sent by using Spring Framework’s event publishing mechanism. Part of this mechanism ensures that an event published to the listeners in a child context is also published to the listeners in any ancestor contexts. As a result of this, if your application uses a hierarchy of SpringApplication instances, a listener may receive multiple instances of the same type of application event.  ->  应用程序事件是通过使用SpringFramework的事件发布机制发送的。此机制的一部分确保在子上下文中向侦听器发布的事件也在任何祖先上下文中发布给侦听器。因此，如果应用程序使用SpringApplication实例的层次结构，则侦听器可能会接收相同类型的应用程序事件的多个实例。

To allow your listener to distinguish between an event for its context and an event for a descendant context, it should request that its application context is injected and then compare the injected context with the context of the event. The context can be injected by implementing ApplicationContextAware or, if the listener is a bean, by using @Autowired.  ->  要允许侦听器区分其上下文的事件和子代上下文的事件，它应该请求注入其应用程序上下文，然后将注入的上下文与事件的上下文进行比较。可以通过实现ApplicationContextAware注入上下文，如果侦听器是bean，则使用@Autowired。

### 23.6 Web Environment

Spring MVC : `AnnotationConfigServletWebServerApplicationContext`

WebFlux : `AnnotationConfigReactiveWebServerApplicationContext`

否则 : `AnnotationConfigApplicationContext`

> It is often desirable to call `setWebApplicationType(WebApplicationType.NONE)` when using `SpringApplication` within a JUnit test.

### 23.7 Accessing Application Arguments

这个还没觉察到其用武之地，但是直觉很有用。

### 23.8 Using the ApplicationRunner or CommandLineRunner

If you need to run some specific code once the `SpringApplication` has started, you can implement the `ApplicationRunner` or `CommandLineRunner` interfaces. Both interfaces work in the same way and offer a single run method, which is called just before `SpringApplication.run(…​)` completes.  ->  如果您需要在`SpringApplication`启动后运行某些特定的代码，则可以实现`ApplicationRunner`或`CommandLineRunner`接口。这两个接口的工作方式相同，并提供了一个Run方法，该方法在`SpringApplication.run(…)`之前调用。​)完成。

If several `CommandLineRunner` or `ApplicationRunner` beans are defined that must be called in a specific order, you can additionally implement the `org.springframework.core.Ordered` interface or use the `org.springframework.core.annotation.Order` annotation.  ->  如果定义了几个CommandLineRunner或ApplicationRunnerbean，并且必须按特定顺序调用这些bean，则可以另外实现org.Springframe work.core.Order接口或使用org.Springframe work.core.annotation.Order注释。


### 23.9 Application Exit

了解一下

### 24. Externalized Configuration

Spring Boot lets you externalize your configuration so that you can work with the same application code in different environments. You can use properties files, YAML files, environment variables, and command-line arguments to externalize configuration. Property values can be injected directly into your beans by using the `@Value` annotation, accessed through Spring’s Environment abstraction, or be bound to structured objects through `@ConfigurationProperties`.  ->   SpringBoot允许您将配置外部化，以便可以在不同的环境中使用相同的应用程序代码。您可以使用属性文件、YAML文件、环境变量和命令行参数来外部化配置。
属性值可以通过使用@Value注释直接注入到bean中，可以通过Spring的环境抽象进行访问，也可以通过@ConfigurationProperties绑定到结构化对象。


> 24节有8个小节,昨晚(2019年04月09日)睡不着,躺在床上用手机把这一节都看一遍了。

### 25. Profiles

Spring Profiles provide a way to segregate parts of your application configuration and make it be available only in certain environments. Any `@Component` or `@Configuration` can be marked with `@Profile` to limit when it is loaded, as shown in the following example:  ->  Spring配置文件提供了一种隔离应用程序配置的部分并使其仅在某些环境中可用的方法。任何@Component或@Configuration都可以标记为@profile，以便在加载时进行限制，如以下示例所示：

```java
@Configuration
@Profile("production")
public class ProductionConfiguration {
    // ...
}
```

可以在 `application.properties` 文件中指定

```sh
spring.profiles.active=dev,hsqldb
```

或者, command line

`--spring.profiles.active=dev,hsqldb`

### 25.1 Adding Active Profiles

`SpringApplication#setAdditionalProfiles()`

`spring.profiles.include`

`spring.profiles.active`

### 25.2 Programmatically Setting Profiles

`SpringApplication#setAdditionalProfiles()`

`ConfigurableEnvironment`

### 25.3 Profile-specific Configuration Files

> 参考 : 24.4 Profile-specific Properties

### 26. Logging

Spring Boot uses Commons Logging for all internal logging but leaves the underlying log implementation open. Default configurations are provided for Java Util Logging, Log4J2, and Logback. In each case, loggers are pre-configured to use console output with optional file output also available.  ->  Spring Boot对所有内部日志记录都使用CommonsLogging，但让底层日志实现保持打开状态。为Java Util日志记录、Log4J2和Logback提供了默认配置。在每种情况下，记录器都预先配置为使用控制台输出，还可以使用可选的文件输出。

By default, if you use the “Starters”, Logback is used for logging. Appropriate Logback routing is also included to ensure that dependent libraries that use Java Util Logging, Commons Logging, Log4J, or SLF4J all work correctly.  ->  默认情况下，如果使用“starters”，则Logback用于日志记录。
还包括适当的Logback路由，以确保使用JavaUtil日志、CommonLogging、Log4J或SLF4J的依赖库都能正常工作。

### 26.1 Log Format

这里解释了日志的每个部分各代表什么，对日志输出不理解时，可以前来复习。

### 26.2 Console Output

打开'debug'模式，可以在启动时指定 `--debug`

```sh
$ java -jar myapp.jar --debug
```

或者, You can also specify `debug=true` in your `application.properties`.

打开'debug'模式，并不会输出全部的`DEBUG`级别信息

`--trace` 或者 `trace=true` in your `application.properties` 才会输出完整的信息。


### 26.2.1 Color-coded Output

让不同级别的输出显示不同的颜色

### 26.3 File Output

默认情况下，springboot只输出日志到控制台。

可以在 `application.properties` 中指定 `logging.file` 或 `logging.path` 来让日志输出到文件。

Log files rotate when they reach 10 MB and, as with console output, ERROR-level, WARN-level, and INFO-level messages are logged by default. Size limits can be changed using the logging.file.max-size property. Previously rotated files are archived indefinitely unless the logging.file.max-history property has been set.  ->  日志文件在达到10MB时旋转，与控制台输出一样，默认情况下会记录错误级别、警告级别和信息级别的消息。可以使用logging.file.max-size属性更改大小限制。
除非设置了logging.file.max-History属性，否则以前旋转的文件将无限期归档。

> The logging system is initialized early in the application lifecycle. Consequently, logging properties are not found in property files loaded through @PropertySource annotations.  ->  日志记录系统在应用程序生命周期的早期被初始化。因此，在通过@PropertySource注释加载的属性文件中找不到日志记录属性。

> Logging properties are independent of the actual logging infrastructure. As a result, specific configuration keys (such as logback.configurationFile for Logback) are not managed by spring Boot.  ->  日志记录属性独立于实际的日志记录基础结构。因此，特定的配置键(如Logback的logback.ConfigurationFile)不是由SpringBoot管理的。

### 26.4 Log Levels

TRACE, DEBUG, INFO, WARN, ERROR, FATAL, or OFF. 

example

```sh
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```

### 26.5 Custom Log Configuration

The various logging systems can be activated by including the appropriate libraries on the classpath and can be further customized by providing a suitable configuration file in the root of the classpath or in a location specified by the following Spring `Environment` property: `logging.config`.  ->  可以通过在类路径中包含适当的库来激活各种日志系统，还可以通过在类路径的根目录中或在由以下Spring Environment属性指定的位置提供适当的配置文件来进一步定制这些系统：logging.config。

You can force Spring Boot to use a particular logging system by using the `org.springframework.boot.logging.LoggingSystem` system property. The value should be the fully qualified class name of a `LoggingSystem` implementation. You can also disable Spring Boot’s logging configuration entirely by using a value of `none`.  -> 可以使用org.springframework.boot.logging.LoggingSystem系统属性强制SpringBoot使用特定的日志记录系统。该值应该是LoggingSystem实现的完全限定类名。
您还可以完全禁用SpringBoot的日志配置，方法是使用None值。

> Since logging is initialized before the `ApplicationContext` is created, it is not possible to control logging from `@PropertySources` in Spring `@Configuration` files. The only way to change the logging system or disable it entirely is via System properties.  ->  由于日志记录是在创建ApplicationContext之前初始化的，因此无法从Spring@配置文件中的@PropertySources控制日志记录。更改或完全禁用日志记录系统的唯一方法是通过系统属性。

> When possible, we recommend that you use the `-spring` variants for your logging configuration (for example, `logback-spring.xml` rather than `logback.xml`). If you use standard configuration locations, Spring cannot completely control log initialization.  ->  如果可能，我们建议您在日志配置中使用-Spring变体(例如，Logback-Spring.xml而不是logback.xml)。
如果使用标准配置位置，则Spring不能完全控制日志初始化。

> There are known classloading issues with Java Util Logging that cause problems when running from an 'executable jar'. We recommend that you avoid it when running from an 'executable jar' if at all possible.  ->  Java Util日志记录中存在已知的类加载问题，这些问题在从“可执行JAR”运行时会导致问题。
如果可能，我们建议您在从“可执行JAR”运行时避免它。

### 26.6 Logback Extensions

Spring Boot includes a number of extensions to Logback that can help with advanced configuration. You can use these extensions in your `logback-spring.xml` configuration file.  ->  SpringBoot包括许多对Logback的扩展，这些扩展可以帮助进行高级配置。您可以在Logback-Spring.xml配置文件中使用这些扩展。

Because the standard `logback.xml` configuration file is loaded too early, you cannot use extensions in it. You need to either use `logback-spring.xml` or define a `logging.config` property.  ->  由于标准logback.xml配置文件加载得太早，因此不能在其中使用扩展名。您需要使用Logback-Spring.xml或定义logging.config属性。

> The extensions cannot be used with Logback’s configuration scanning. If you attempt to do so, making changes to the configuration file results in an error similar to one of the following being logged:  ->  扩展不能与Logback的配置扫描一起使用。如果尝试这样做，则对配置文件进行更改会导致类似于记录以下内容之一的错误：
>
> ERROR in ch.qos.logback.core.joran.spi.Interpreter@4:71 - no applicable action for [springProperty], current ElementPath is [[configuration][springProperty]]
>
> ERROR in ch.qos.logback.core.joran.spi.Interpreter@4:71 - no applicable action for [springProfile], current ElementPath is [[configuration][springProfile]]


### 26.6.1 Profile-specific Configuration

The `<springProfile>` tag lets you optionally include or exclude sections of configuration based on the active Spring profiles. Profile sections are supported anywhere within the `<configuration>` element. Use the name attribute to specify which profile accepts the configuration. Multiple profiles can be specified with a comma-separated list. The following listing shows three sample profiles:  ->  `<springProfile>`标记允许您根据活动的Spring配置文件有选择地包含或排除配置部分。配置文件部分在`<configuration>`元素中的任何位置都受支持。
使用name属性指定接受配置的配置文件。可以使用逗号分隔的列表指定多个配置文件。以下清单显示了三个示例配置文件：

```xml
<springProfile name="staging">
    <!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev, staging">
    <!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
    <!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
```

### 26.6.2 Environment Properties

The `<springProperty>` tag lets you expose properties from the Spring `Environment` for use within Logback. Doing so can be useful if you want to access values from your `application.properties` file in your Logback configuration. The tag works in a similar way to Logback’s standard `<property>` tag. However, rather than specifying a direct `value`, you specify the `source` of the property (from the `Environment`). If you need to store the property somewhere other than in `local` scope, you can use the `scope` attribute. If you need a fallback value (in case the property is not set in the `Environment`), you can use the `defaultValue` attribute. The following example shows how to expose properties for use within Logback:  ->  `<springproperty>`标记允许您从Spring环境中公开属性，以便在logback中使用。如果您想要从您的logback配置中的application.properties文件访问值，那么这样做很有用。标记的工作方式与logback的标准`<property>`标记类似。但是，不指定直接值，而是指定属性的源（来自环境）。如果需要将属性存储在本地作用域之外的其他地方，可以使用scope属性。如果需要回退值（在环境中未设置属性的情况下），可以使用defaultvalue属性。以下示例显示如何公开要在logback中使用的属性：

```xml
<springProperty scope="context" name="fluentHost" source="myapp.fluentd.host"
    defaultValue="localhost"/>
<appender name="FLUENT" class="ch.qos.logback.more.appenders.DataFluentAppender">
    <remoteHost>${fluentHost}</remoteHost>
    ...
</appender>
```

### 27. JSON

Gson, Jackson, JSON-B

Jackson is the preferred and default library.  ->  Jackson是首选的默认库。

#### Jackson

Auto-configuration for Jackson is provided and Jackson is part of `spring-boot-starter-json`. When Jackson is on the classpath an `ObjectMapper` bean is automatically configured. Several configuration properties are provided for customizing the configuration of the `ObjectMapper`.  ->  提供了Jackson的自动配置，Jackson是Spring-bootstarter-json的一部分。当Jackson在类路径上时，将自动配置`ObjectMapper`bean。为自定义ObjectMapper的配置提供了多个配置特性。


#### Gson

Auto-configuration for Gson is provided. When Gson is on the classpath a `Gson` bean is automatically configured. Several `spring.gson.*` configuration properties are provided for customizing the configuration. To take more control, one or more `GsonBuilderCustomizer` beans can be used.   ->  提供了gson的自动配置。当gson在类路径上时，将自动配置gsonbean。提供了几个用于自定义配置的Spring.gson.*配置属性。若要获得多个控件，可以使用一个或多个`GsonBuilderCustomizer` bean。

#### JSON-B

Auto-configuration for JSON-B is provided. When the JSON-B API and an implementation are on the classpath a `Jsonb` bean will be automatically configured. The preferred JSON-B implementation is Apache Johnzon for which dependency management is provided.  ->  提供了JSON-B的自动配置。当JSON-BAPI和实现位于类路径上时，将自动配置Jsonbbean。首选的JSON-B实现是ApacheJohnzon，它提供了依赖性管理。












