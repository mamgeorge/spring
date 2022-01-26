# Purpose

The purpose of this project is to provide a stripped down example of using an Embedded DB; in thais case H2.

Notes:
The persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
The embedded h2 console JDBC URL must be "jdbc:h2:mem:anydbname" (match "spring.datasource.url: jdbc:h2:mem:anydbname")

# Components

* Spring Boot		Actuator OPS
* Thymeleaf		    TEMPLATE ENGINES
* H2 Database		SQL
* Spring Data JPA	SQL
* Spring Web		WEB
* Lombok			DEVELOPER TOOLS

# Additions

* added "resources\templates\index.html" with tl tags
* added "resources\import.sql" with imports
* alter "resources\application.yml" with 4 DB properties
* added @Entity @Table City, @Repository CityRepository, @Service CityService, ICityService
* added @RestController SbController
  * @Autowired iService
  * with @GetMapping returning String
  * with @GetMapping returning ModelAndView
  * with @GetMapping returning Object
  * with @GetMapping with @PathVariable returning ModelAndView
* added @SpringBootTest SbControllerTest

# References

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.5/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

