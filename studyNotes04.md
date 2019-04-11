
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





















