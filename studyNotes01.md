

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






