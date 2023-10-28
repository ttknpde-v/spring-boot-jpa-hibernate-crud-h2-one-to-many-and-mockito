package com.ttknpdev.springbootonetomanyh2.log;
// -- Default Logging With No Configurations
import com.ttknpdev.springbootonetomanyh2.command.CommandClient;
import com.ttknpdev.springbootonetomanyh2.dao.many.AddressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
Spring boot allows us to see the logs in the console even if we do not provide any specific configuration for it.
This is because spring boot uses Logback for its default logging.
Spring boot’s internal logging provider is Apache Commons which provides support for Java Util Logging ,Log4j2, and Logback.
Apache Commons had to be manually imported until spring boot 1.x.
However, since spring boot 2.x, it is downloaded transitively. To be more specific,
Spring boot starters such as spring-boot-starter-web imports spring-boot-starter-logging which automatically pulls in Logback.
On running the application and visiting the
*/
public interface Logging {
    Logger addressDao = LoggerFactory.getLogger(AddressDao.class);
    Logger commandClient = LoggerFactory.getLogger(CommandClient.class);
}
/*
 *********************************************
 we can set Logging with Logback configuration
 Whenever Spring boot finds a file with any of the following names, It automatically overrides the default configuration.
    logback-spring.groovy
    logback.groovy
    logback-spring.xml
    logback.xml

 *********************************************
 *********************************************
 *********************************************

 and we can set Logging with Log4j2 configuration
 For using log4j2, we need to exclude Logback from our starter dependency and add the dependency for log4j2
 Like
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>

 we need to define the configuration file for Log4j2. When spring boot finds a file with any of the following names, It automatically overrides it over default configuration.
     log4j2-spring.xml
     log4j2.xml
**/
