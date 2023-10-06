Spring Security

	https://spring.io/guides/gs/securing-web/
	https://www.markdownguide.org/basic-syntax

	> cd c:\workspace\github\spring\sb_security

	goto > http://localhost:8080

SSL

** 2way: "https://www.baeldung.com/java-https-client-certificate-authentication"

* 1 server_certificate [ (Self_Signed) "serverkeystore.p12", "server-certificate.pem", "clienttruststore.jks"

* 2 client_certificate [ (Self_Signed) "clientkeystore.p12", "client-certificate.pem", "servertruststore.jks"

* 3 code [ SSLSocketServer, SSLSocketClient ]

* 4 run code with certs [ note: Windows multiLine Shell Code: ^, \, ;, && ]


	java -cp "C:/workspace/github/spring/sb_security/target/classes/;C:/workspace/github/spring/sb_security/target/test-classes/" -Djavax.net.ssl.keyStore="C:/workspace/training/xtra/serverkeystore.p12" -Djavax.net.ssl.keyStorePassword=password -Djavax.net.ssl.trustStore="C:/workspace/training/xtra/servertruststore.jks" -Djavax.net.ssl.trustStorePassword=password com.basics.securing.utils.SSLSocketServer

	java -cp "C:/workspace/github/spring/sb_security/target/classes/;C:/workspace/github/spring/sb_security/target/test-classes/" -Djavax.net.ssl.keyStore="C:/workspace/training/xtra/clientkeystore.p12" -Djavax.net.ssl.keyStorePassword=password -Djavax.net.ssl.trustStore="C:/workspace/training/xtra/clienttruststore.jks" -Djavax.net.ssl.trustStorePassword=password com.basics.securing.utils.SSLSocketClient

** 1way [ KeyStore, SSLContext, HttpClient, RestTemplate, ResponseEntity; SSLExchange, SSLExchangeTest ]

ERROR:

	Error starting Tomcat context. Exception: org.springframework.beans.factory.UnsatisfiedDependencyException.

notes:

	// note: needs forward slash if from screen
	set JAVA_TEMPCP=C:\workspace\github\spring_security\build\classes\java\main\

	java -cp %JAVA_TEMPCP% com.basics.securing.SecuringWebApp -Xlint

	java -cp $env:JAVA_TEMPCP com.basics.securing.UtilityExtra // for PowerShell
