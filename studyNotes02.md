
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

















