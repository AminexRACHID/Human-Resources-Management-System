# Systeme-de-Gestion-des-Ressources-Humaines

This project is a distributed Human Resources Management System (HRMS) designed for organizations and enterprises to efficiently manage various human resource processes. The system provides a range of services for both employees and administrators, aiming to streamline operations, manage personnel data, and facilitate internal communication.

## Key Features:

- **Administrative Personnel Management**: Add, modify, delete, or search for employees. Automated account creation during employee addition, with login credentials generated from their email and national ID.
  
- **Absence Management**: Employees can request leaves by specifying start and end dates, the reason for the absence, and provide justifications. The system tracks both justified and unjustified absences and sends absenteeism reports when thresholds are exceeded.

- **Training Management**: Administrators can create training plans and sessions. Employees can request training, and the system keeps track of their completed courses.

- **Stage (Internship) Management**: Manage internship offers, including creating, modifying, or deleting offers. Interns can apply for positions, and the system sends confirmation emails. Successful applicants can track their internship progress and request internship certificates.

- **Internal Messaging System**: Facilitates communication between employees, enabling messages to individuals or groups with options for attachments. Users receive notifications for new messages and can manage their inbox and sent items.

- **Document Generation**: Generate different types of attestations, such as work or internship certificates, tailored to the employee’s information.

## Services Provided:

1. **Employee Management**: Administrative functions to manage employee data and accounts.
2. **Absence Management**: Allows employees to request and view their absences.
3. **Training Management**: Enables administrators to manage training sessions and allows employees to view and request training.
4. **Internship Management**: Internship offer management and tracking of applications.
5. **Internal Messaging**: A secure platform for communication between collaborators within the organization.
6. **Attestation and Documentation**: Generation of certificates for employees and interns.

This system aims to simplify HR management and improve internal operations by providing a fully integrated solution for handling employee records, absences, training, and communication within the organization.


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
