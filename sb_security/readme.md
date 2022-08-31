Spring Security

https://spring.io/guides/gs/securing-web/
https://www.markdownguide.org/basic-syntax

	> cd c:\workspace\github\spring_security

for gradle

	> gradle			// installs local env

	> gradle tasks
	> gradle build --info -x test
	> gradle test --i
	> gradle clean test --i | findstr /i INFO:	
	> gradle bootrun

SpringBoot does not automatically configure Sqlite:

1. application.yml: store sqlite configs
	- add sqlite datasource configs driverClassName & jdbcUrl to yml
	- add hibernate configs dialect & auto
2. GeneralConfiguration: write Configuration file to create sqlite beans
	- create DataSource bean that reads configs from yml
	- create LocalContainerEntityManagerFactoryBean bean that reads configs from yml
3. SqliteDialect: create sqlite class extending Dialect
	- must define registerColumnTypes for all used table column types
	- must add Override to handle Identity Column (1 method)
	- must add Overrides to disable constraints (extra 5 methods)
	- referenced in app.yml (hibernate.dialect=com.basics...SqliteDialect)
4. SqliteIdentityColumnSupportImpl: create sqlite class extending IdentityColumnSupportImpl
	- has 3 overrides to handle @Id columns; also referenced in SqliteDialect
6. assumes Model class matches sqlite schema
	- has all column names corrected: @Column(name = "CustomerId",updatable = false, nullable = false)
	- has table name with schema prefix if any: @Table( name = "main.customers" )
7. assumes table column datatypes match
	- repository used Integer oblject, not Long
	- services used int primitives not long

---

goto > http://localhost:8080

notes:

	// note: needs forward slash if from screen
	set JAVA_TEMPCP=C:\workspace\github\spring_security\build\classes\java\main\

	java -cp %JAVA_TEMPCP% com.basics.securing.SecuringWebApp -Xlint

	java -cp $env:JAVA_TEMPCP com.basics.securing.UtilityExtra // for PowerShell
