
### 28. Developing Web Applications

Spring Boot is well suited for web application development. You can create a self-contained HTTP server by using embedded Tomcat, Jetty, Undertow, or Netty. Most web applications use the spring-boot-starter-web module to get up and running quickly. You can also choose to build reactive web applications by using the spring-boot-starter-webflux module.  ->  SpringBoot非常适合Web应用程序开发。您可以使用嵌入式Tomcat、Jetty、Undertow或Netty创建自包含的HTTP服务器。大多数Web应用程序都使用Spring-bootstarter-web模块来快速启动和运行。
您还可以选择使用Spring-Boot-Starter-WebFlux模块来构建反应性Web应用程序。

### 28.1 The “Spring Web MVC Framework”

### 28.1.1 Spring MVC Auto-configuration

### 28.1.2 HttpMessageConverters

`HttpMessageConverter`

JSON (by using the Jackson library) 

or XML (by using the Jackson XML extension, 

By default, strings are encoded in `UTF-8`.

```java
@Configuration
public class MyConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = ...
        HttpMessageConverter<?> another = ...
        return new HttpMessageConverters(additional, another);
    }

}
```

> 这有点像 Netty 中的 handler 添加到 pipeline 中的场景。

### 28.1.3 Custom JSON Serializers and Deserializers

> 这也有点像 Netty 的 encoder 和 decoder

If you use Jackson to serialize and deserialize JSON data, you might want to write your own `JsonSerializer` and `JsonDeserializer` classes. Custom serializers are usually registered with Jackson through a module, but Spring Boot provides an alternative `@JsonComponent` annotation that makes it easier to directly register Spring Beans.  ->  如果使用Jackson对JSON数据进行序列化和反序列化，则可能需要编写自己的JsonSeriizer和JsonDeseriizer类。
定制序列化程序通常通过模块向Jackson注册，但SpringBoot提供了一个替代`@JsonComponent`注释，使直接注册SpringBeans变得更容易。

You can use the @JsonComponent annotation directly on JsonSerializer or JsonDeserializer implementations. You can also use it on classes that contain serializers/deserializers as inner classes, as shown in the following example:  ->  您可以直接在JsonSeriizer或JsonDeseriizer实现上使用@JsonComponent注释。还可以在包含序列化程序/反序列化程序的类中将其用作内部类，如以下示例所示：

```java
@JsonComponent
public class Example {

    public static class Serializer extends JsonSerializer<SomeObject> {
        // ...
    }

    public static class Deserializer extends JsonDeserializer<SomeObject> {
        // ...
    }

}
```

All `@JsonComponent` beans in the `ApplicationContext` are automatically registered with Jackson. Because `@JsonComponent` is meta-annotated with `@Component`, the usual component-scanning rules apply.  ->  `ApplicationContext`中的所有`@JsonComponent` bean都会自动向Jackson注册。因为`@JsonComponent`是用`@Component`进行元注释的，所以应用通常的组件扫描规则。

还有 `JsonObjectSerializer` and `JsonObjectDeserializer` 

### 28.1.4 MessageCodesResolver

Spring MVC has a strategy for generating error codes for rendering error messages from binding errors: MessageCodesResolver. If you set the spring.mvc.message-codes-resolver.format property PREFIX_ERROR_CODE or POSTFIX_ERROR_CODE, Spring Boot creates one for you (see the enumeration in DefaultMessageCodesResolver.Format).  ->  SpringMVC有一个从绑定错误生成错误代码以呈现错误消息的策略：MessageCodesResolver。如果设置Spring.mvc.message-code-oluver.format属性PREFIX_ERROR_CODE或Postfix_ERROR_CODE，则Spring Boot将为您创建一个(请参见DefaultMessageCodesResolver.Format中的枚举)。

### 28.1.5 Static Content

`/static`, `/public`, `/resources`, `/META-INF/resources` :  这几个目录是springboot默认的加载路径。

use `ResourceHttpRequestHandler` from Spring MVC

可以添加自己的 `WebMvcConfigurer` and overriding the `addResourceHandlers` method 来修改默认行为。

By default, resources are mapped on `/**`, 可以通过 `spring.mvc.static-path-pattern` 修改, 比如修改为 `/resources/**` :

```sh
spring.mvc.static-path-pattern=/resources/**
```

You can also customize the static resource locations by using the `spring.resources.static-locations` property

[Webjars content](https://www.webjars.org/) : 这个Webjars第一次见到，点击链接可以跳转到主页<https://www.webjars.org/> 。 Any resources with a path in `/webjars/**` are served from jar files if they are packaged in the Webjars format.

> Do not use the `src/main/webapp` directory if your application is packaged as a jar. Although this directory is a common standard, it works **only** with war packaging, and it is silently ignored by most build tools if you generate a jar.  -> 打包成jar时，不要使用`src/main/webapp`, 这个目录只能在war包中使用。

To use version agnostic URLs for Webjars, add the `webjars-locator-core` dependency. Then declare your Webjar. Using jQuery as an example, adding `"/webjars/jquery/jquery.min.js"` results in `"/webjars/jquery/x.y.z/jquery.min.js"`. where `x.y.z` is the Webjar version.

> JBoss 中要使用 `webjars-locator-jboss-vfs` 来替换 `webjars-locator-core` , 否则`404`

> Links to resources are rewritten in templates at runtime, thanks to a `ResourceUrlEncodingFilter` that is auto-configured for Thymeleaf and FreeMarker. You should manually declare this filter when using JSPs. Other template engines are currently not automatically supported but can be with custom template macros/helpers and the use of the `ResourceUrlProvider`.  ->  通过为Thymeleaf和FreeMarker自动配置的`ResourceUrlEncodingFilter`，可以在运行时在模板中重写指向资源的链接。您应该在使用JSP时手动声明此筛选器。当前不自动支持其他模板引擎，但可以使用自定义模板宏/帮助程序和使用`ResourceUrlProvider`。

When loading resources dynamically with, for example, a JavaScript module loader, renaming files is not an option. That is why other strategies are also supported and can be combined. A "fixed" strategy adds a static version string in the URL without changing the file name, as shown in the following example:  -> 例如，使用JavaScript模块加载器动态加载资源时，不能重命名文件。这就是为什么其他战略也得到支持并可以结合起来的原因。“固定”策略在URL中添加静态版本字符串，而不更改文件名，如以下示例所示：

```sh
spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**
spring.resources.chain.strategy.fixed.enabled=true
spring.resources.chain.strategy.fixed.paths=/js/lib/
spring.resources.chain.strategy.fixed.version=v12
```

> 2019年04月09日22:04:02，这一段没看懂。

<https://docs.spring.io/spring/docs/5.0.13.RELEASE/spring-framework-reference/web.html#mvc-config-static-resources> 似乎应该前去补课！

### 28.1.8 Path Matching and Content Negotiation

默认情况下，Spring Boot选择禁用后缀模式匹配，这意味着像`"GET /projects/spring-boot.json"`这样的请求不会与`@GetMapping("/projects/spring-boot")`映射匹配。这被认为是SpringMVC应用程序的最佳实践。这个特性在过去主要用于HTTP客户机，它们没有发送正确的“接受”请求标头；我们需要确保向客户机发送正确的ContentType。如今，内容协商变得更加可靠。

we can use a query parameter to ensure that requests like `"GET /projects/spring-boot?format=json"`

```sh
spring.mvc.contentnegotiation.favor-parameter=true

# We can change the parameter name, which is "format" by default:
# spring.mvc.contentnegotiation.parameter-name=myparam

# We can also register additional file extensions/media types with:
spring.mvc.contentnegotiation.media-types.markdown=text/markdown
```

如果您了解这些警告，并且仍然希望应用程序使用后缀模式匹配，则需要以下配置：

```sh
spring.mvc.contentnegotiation.favor-path-extension=true
spring.mvc.pathmatch.use-suffix-pattern=true
```

或者，与其打开所有后缀模式，只支持注册的后缀模式更安全：

```sh
spring.mvc.contentnegotiation.favor-path-extension=true
spring.mvc.pathmatch.use-registered-suffix-pattern=true

# You can also register additional file extensions/media types with:
# spring.mvc.contentnegotiation.media-types.adoc=text/asciidoc
```

### 28.1.10 Template Engines

Spring Boot包括对以下模板引擎的自动配置支持：
- FreeMarker
- Groovy
- Thymeleaf
- Mustache

> 如果可能，应该避免JSP。它与嵌入的servlet容器一起使用时会有问题。

When you use one of these templating engines with the default configuration, your templates are picked up automatically from `src/main/resources/templates`.  ->  当您以默认配置使用这些模板引擎之一时，您的模板将从`src/main/resources/templates`中自动获取。

> Depending on how you run your application, IntelliJ IDEA orders the classpath differently. Running your application in the IDE from its main method results in a different ordering than when you run your application by using Maven or Gradle or from its packaged jar. This can cause Spring Boot to fail to find the templates on the classpath. If you have this problem, you can reorder the classpath in the IDE to place the module’s classes and resources first. Alternatively, you can configure the template prefix to search every `templates` directory on the classpath, as follows: `classpath*:/templates/`.  -> 根据您运行应用程序的方式，IntelliJIDEA对类路径的排序是不同的。
在IDE中从其Main方法运行应用程序的结果与使用Maven或Gradle或从其打包JAR运行应用程序时的顺序不同。
这可能导致SpringBoot在类路径上找不到模板。
如果您有这个问题，您可以在IDE中重新排序类路径，以便首先放置模块的类和资源。
或者，您可以配置模板前缀来搜索类路径上的每个模板目录，如下所示：CLASSPATH*：/Templates/。

### 28.1.11 Error Handling

By default, Spring Boot provides an `/error` mapping that handles all errors in a sensible way, and it is registered as a “global” error page in the servlet container. For machine clients, it produces a JSON response with details of the error, the HTTP status, and the exception message. For browser clients, there is a “whitelabel” error view that renders the same data in HTML format (to customize it, add a `View` that resolves to `error`). To replace the default behavior completely, you can implement `ErrorController` and register a bean definition of that type or add a bean of type `ErrorAttributes` to use the existing mechanism but replace the contents.  ->  默认情况下，SpringBoot提供`/error`映射，以合理的方式处理所有错误，并在servlet容器中注册为“全局”错误页面。
对于机器客户机，它生成一个JSON响应，其中包含错误、HTTP状态和异常消息的详细信息。
对于浏览器客户端，有一个以HTML格式呈现相同数据的“白色标签”错误视图(要对其进行自定义，请添加可解析为错误的视图)。
要完全替换默认行为，可以实现`ErrorController`并注册该类型的bean定义，或者添加`ErrorAttributes`类型的bean以使用现有机制但替换内容。

>  The `BasicErrorController` can be used as a base class for a custom `ErrorController`. This is particularly useful if you want to add a handler for a new content type (the default is to handle `text/html` specifically and provide a fallback for everything else). To do so, extend `BasicErrorController`, add a public method with a `@RequestMapping` that has a `produces` attribute, and create a bean of your new type.  ->  `BasicErrorController`可以用作自定义`ErrorController`的基类。
如果要为新的内容类型添加处理程序(默认值是专门处理`text/html`并为其他所有内容提供后备)，则这一点特别有用。
为此，扩展`BasicErrorController`，添加一个具有`@RequestMapping`且具有`Produces`属性的公共方法，并创建一个新类型的bean。

还可以使用 `@ControllerAdvice` , 如下：

```java
@ControllerAdvice(basePackageClasses = AcmeController.class)
public class AcmeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(YourException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(new CustomErrorType(status.value(), ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
```

In the preceding example, if `YourException` is thrown by a controller defined in the same package as `AcmeController`, a JSON representation of the `CustomErrorType` POJO is used instead of the `ErrorAttributes` representation.  ->  在前面的示例中，如果`YourException`是由`AcmeController`所定义的同一包中的控制器引发的，则使用`CustomErrorType`POJO的JSON表示而不是`ErrorAttributes`表示。

### Custom Error Pages

可以在 `/error` 目录下添加 Error Page, html或者模板都可以。

```sh
src/
 +- main/
     +- java/
     |   + <source code>
     +- resources/
         +- public/
             +- error/
             |   +- 404.html
             +- <other public assets>
```

```sh
src/
 +- main/
     +- java/
     |   + <source code>
     +- resources/
         +- templates/
             +- error/
             |   +- 5xx.ftl
             +- <other templates>
```

或者，实现 `ErrorViewResolver` 接口

```java
public class MyErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
            HttpStatus status, Map<String, Object> model) {
        // Use the request or status to optionally return a ModelAndView
        return ...
    }

}
```

You can also use regular Spring MVC features such as `@ExceptionHandler` methods and `@ControllerAdvice`. The ErrorController then picks up any unhandled exceptions.  ->  您还可以使用常规的SpringMVC特性，例如`@ExceptionHandler`方法和`@ControllerAdvice`。
然后，ErrorController将拾取所有未处理的异常。

### Mapping Error Pages outside of Spring MVC

For applications that do not use Spring MVC, you can use the `ErrorPageRegistrar` interface to directly register `ErrorPages`. This abstraction works directly with the underlying embedded servlet container and works even if you do not have a Spring MVC `DispatcherServlet`.  ->  对于不使用SpringMVC的应用程序，可以使用ErrorPageRegistry接口直接注册`ErrorPages`。
这种抽象直接用于底层的嵌入式servlet容器，即使没有SpringMVC`DispatcherServlet`也可以工作。

```java
@Bean
public ErrorPageRegistrar errorPageRegistrar(){
    return new MyErrorPageRegistrar();
}

// ...

private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
    }

}
```

>  If you register an `ErrorPage` with a path that ends up being handled by a `Filter` (as is common with some non-Spring web frameworks, like Jersey and Wicket), then the `Filter` has to be explicitly registered as an `ERROR` dispatcher, as shown in the following example:  ->  如果将`ErrorPage`注册为最终由筛选器处理的路径(这在某些非Spring Web框架(如泽西和Wicket)中很常见)，则必须将该筛选器显式注册为错误分派器，如以下示例所示：

```java
@Bean
public FilterRegistrationBean myFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new MyFilter());
    ...
    registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
    return registration;
}
```

Note that the default `FilterRegistrationBean` does not include the `ERROR` dispatcher type.  ->  请注意，默认的FilterRegistrationBean不包括Error Dispatcher类型。

CAUTION:When deployed to a servlet container, Spring Boot uses its error page filter to forward a request with an error status to the appropriate error page. The request can only be forwarded to the correct error page if the response has not already been committed. By default, WebSphere Application Server 8.0 and later commits the response upon successful completion of a servlet’s service method. You should disable this behavior by setting `com.ibm.ws.webcontainer.invokeFlushAfterService` to `false`.  ->  注意：当部署到servlet容器时，SpringBoot使用它的错误页过滤器将带有错误状态的请求转发到相应的错误页。
只有在尚未提交响应的情况下，才能将请求转发到正确的错误页。
默认情况下，WebSphereApplicationServer8.0和更高版本在servlet的服务方法成功完成后提交响应。
您应该通过将`com.ibm.ws.webcontainer.invokeFlushAfterService`设置为`false`来禁用此行为。

### 28.1.13 CORS Support

```java
@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }
        };
    }
}
```

### 28.2 The “Spring WebFlux Framework”

Spring WebFlux is the new reactive web framework introduced in Spring Framework 5.0. Unlike Spring MVC, it does not require the Servlet API, is fully asynchronous and non-blocking, and implements the Reactive Streams specification through the Reactor project.  ->  SpringWebFlux是SpringFramework5.0中引入的新的反应性Web框架。
与SpringMVC不同，它不需要ServletAPI，是完全异步和非阻塞的，并且通过反应器项目实现了反应流规范。


Spring WebFlux comes in two flavors: functional and annotation-based. The annotation-based one is quite close to the Spring MVC model, as shown in the following example:  ->  SpringWebFlux有两种风格：基于功能的和基于注释的。
基于注释的模型非常接近Spring MVC模型，如以下示例所示：

```java
@RestController
@RequestMapping("/users")
public class MyRestController {

    @GetMapping("/{user}")
    public Mono<User> getUser(@PathVariable Long user) {
        // ...
    }

    @GetMapping("/{user}/customers")
    public Flux<Customer> getUserCustomers(@PathVariable Long user) {
        // ...
    }

    @DeleteMapping("/{user}")
    public Mono<User> deleteUser(@PathVariable Long user) {
        // ...
    }

}
```

“WebFlux.fn”, the functional variant, separates the routing configuration from the actual handling of the requests, as shown in the following example:  ->  “WebFlu.fn”是功能变体，它将路由配置与请求的实际处理分开，如以下示例所示：

```java
@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(GET("/{user}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/{user}/customers").and(accept(APPLICATION_JSON)), userHandler::getUserCustomers)
                .andRoute(DELETE("/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

}

@Component
public class UserHandler {

    public Mono<ServerResponse> getUser(ServerRequest request) {
        // ...
    }

    public Mono<ServerResponse> getUserCustomers(ServerRequest request) {
        // ...
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // ...
    }
}
```

WebFlux is part of the Spring Framework and detailed information is available in its [reference documentation](https://docs.spring.io/spring/docs/5.0.13.RELEASE/spring-framework-reference/web-reactive.html#webflux-fn).


> ...... 很多内容和mvc差不多，这部分文档没仔细阅读，需要时再前来复习。

### 28.3 JAX-RS and Jersey

> 这个文档也没仔细看，之前没接触过，对`Jersey`不了解，用到时再来复习吧。

### 28.4 Embedded Servlet Container Support

Spring Boot includes support for embedded `Tomcat`, `Jetty`, and `Undertow` servers. Most developers use the appropriate “Starter” to obtain a fully configured instance. By default, the embedded server listens for HTTP requests on port 8080.  ->  Spring Boot包括对嵌入式Tomcat、Jetty和Undertwow服务器的支持。
大多数开发人员使用适当的“Starter”来获得完全配置的实例。
默认情况下，嵌入式服务器侦听端口8080上的HTTP请求。

### 28.4.1 Servlets, Filters, and listeners

When using an embedded servlet container, you can register servlets, filters, and all the listeners (such as `HttpSessionListener`) from the Servlet spec, either by using Spring beans or by scanning for Servlet components.   ->  使用嵌入式servlet容器时，可以通过使用SpringBeans或扫描servlet组件，从servlet规范中注册servlet、筛选器和所有侦听器(如`HttpSessionListener`)。

### Registering Servlets, Filters, and Listeners as Spring Beans

Any `Servlet`, `Filter`, or servlet `*Listener` instance that is a Spring bean is registered with the embedded container. This can be particularly convenient if you want to refer to a value from your `application.properties` during configuration.  ->  任何作为Springbean的`servlet`、`Filter`或servlet`*Listener`实例都会注册到嵌入的容器中。
如果要在配置过程中引用`application.properties`中的值，这将特别方便。

By default, if the context contains only a single Servlet, it is mapped to `/`. In the case of multiple servlet beans, the bean name is used as a path prefix. Filters map to `/*`.  ->  默认情况下，如果上下文只包含一个servlet，则将其映射到`/`。
在多个Servletbean的情况下，bean名称用作路径前缀。
过滤器映射到`/*`。

If convention-based mapping is not flexible enough, you can use the `ServletRegistrationBean`, `FilterRegistrationBean`, and `ServletListenerRegistrationBean` classes for complete control.  ->  如果基于约定的映射不够灵活，可以使用`ServletRegistrationBean`、`FilterRegistrationBean`和`ServletListenerRegistrationBean`类进行完全控制。

Spring Boot ships with many auto-configurations that may define Filter beans. Here are a few examples of Filters and their respective order (lower order value means higher precedence):  ->  SpringBoot附带了许多可能定义过滤bean的自动配置。
下面是一些过滤器及其相应顺序的示例(低阶值表示更高的优先级)：

|Servlet Filter|Order|
|---|---|
|`OrderedCharacterEncodingFilter`|`Ordered.HIGHEST_PRECEDENCE`|
|`WebMvcMetricsFilter`|`Ordered.HIGHEST_PRECEDENCE + 1`|
|`ErrorPageFilter`|`Ordered.HIGHEST_PRECEDENCE + 1`|
|`HttpTraceFilter`|`Ordered.LOWEST_PRECEDENCE - 10`|

It is usually safe to leave Filter beans unordered.

If a specific order is required, you should avoid configuring a Filter that reads the request body at `Ordered.HIGHEST_PRECEDENCE`, since it might go against the character encoding configuration of your application. If a Servlet filter wraps the request, it should be configured with an order that is less than or equal to `FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER`.   ->  如果需要特定的顺序，则应避免配置读取`Ordered.HIGHEST_PRECEDENCE`上的请求正文的筛选器，因为它可能与应用程序的字符编码配置背道而驰。
如果Servlet过滤器封装了请求，则应该使用小于或等于`FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER`.的顺序对其进行配置。

### 28.4.2 Servlet Context Initialization

Embedded servlet containers do not directly execute the Servlet 3.0+ `javax.servlet.ServletContainerInitializer` interface or Spring’s `org.springframework.web.WebApplicationInitializer` interface. This is an intentional design decision intended to reduce the risk that third party libraries designed to run inside a war may break Spring Boot applications.  ->  嵌入式Servlet容器不直接执行Servlet3.0+`javax.servlet.ServletContainerInitiizer`接口或SpringSpring的`org.springframework.web.WebApplicationInitializer`接口。
这是一个有意的设计决策，旨在降低设计为在WAR中运行的第三方库可能破坏SpringBoot应用程序的风险。


If you need to perform servlet context initialization in a Spring Boot application, you should register a bean that implements the `org.springframework.boot.web.servlet.ServletContextInitializer` interface. The single `onStartup` method provides access to the `ServletContext` and, if necessary, can easily be used as an adapter to an existing `WebApplicationInitializer`.   ->  如果需要在SpringBoot应用程序中执行Servlet上下文初始化，那么应该注册一个实现`org.springframework.boot.web.servlet.ServletContextInitializer`接口的Bean。
Single`onStarup`方法提供对`ServletContext`的访问，如果需要，可以很容易地将其用作现有`WebApplicationInitiizer`的适配器。

### Scanning for Servlets, Filters, and listeners

When using an embedded container, automatic registration of classes annotated with `@WebServlet`, `@WebFilter`, and `@WebListener` can be enabled by using `@ServletComponentScan`.   ->   使用嵌入式容器时，可以使用@ServletComponentScan启用使用@WebServlet、@WebFilter和@WebListener注释的类的自动注册。

>  `@ServletComponentScan` has no effect in a standalone container, where the container’s built-in discovery mechanisms are used instead.  ->  `@ServletComponentScan`在独立容器中不起作用，而是使用容器的内置发现机制。

### 28.4.3 The ServletWebServerApplicationContext

Under the hood, Spring Boot uses a different type of ApplicationContext for embedded servlet container support. The ServletWebServerApplicationContext is a special type of WebApplicationContext that bootstraps itself by searching for a single ServletWebServerFactory bean. Usually a TomcatServletWebServerFactory, JettyServletWebServerFactory, or UndertowServletWebServerFactory has been auto-configured.  ->  在幕后，SpringBoot使用一种不同类型的ApplicationContext来支持嵌入式servlet容器。
ServletWebServerApplicationContext是WebApplicationContext的一种特殊类型，它通过搜索单个ServletWebServerFactory bean来引导自身。
通常情况下，TomcatServletWebServerFactory、JettyServletWebServerFactory或UndertraServletWebServerFactory都是自动配置的。

>  You usually do not need to be aware of these implementation classes. Most applications are auto-configured, and the appropriate ApplicationContext and ServletWebServerFactory are created on your behalf.  ->  您通常不需要知道这些实现类。
大多数应用程序都是自动配置的，并为您创建了相应的ApplicationContext和ServletWebServerFactory。

### 28.4.4 Customizing Embedded Servlet Containers

> See the `ServerProperties` class for a complete list.

application.properties

```sh
server.port
server.address
server.servlet.session.persistence
server.servlet.session.timeout
server.servlet.session.store-dir
server.servlet.session.cookie.*
server.error.path

server.tomcat
server.undertow
```

### Programmatic Customization

如果需要以编程方式来配置嵌入式容器，可以提供一个`WebServerFactoryCustomizer`接口的实现，如下：

```java
@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(9000);
    }

}
```

`TomcatServletWebServerFactory`, `JettyServletWebServerFactory`, `UndertowServletWebServerFactory` 是 `ConfigurableServletWebServerFactory` 的变体，提供了更具针对性的`setter`方法


### Customizing ConfigurableServletWebServerFactory Directly

如果前面的定制技术太有限，您可以自己注册`TomcatServletWebServerFactory`、`JettyServletWebServerFactory`或`UndertraServletWebServerFactory` bean。

```java
@Bean
public ConfigurableServletWebServerFactory webServerFactory() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.setPort(9000);
    factory.setSessionTimeout(10, TimeUnit.MINUTES);
    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
    return factory;
}
```

### 28.4.5 JSP Limitations

当运行使用嵌入式servlet容器(打包为可执行档案)的SpringBoot应用程序时，JSP支持存在一些限制。

- 对于 tomcat、jetty， war包运行良好，可执行jar不支持jsp
- Undertow不支持jsp
- 创建自定义error.jsp页面不会覆盖用于错误处理的默认视图。应改用自定义错误页。





