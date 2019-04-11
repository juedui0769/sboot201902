
### 29. Security

If Spring Security is on the classpath, then web applications are secured by default. Spring Boot relies on Spring Security’s content-negotiation strategy to determine whether to use `httpBasic` or `formLogin`. To add method-level security to a web application, you can also add `@EnableGlobalMethodSecurity `with your desired settings. Additional information can be found in the [Spring Security Reference Guide](https://docs.spring.io/spring-security/site/docs/5.0.12.RELEASE/reference/htmlsingle/#jc-method).   ->  如果SpringSecurity在类路径上，那么默认情况下Web应用程序是安全的。
SpringBoot依赖于SpringSecurity的内容协商策略来确定是使用httpAccess还是formLogin。
若要向Web应用程序添加方法级安全性，还可以使用所需的设置添加@EnableGlobalMethodSecurity。
其他信息可以在SpringSecurityReferenceGuide中找到。

The default `UserDetailsService` has a single user. The user name is `user`, and the password is random and is printed at INFO level when the application starts, as shown in the following example:   ->  默认`UserDetailsService`只有一个用户。
用户名是用户名，密码是随机的，并在应用程序启动时以信息级别打印，如以下示例所示：

```sh
Using generated security password: 78fa095d-3f4c-48b1-ad50-e24c31d5cf35
```

> If you fine-tune your logging configuration, ensure that the `org.springframework.boot.autoconfigure.security` category is set to log `INFO`-level messages. Otherwise, the default password is not printed.   ->  如果您微调了日志配置，请确保将org.springframework.boot.autoconfigure.security类别设置为日志信息级别的消息。
否则，将不打印默认密码。


You can change the username and password by providing a `spring.security.user.name` and `spring.security.user.password`.

- `UserDetailsService` (or `ReactiveUserDetailsService` WebFlux) , `SecurityProperties.User`
- A `DefaultAuthenticationEventPublisher` for publishing authentication events.

You can provide a different `AuthenticationEventPublisher` by adding a bean for it.

### 29.1 MVC Security

...

### 29.3 OAuth2

### 29.3.1 Client

<https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#boot-features-security-oauth2-client>


### 29.4 Actuator Security


>  这第`29`节快速扫了一遍，没仔细看。


### 30. Working with SQL Databases

The Spring Framework provides extensive support for working with SQL databases, from direct JDBC access using `JdbcTemplate` to complete “object relational mapping” technologies such as Hibernate. Spring Data provides an additional level of functionality: creating `Repository` implementations directly from interfaces and using conventions to generate queries from your method names.   ->  SpringFramework为使用SQL数据库提供了广泛的支持，从使用JdbcTemplate直接访问JDBC到完成Hibernate等“对象关系映射”技术。
Spring Data提供了一个附加级别的功能：直接从接口创建存储库实现，并使用约定从方法名生成查询。

### 30.1 Configure a DataSource

Java’s `javax.sql.DataSource` interface provides a standard method of working with database connections. Traditionally, a 'DataSource' uses a URL along with some credentials to establish a database connection.  ->  DataSource接口提供了处理数据库连接的标准方法。
传统上，“DataSource”使用URL和一些凭据来建立数据库连接。


### 30.1.1 Embedded Database Support

It is often convenient to develop applications by using an in-memory embedded database. Obviously, in-memory databases do not provide persistent storage. You need to populate your database when your application starts and be prepared to throw away data when your application ends.   ->   使用内存中的嵌入式数据库来开发应用程序通常是很方便的。
显然，内存中的数据库不提供持久存储。
您需要在应用程序启动时填充数据库，并做好在应用程序结束时丢弃数据的准备。

Spring Boot can auto-configure embedded `H2`, `HSQL`, and `Derby` databases. You need not provide any connection URLs. You need only include a build dependency to the embedded database that you want to use.  ->  Spring Boot可以自动配置嵌入式H2、HSQL和Derby数据库。
您不需要提供任何连接URL。
您只需将构建依赖项包含到要使用的嵌入式数据库。

>  If you are using this feature in your tests, you may notice that the same database is reused by your whole test suite regardless of the number of application contexts that you use. If you want to make sure that each context has a separate embedded database, you should set `spring.datasource.generate-unique-name` to `true`.  ->  如果您在测试中使用此功能，您可能会注意到，无论您使用多少应用程序上下文，整个测试套件都会重用相同的数据库。
如果要确保每个上下文都有一个单独的嵌入式数据库，则应该将Spring.Datasource.generate-unique-name设置为true。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.hsqldb</groupId>
    <artifactId>hsqldb</artifactId>
    <scope>runtime</scope>
</dependency>
```

>  You need a dependency on `spring-jdbc` for an embedded database to be auto-configured. In this example, it is pulled in transitively through `spring-boot-starter-data-jpa`.   ->  您需要依赖Spring-JDBC才能自动配置嵌入式数据库。
在这个例子中，它是通过Spring-Boot-Starter-Data-JPA过渡地拉入的。

> If, for whatever reason, you do configure the connection URL for an embedded database, take care to ensure that the database’s automatic shutdown is disabled. If you use H2, you should use `DB_CLOSE_ON_EXIT=FALSE` to do so. If you use HSQLDB, you should ensure that `shutdown=true` is not used. Disabling the database’s automatic shutdown lets Spring Boot control when the database is closed, thereby ensuring that it happens once access to the database is no longer needed.   ->   如果出于某种原因，您确实为嵌入式数据库配置了连接URL，请注意确保数据库的自动关闭处于禁用状态。
如果使用H2，则应该使用DB_CLOSE_ON_EXIT=FALSE来执行此操作。
如果使用HSQLDB，则应确保不使用Shutdown=true。
禁用数据库的自动关闭可以在关闭数据库时进行SpringBoot控制，从而确保一旦不再需要访问数据库，就会发生这种情况。

### 30.1.2 Connection to a Production Database

- `HikariCP` 是首选
- 或者 Tomcat pooling `DataSource`
- 或者 if `Commons DBCP2` is available, we use it.


If you use the `spring-boot-starter-jdbc` or `spring-boot-starter-data-jpa` “starters”, you automatically get a dependency to HikariCP.   ->   如果您使用Spring-bootstarter-jdbc或Spring-bootstarter-data-jpa“starters”，那么您将自动获得对HikariCP的依赖关系。

> You can bypass that algorithm completely and specify the connection pool to use by setting the `spring.datasource.type` property. This is especially important if you run your application in a Tomcat container, as `tomcat-jdbc` is provided by default.  ->  您可以完全绕过该算法，并通过设置Spring.Datource.type属性来指定要使用的连接池。
如果在Tomcat容器中运行应用程序，这一点尤其重要，因为默认情况下提供tomcat-jdbc。

>  Additional connection pools can always be configured manually. If you define your own `DataSource` bean, auto-configuration does not occur.  ->  始终可以手动配置其他连接池。
如果您定义了自己的DataSourcebean，则不会进行自动配置。

DataSource configuration is controlled by external configuration properties in `spring.datasource.*`. For example, you might declare the following section in `application.properties`:   ->   DataSource配置由Spring.Datasource.*中的外部配置属性控制。
例如，您可以在application.properties中声明以下部分：

```xml
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

>  You should at least specify the URL by setting the spring.datasource.url property. Otherwise, Spring Boot tries to auto-configure an embedded database.  ->  您至少应该通过设置Spring.Datasource.url属性来指定URL。
否则，SpringBoot将尝试自动配置嵌入式数据库。

> You often do not need to specify the driver-class-name, since Spring Boot can deduce it for most databases from the url.  ->  您通常不需要指定驱动程序类名，因为SpringBoot可以从url推断大多数数据库的驱动程序类名。

>  For a pooling `DataSource` to be created, we need to be able to verify that a valid `Driver` class is available, so we check for that before doing anything. In other words, if you set `spring.datasource.driver-class-name=com.mysql.jdbc.Driver`, then that class has to be loadable.  ->  对于要创建的池DataSource，我们需要能够验证一个有效的驱动程序类是否可用，因此我们在执行任何操作之前都要检查它是否可用。
换句话说，如果您设置了spring.datasource.driver-class-name=com.mysql.jdbc.Driver，那么该类必须是可加载的。

See `DataSourceProperties` for more of the supported options. These are the standard options that work regardless of the actual implementation. It is also possible to fine-tune implementation-specific settings by using their respective prefix (`spring.datasource.hikari.*`, `spring.datasource.tomcat.*`, and `spring.datasource.dbcp2.*`). Refer to the documentation of the connection pool implementation you are using for more details.   ->  有关支持的更多选项，请参见DataSourceProperties。
无论实际实现如何，这些都是标准选项。
还可以使用它们各自的前缀(Spring.data ource.hikari.*、Spring.data ource.tomcat.*和Spring.data ource.dbcp2.*)来微调特定于实现的设置。
有关详细信息，请参阅您正在使用的连接池实现的文档。


For instance, if you use the Tomcat connection pool, you could customize many additional settings, as shown in the following example:

```xml
# Number of ms to wait before throwing an exception if no connection is available.
spring.datasource.tomcat.max-wait=10000

# Maximum number of active connections that can be allocated from this pool at the same time.
spring.datasource.tomcat.max-active=50

# Validate the connection before borrowing it from the pool.
spring.datasource.tomcat.test-on-borrow=true
```

### 30.1.3 Connection to a JNDI DataSource

...

### 30.2 Using JdbcTemplate

Spring’s `JdbcTemplate` and `NamedParameterJdbcTemplate` classes are auto-configured, and you can `@Autowire` them directly into your own beans, as shown in the following example:  ->  Spring的JdbcTemplate和NamedParameterJdbcTemplate类是自动配置的，您可以将它们直接@Autowire放到您自己的bean中，如以下示例所示：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyBean(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ...

}
```

You can customize some properties of the template by using the spring.jdbc.template.* properties, as shown in the following example:

```sh
spring.jdbc.template.max-rows=500
```

>  The `NamedParameterJdbcTemplate` reuses the same `JdbcTemplate` instance behind the scenes. If more than one `JdbcTemplate` is defined and no primary candidate exists, the `NamedParameterJdbcTemplate` is not auto-configured.  ->  NamedParameterJdbcTemplate在后台重用相同的JdbcTemplate实例。
如果定义了多个JdbcTemplate且不存在主要候选者，则NamedParameterJdbcTemplate不会自动配置。

### 30.3 JPA and “Spring Data”

...

<https://spring.io/guides/gs/accessing-data-jpa/>

<https://docs.spring.io/spring-data/jpa/docs/2.1.6.RELEASE/reference/html/>

### 30.3.1 Entity Classes

 `@Entity`, `@Embeddable`, or `@MappedSuperclass `

`@EntityScan` : [81.4 Separate @Entity Definitions from Spring Configuration](https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#howto-separate-entity-definitions-from-spring-configuration)

### 30.3.2 Spring Data JPA Repositories

这里讲得就是根据方法名来推断出查询条件(我在imooc视频中有见过)，更具体的信息，还是见下面的链接。

<https://docs.spring.io/spring-data/jpa/docs/2.1.6.RELEASE/reference/html/>

### 30.3.3 Creating and Dropping JPA Databases

```
spring.jpa.hibernate.ddl-auto=create-drop
```

```
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
```

### 30.3.4 Open EntityManager in View

If you are running a web application, Spring Boot by default registers `OpenEntityManagerInViewInterceptor` to apply the “Open EntityManager in View” pattern, to allow for lazy loading in web views. If you do not want this behavior, you should set `spring.jpa.open-in-view` to `false` in your `application.properties`.  ->   如果您运行的是Web应用程序，则默认情况下，Spring Boot会注册`OpenEntityManagerInViewInterceptor`以应用“OpenEntityManagerinView”模式，以允许在Web视图中进行延迟加载。
如果不希望出现此行为，则应在application.properties中将Spring.jpa.open-in-view设置为false。

### 30.4 Using H2’s Web Console

满足下面三个条件，会自动激活H2 web console
- You are developing a servlet-based web application.
- `com.h2database:h2` is on the classpath.
- devtools 开启， [You are using Spring Boot’s developer tools.](https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-devtools)

>  If you are not using Spring Boot’s developer tools but would still like to make use of H2’s console, you can configure the `spring.h2.console.enabled` property with a value of `true`.  -> 如果您不使用SpringBoot的开发人员工具，但仍然希望使用H2的控制台，则可以使用值true来配置SpringShare2.Console.Enabled属性。(如果您不使用SpringBoot的开发工具，但仍想使用H2的控制台)，则可以将其配置为true。


>  The H2 console is only intended for use during development, so you should take care to ensure that `spring.h2.console.enabled` is not set to `true` in production.  -> H2控制台仅打算在开发期间使用，因此您应该注意确保在生产中未将Spring 2.sole e.Enabled设置为true。

### 30.4.1 Changing the H2 Console’s Path

By default, the console is available at `/h2-console`. You can customize the console’s path by using the `spring.h2.console.path` property.


## 31. Working with NoSQL Technologies

### 31.1 Redis

`spring-boot-starter-data-redis`  : By default, it uses `Lettuce`.   <https://github.com/lettuce-io/lettuce-core/>

`spring-boot-starter-data-redis-reactive` 还有响应式的starter

```java
@Component
public class MyBean {

    private StringRedisTemplate template;

    @Autowired
    public MyBean(StringRedisTemplate template) {
        this.template = template;
    }

    // ...

}
```

### 31.2 MongoDB

including the `spring-boot-starter-data-mongodb` and `spring-boot-starter-data-mongodb-reactive` “Starters”.

```java
import org.springframework.data.mongodb.MongoDbFactory;
import com.mongodb.DB;

@Component
public class MyBean {

    private final MongoDbFactory mongo;

    @Autowired
    public MyBean(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    // ...

    public void example() {
        DB db = mongo.getDb();
        // ...
    }

}
```

Mongo 2.x 和 Mongo 3.0 的配置有些不同，具体用到时前来查阅。


### 31.6 Elasticsearch

<https://github.com/searchbox-io/Jest>


## 32. Caching

The Spring Framework provides support for transparently adding caching to an application. At its core, the abstraction applies caching to methods, thus reducing the number of executions based on the information available in the cache. The caching logic is applied transparently, without any interference to the invoker. Spring Boot auto-configures the cache infrastructure as long as caching support is enabled via the `@EnableCaching` annotation.   ->   SpringFramework为向应用程序透明地添加缓存提供了支持。
在其核心部分，抽象将缓存应用于方法，从而减少了基于缓存中可用信息的执行次数。
缓存逻辑的应用是透明的，不会对调用器产生任何干扰。
只要通过@EnableCach注释启用了缓存支持，Spring Boot就会自动配置缓存基础结构。

......

## 33.1 JMS





















