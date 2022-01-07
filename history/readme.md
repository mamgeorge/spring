> cd c:\workspace\github\spring\history

intent is to build a flexible data entry DB
 
* see: \5Personal\History\icons
* http://localhost:8080/


process:
* embedded H2 & JPA to start
* embedded syntax is specialized to use multiline
* uses std controller, repository, service, 
* uses springBoot eb, thymeleaf, lombok, JUnit5
* thymeleaf includes images, loops 

---
> mvn clean install
> mvn compile
> mvn test
> mvn spring-boot:run

> gradle clean build --info -x test				// CTRL-ENTER in IJ IDE
> gradle clean test --i | findstr /i INFO:
> gradle bootrun

