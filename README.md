### sboot-parent201902

- [studyNotes01.md](./studyNotes01.md)
- [studyNotes02.md](./studyNotes02.md)

> <https://github.com/juedui0769/dive-in-spring-boot> , 近期spring boot的学习记录都保存在这个链接了(这是我观看小马哥视频章节笔记)。

### 13.2.2 Using Spring Boot without the Parent POM

- <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#using-boot-maven-without-a-parent>
    - 官方文档
- <https://blog.csdn.net/mn960mn/article/details/50894022>
    - 一篇非常好的csdn博文

### 向高版本迁移时可以添加一个依赖来帮助迁移

- 参考 : <https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#getting-started-upgrading-from-an-earlier-version>
- ```
  <dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-properties-migrator</artifactId>
  	<scope>runtime</scope>
  </dependency>
  ```
- 按照文档上的描述，添加这个依赖后，spring会帮你把旧的属性转化成新的属性(临时)。
- 完成迁移后，应该将此依赖移除。
- 参考博文 : <https://blog.51cto.com/7308310/2133163?source=dra>




