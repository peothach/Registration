# API for Mobile Developer Test

This application provide Registration User Services.

------------
#### 1. Design approach
![](https://github.com/peothach/Registration/blob/main/image/Design-Approach.png)

#### 2. Workflow Diagram
![](https://github.com/peothach/Registration/blob/main/image/Workflow-Diagram_1.png)

#### 3.  Requirements
- Java - 11.
- Maven - 3.x.x.
- H2 for local environment.
- Docker.
- Swagger UI.

------------


#### 4. Steps to setup
- Build and run the application using docker

```bash
docker-compose up -d
```

> **Notes: If you are using Linux and facing a problem seem likes  /bin/sh: ./mvnw: Permission denied**

***To do resolved that:***

    chmod +x mvnw

> ***Run again: docker-compose up -d***

------------
#### 5. Local development
-  In memory H2 database: http://localhost:8080/h2 (JDBC URL: jdbc:h2:mem:h2db, User Name: sa)
![](https://github.com/peothach/Registration/blob/main/image/H2-DB.png)

- Swagger UI: http://localhost:8080/swagger-ui.html
![](https://github.com/peothach/Registration/blob/main/image/Swagger.png)

#### 6. Public API

- http://localhost:8080/api/v1/registration

#### 7. Check test coverage and checkstyle
- Build project: mvn clean install
- Test coverage verification: mvn jacoco:check. Detail test coverage at least 70%, please go to folder {current_source_folder}/target/site/jacoco/index.html
- Checkstyle: mvn checkstyle:check

#### 8. Testing with Postman
-  I have attached postman file. You can import and testing with API for Mobile Developer Test Collection.

#### 9. Recommendation
- We can leverage docker to scale up and down by using containerized application.
- To easily change logic, we can apply rule engine.

#### 10.  Test Driven Development
![](https://github.com/peothach/Registration/blob/main/image/TDD_1.png)
![](https://github.com/peothach/Registration/blob/main/image/TDD_2.png)
