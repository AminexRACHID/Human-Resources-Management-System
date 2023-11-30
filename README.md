# Systeme-de-Gestion-des-Ressources-Humaines

### Services Configuration for Eureka and gateway

#### Step 1: Add spring-cloud.version tag in pom.xml as show below. Ofcourse you can change the version or use below mention version.
```xml
<properties>
    ...
    <spring-cloud.version>2022.0.2</spring-cloud.version>
    ...
</properties>
```

#### Step 2: Add dependency as show below
```xml
<dependency>
   <groupId>org.springframework.cloud</groupId> 
   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

#### Step 3: Add dependency management tag just below dependencies tag
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2022.0.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

#### Step 4 : add in properties
```xml
spring.cloud.discovery.enabled=true
spring.application.name=user-service
```

### Services Configuration for Feign Rest Client

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
