Spring Annotations

http://zetcode.com/springboot/annotations

	> cd c:\workspace/github/annotations

	> mvn clean install
	> mvn compile
	> mvn test
	> mvn spring-boot:run

	> gradle clean build --info -x test				// CTRL-ENTER in IJ IDE
	> gradle clean test --i | findstr /i INFO:
	> gradle bootrun

	http://localhost:8080/

Another way to run this is below, but the classPath changes for internal resources:

	> mvn exec:java -Dexec.mainClass="com.basics.BasicsApplication"

	Similarly, you can run the Utility class with this

	// note: needs forward slash if from screen
	set JAVA_TEMPCP=C:\workspace\github\spring_annotations\target\classes;C:\Users\mamge\.m2\repository\org\json\org.json\chargebee-1.0\org.json-chargebee-1.0.jar;C:\Users\mamge\.m2\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-yaml\2.11.2\jackson-dataformat-yaml-2.11.2.jar;C:\Users\mamge\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.11.1\jackson-core-2.11.1.jar;C:\Users\mamge\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.11.1\jackson-databind-2.11.1.jar

	java -cp %JAVA_TEMPCP% com.basics.util.UtilityExtra
	java -cp $env:JAVA_TEMPCP com.basics.util.UtilityExtra // for PowerShell

search filters

	*.java;*.js;*.json;*.xml;*.txt;*.yml;*.properties,*.flth

http://localhost:8080/h2console/login.jsp

	SELECT * FROM cities WHERE 
		population > 20000000 AND
		name NOT LIKE 'ZZ%'
		ORDER BY population ASC