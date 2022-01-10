> cd c:\workspace\github\spring\history
> \\5Personal\History\0304HistorySum.txt

intent: build a flexible data entry DB
* see: \5Personal\History\icons
* http://localhost:8080/

features
* employs global world history principles
* handles utf charsets in sql
* truncated length

todos
* error handling for entries
* handle duplicates
* upload images
* scrolling on different options?
* read in csv (alternate entries); parse text files
* terminal show utf8 characters, MAYBE
 
process
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

Copyright 2022 by Martin Lee George, Columbus Ohio

1964 -2064 | eramain | USA Ohio | Martin George | lived life, married a wife, had a son, blessed a ton, 历史 | geneology, SS card, BirthCertificate, Credit Card | a2000