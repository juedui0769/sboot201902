

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
- `spring-boot-starter-*`
- `thirdpartyproject-spring-boot-starter`
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






