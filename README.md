# Spring Cloud Config Extension

Mule 4 Extension to read properties from Spring Cloud Config

Porting [Mule 3 Spring Config Connector] to Mule 4.

To Use this Extension, execute the following. 
```
git clone git@github.com:krisunni/spring-cloud-config-connector.git
cd spring-cloud-config-connector
mvn clean install
```
To use this extension in your Mule 4 application add the following dependency in `pom.xml`
```
<dependency>
            <groupId>com.ku.portfolio</groupId>
            <artifactId>spring-cloud-config-connector</artifactId>
            <version>4.0.0</version>
            <classifier>mule-plugin</classifier>
 </dependency>
```

[Mule 3 Spring Config Connector]:https://github.com/mulesoft-labs/spring-cloud-config-connector
