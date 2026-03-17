# Getting Started

* code

    > cd "C:\workspace\github\javaSamples\spring\camunda"

*startup

    > cd "C:/workspace/samples/Camunda"
    > camunda-start.bat
    > camunda-modeler.bat > deploy > run > {"item": "extra widget"}
    > browser > "http://localhost:8080/operate" > demo > demo
    > run "SpringBootApplication"

*shutdown
    > camunda-stop.bat, or CTRL_C

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.3/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

