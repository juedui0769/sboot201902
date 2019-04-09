
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


















