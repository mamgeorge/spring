Spring Security

https://spring.io/guides/gs/securing-web/

	> cd c:\workspace\github\spring_security

for gradle

	> gradle			// installs local env

	> gradle tasks
	> gradle build --info -x test
	> gradle test --i
	> gradle clean test --i | findstr /i INFO:	
	> gradle bootrun

goto > http://localhost:8080

notes:

	// note: needs forward slash if from screen
	set JAVA_TEMPCP=C:\workspace\github\spring_security\build\classes\java\main\

	java -cp %JAVA_TEMPCP% com.basics.securing.WebSecurityAny -Xlint

	java -cp $env:JAVA_TEMPCP com.basics.securing.UtilityExtra // for PowerShell
