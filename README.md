# Spring boot crud h2 (one to many)
<details>
  
  - Testing with mockito 
  - Using logback for debug
  - Using Jpa/hibernate 

</details>

Developed by [ttknpde-v](https://github.com/ttknpde-v)



# What things I got this project
- [x] How to set up logback pattern on xml file
<details>
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <configuration>
  
      <!-- Setting up log path and log file name -->
      <property name="LOG_PATH" value="B:/sp-log/" />
      <property name="LOG_FILE_NAME" value="one-to-many-h2" />
  
      <!-- Setting up logging pattern for console logging
           We can set color text logging !
           like
                                  (color follow the level)       (yellow)
           2023-10-28 09:15:57,510        INFO            [http-nio-8080-exec-1] : Completed initialization in 2 ms
      -->
      <appender name="ConsoleOutput"
                class="ch.qos.logback.core.ConsoleAppender">
          <layout class="ch.qos.logback.classic.PatternLayout">
              <Pattern>
                  %white(%d{ISO8601}) %highlight(%-5level) [%yellow(%t)] : %msg%n%throwable
              </Pattern>
          </layout>
      </appender>
  
      <!-- Setting the filename and logging pattern for log file -->
      <appender name="LogFile"
                class="ch.qos.logback.core.rolling.RollingFileAppender">
          <file>${LOG_PATH}/${LOG_FILE_NAME}.log</file>
          <encoder
                  class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
              <Pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level : %msg%n
              </Pattern>
          </encoder>
  
          <!-- Setting up a rolling policy with rolling done
                daily and when file size is 10MB-->
          <rollingPolicy
                  class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
              <fileNamePattern>${LOG_PATH}/archived/${LOG_FILE_NAME}-%d{yyyy-MM-dd}.%i.log
              </fileNamePattern>
              <timeBasedFileNamingAndTriggeringPolicy
                      class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                  <maxFileSize>10MB</maxFileSize>
              </timeBasedFileNamingAndTriggeringPolicy>
          </rollingPolicy>
      </appender>
  
      <!-- Logging at INFO level -->
      <root level="info">
          <appender-ref ref="LogFile" />
          <appender-ref ref="ConsoleOutput" />
      </root>
  
      <!-- Logging at TRACE level -->
      <logger name="com.log" level="trace" additivity="false">
          <appender-ref ref="LogFile" />
          <appender-ref ref="ConsoleOutput" />
      </logger>
  
  </configuration>
  ```

  `example inside this project`

  ```java
  package com.ttknpdev.springbootonetomanyh2.log;
  import com.ttknpdev.springbootonetomanyh2.command.CommandClient;
  import com.ttknpdev.springbootonetomanyh2.dao.many.AddressDao;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  public interface Logging {
    Logger addressDao = LoggerFactory.getLogger(AddressDao.class);
    Logger commandClient = LoggerFactory.getLogger(CommandClient.class);
  }
  ```

</details>

- [x] Understand more to use anotations @OneToMany and @ManyToOne
<details>
  
 `this example's inside entity Employee`
  
```java
    @OneToMany(cascade = CascadeType.ALL) 
    @JoinColumn(name = "eid")
    private List<Address> addresses;
    /*
    {
        "eid":"E001",
        "fullname":"Peter Parker",
        "age":23,
        "salary":25000.0,
        "addresses":[ // json array
           {
              "aid":"A001",
              "city":"Bangkok",
              "country":"Thailand",
              "details":"123 soi.1/3 ABC Village"
           },
           {
              "aid":"A002",
              "city":"Bangkok",
              "country":"Thailand",
              "details":"122 soi.1/3 ABC Village"
           }
        ]
     }
    */
```

   `this example's inside entity Address`
   
```java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid")
    @JsonIgnore
    private Employee employee;
 ```
   
</details>

- [x] Repeat , How to use RestTemplate (within the Spring framework) for sending any http method I wanted   [example](https://github.com/ttknpde-v/spring-boot-jpa-hibernate-crud-h2-one-to-many-and-mockito-and-logback-configuration/blob/main/src/main/java/com/ttknpdev/springbootonetomanyh2/command/CommandClient.java)
- `import org.springframework.web.client.RestTemplate;`
- `import org.springframework.http.*;`

  
- [x] Testing this application with mokito [example](https://github.com/ttknpde-v/spring-boot-jpa-hibernate-crud-h2-one-to-many-and-mockito-and-logback-configuration/blob/main/src/test/java/com/ttknpdev/springbootonetomanyh2/MyBusinessLogic.java)
<details>
  
  `these are dependency for using mockito`

```xml
   <dependency>
     <groupId>org.junit.jupiter</groupId>
     <artifactId>junit-jupiter-api</artifactId>
     <version>5.8.1</version>
     <scope>test</scope>
   </dependency>
   <dependency>
     <groupId>org.junit.jupiter</groupId>
     <artifactId>junit-jupiter-engine</artifactId>
     <version>5.8.1</version>
     <scope>test</scope>
   </dependency>
```
  
</details>

# My Description

How difference to the last project about one to many , 

I know better to use logging library. (Default config and setting log back config) And this project 

I understand How to use JPA for relations one to many better. (using @OneToMany for List Entity,@manytoone for Entity)

I know it's bad performance. And again I use mock for testing my logic.
