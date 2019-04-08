
### 开始

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#getting-started-first-application>
- 开始于上面的链接。

> 我这个module是继承parent的，在parent中又继承了 springboot
>
> 可以使用 Maven 的 `import` 另辟蹊径。

### dependency:tree

```sh
mvn dependency:tree
```

- 运行以上命令可以查看项目依赖
- 终于知道 `mvn dependency:tree` 的出处了， 看什么spring boot教程都不如看官方文档。

### 跳转到spring mvc

- <https://docs.spring.io/spring/docs/5.0.13.RELEASE/spring-framework-reference/web.html#mvc>
- `@RestController`, `@RequestMapping` 这两个注解是springmvc的内容，以上链接是对应的文档。

### mvn spring-boot:run

```sh
mvn spring-boot:run
```

-  Since you used the `spring-boot-starter-parent` POM, you have a useful `run` goal that you can use to start the application
- <http://localhost:8080>

### mvn package

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

- 添加以上依赖，执行 `mvn package` 会得到一个可执行jar文件。
- 使用 `jar -tvf docs-helloworld-1.0-SNAPSHOT.jar` 命令可以查看jar文件清单









