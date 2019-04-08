

### resource.delimiter

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-maven>

> Note that, since the `application.properties` and `application.yml` files
> accept Spring style placeholders (`${…​}`),
> the Maven filtering is changed to use `@..@` placeholders.
> (You can override that by setting a Maven property called `resource.delimiter`.)

### 在springboot中指定jar包版本

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-maven-parent-pom>
- 使用了springboot后，我们就不用指定依赖jar包的版本了。
- 但是，如果你需要使用某个版本的jar呢？

```xml
<properties>
    <spring-data-releasetrain.version>Fowler-SR2</spring-data-releasetrain.version>
</properties>
```

- 如上，可以在 `properties` 中修改version

### 13.2.2 Using Spring Boot without the Parent POM

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-maven-without-a-parent>
- 使用 Maven `import` , 具体操作参考上面的链接。

### 13.2.3 Using the Spring Boot Maven Plugin

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-maven-plugin>
- 使用springboot提供的 Maven plugin 可以生成可执行jar包，方便部署。

### 13.5 Starters

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-starter>
- Starters are a set of convenient dependency descriptors that you can include in your application. You get a one-stop shop for all the Spring and related technologies that you need without having to hunt through sample code and copy-paste loads of dependency descriptors. For example, if you want to get started using Spring and JPA for database access, include the `spring-boot-starter-data-jpa` dependency in your project.
- 官方定义标准  : `spring-boot-starter-*`
- 第三方定义标准 : `thirdpartyproject-spring-boot-starter`
- <https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-starters/README.adoc>
    - 除了文档中描述的starters以外，以上链接中还列举了其他的starters

### 15. Configuration Classes

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-configuration-classes>
- `@Configuration`
    - 一般在最外层的定义`main`方法的类上添加`@Configuration`注解
- `@Configuration` 可以定义到多个类上，
    - 可以使用 `@Import` 导入其他类中的configuration
    - 或者使用 `@ComponentScan` 自动收集所有的 components，包含标准为 `@Configuration` 的类。
- XML 配置也可以通过 `@ImportResource` 导入

### 16. Auto-configuration

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-auto-configuration>
- You should only ever add one `@SpringBootApplication` or `@EnableAutoConfiguration` annotation. We generally recommend that you add one or the other to your primary `@Configuration` class only.
    - 以上两个注解最好只标注在包含`@Configuration`的类上(primary `@Configuration` class only)。

```java
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MyConfiguration {
}
```

- 可以向上面那样将某个自动配置排除(disable) ,
- 如果类不包含在classpath中，可以使用`excludeName`属性，值是类的完全限定名，
- 还可以使用 `spring.autoconfigure.exclude` property 来达到同样的目的。

### 18. Using the @SpringBootApplication Annotation

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-using-springbootapplication-annotation>
- `@SpringBootApplication` 等于以下三个注解
    - `@EnableAutoConfiguration`
    - `@ComponentScan`
    - `@Configuration`
- 也可以不使用 `@ComponentScan` , 如下：

```java
@Configuration
@EnableAutoConfiguration
@Import({ MyConfig.class, MyAnotherConfig.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### remote debugging support

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-running-as-a-packaged-application>

```sh
$ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n \
      -jar target/myapplication-0.0.1-SNAPSHOT.jar
```

### 19.3 Using the Maven Plugin

```sh
$ mvn spring-boot:run

$ export MAVEN_OPTS=-Xmx1024m
```

### 20. Developer Tools

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-devtools>

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 20.1 Property Defaults

> 默认关闭缓存

- Several of the libraries supported by Spring Boot use caches to improve performance. For example, template engines cache compiled templates to avoid repeatedly parsing template files. Also, Spring MVC can add HTTP caching headers to responses when serving static resources.
    - SpringBoot支持的几个库使用缓存来提高性能。例如，模板引擎缓存编译的模板，以避免重复解析模板文件。此外，SpringMVC可以在服务静态资源时向响应添加HTTP缓存头。
- While caching is very beneficial in production, it can be counter-productive during development, preventing you from seeing the changes you just made in your application. For this reason, spring-boot-devtools disables the caching options by default.
    - 虽然缓存在生产中非常有用，但它在开发过程中可能会适得其反，使您无法看到刚刚在应用程序中所做的更改。因此，默认情况下，Spring-bootDevTools禁用缓存选项。
- Cache options are usually configured by settings in your `application.properties` file. For example, Thymeleaf offers the spring.thymeleaf.cache property. Rather than needing to set these properties manually, the `spring-boot-devtools` module automatically applies sensible development-time configuration.
    - 缓存选项通常由application.properties文件中的设置进行配置。例如，Thymeleaf提供了Spring.thyeleaf.cache属性。Spring-bootDevTools模块不需要手动设置这些属性，而是自动应用合理的开发时配置。
- For a complete list of the properties that are applied by the devtools, see  [DevToolsPropertyDefaultsPostProcessor.java](https://github.com/spring-projects/spring-boot/blob/v2.0.9.RELEASE/spring-boot-project/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java).


### 20.2 Automatic Restart

> 这一小节有很多注意点


- DevTools relies on the application context’s shutdown hook to close it during a restart. It does not work correctly if you have disabled the shutdown hook (`SpringApplication.setRegisterShutdownHook(false)`).
    - DevTools依赖应用程序上下文的关闭钩子在重新启动期间关闭它。如果禁用了关机挂钩(SpringApplication.setRegisterShutdownHook(false)).，则不能正常工作。
- DevTools needs to customize the `ResourceLoader` used by the `ApplicationContext`. If your application provides one already, it is going to be wrapped. Direct override of the `getResource` method on the `ApplicationContext` is not supported.
    - DevTools需要自定义ApplicationContext使用的ResourceLoader。如果您的应用程序已经提供了一个，那么它将被包装。不支持直接重写ApplicationContext上的getResource方法。

> **Restart vs Reload**
>
> The restart technology provided by Spring Boot works by using two classloaders. Classes that do not change (for example, those from third-party jars) are loaded into a base classloader. Classes that you are actively developing are loaded into a restart classloader. When the application is restarted, the restart classloader is thrown away and a new one is created. This approach means that application restarts are typically much faster than “cold starts”, since the base classloader is already available and populated.  ->  SpringBoot提供的重新启动技术通过使用两个类加载器来工作。不更改的类(例如，来自第三方JAR的类)被加载到基类加载器中。正在积极开发的类将加载到重新启动类加载器中。当应用程序重新启动时，将丢弃重新启动的类加载器，并创建一个新的类加载器。这种方法意味着应用程序重新启动通常比“冷启动”要快得多，因为基类加载器已经可用并已填充。
> 
> If you find that restarts are not quick enough for your applications or you encounter classloading issues, you could consider reloading technologies such as JRebel from ZeroTurnaround. These work by rewriting classes as they are loaded to make them more amenable to reloading.  ->  如果您发现重新启动对您的应用程序来说不够快，或者您遇到了类加载问题，您可以考虑从ZeroTurnWith重新加载JRebel之类的技术。这些方法是在类被加载时重写它们，以使它们更易于重新加载。
















