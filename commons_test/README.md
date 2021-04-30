To use in maven module, add the following dependency:
```xml
<dependencies>
  <dependency>
    <groupId>com.savdev.datasource</groupId>
    <artifactId>datasource-commons-test</artifactId>
    <version>${project.version}</version>
    <classifier>tests</classifier>
    <type>test-jar</type>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
  </dependency>
</dependencies>
```
Note: test dependencies are not inherited from `datasource-commons-test` module.

Extend your test class from `MySqlLiquibaseBaseIT`:
```java
public class ExampleEntityIT extends MySqlLiquibaseBaseIT {}
```

Requirement: in the parent `pom.xml` you must configure `maven-jar-plugin` with `test-jar` goal:
```xml
  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-jar-plugin</artifactId>
          <version>${maven-jar-plugin.version}</version>
          <executions>
            <execution>
              <goals>
                <goal>test-jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
```