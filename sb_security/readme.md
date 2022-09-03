Spring Security

https://spring.io/guides/gs/securing-web/
https://www.markdownguide.org/basic-syntax

	> cd c:\workspace\github\spring\sb_security

goto > http://localhost:8080

ERROR:

	Error starting Tomcat context. Exception: org.springframework.beans.factory.UnsatisfiedDependencyException.

notes:

	// note: needs forward slash if from screen
	set JAVA_TEMPCP=C:\workspace\github\spring_security\build\classes\java\main\

	java -cp %JAVA_TEMPCP% com.basics.securing.SecuringWebApp -Xlint

	java -cp $env:JAVA_TEMPCP com.basics.securing.UtilityExtra // for PowerShell
