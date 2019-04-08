
## Part IV. Spring Boot features

<https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#boot-features>

### 23. SpringApplication

The SpringApplication class provides a convenient way to bootstrap a Spring application that is started from a main() method. In many situations, you can delegate to the static SpringApplication.run method, as shown in the following example:  ->  SpringApplication类提供了一种方便的方法来引导从main()方法启动的Spring应用程序。在许多情况下，您可以委托静态SpringApplication.run方法，如以下示例所示：

```java
public static void main(String[] args) {
    SpringApplication.run(MySpringConfiguration.class, args);
}
```











