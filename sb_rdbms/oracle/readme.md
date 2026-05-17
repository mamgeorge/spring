
# NOTES

* What jar file is used for Oracle 24.3.2.347.1826? 
	* com.oracle.database.jdbc:ojdbc11:23.26.2.0.0 

* How to config entity creation with apache? 
	* https://hibernate.org/tools/
	* requires: hibernate.properties
	* requires: pom dependency > "core"
		* org.hibernate:hibernate-core
	* requires: pom build plugin
		* org.apache.maven.plugins:maven-compiler-plugin:3.15.0
		* executions > id, phase, goals 
		* configuration > outputDirectory, configFile, detectAnnotations
		* dependencies > ojdbc11

* How to create entities with apache?
	* may need to wipe out old m2 caches
		* mvn clean install -U
		* mvn dependency:purge-local-repository -DmanualInclude=org.hibernate:hibernate-core
		* //.m2/repository/org/hibernate
	* run mvn command
		* mvn org.hibernate.tool:hibernate-tools-maven:hbm2java

* How to create entities with mybatis?
	* add mybatisconfig.xml 
		* https://mybatis.org/generator/configreference/xmlconfig.html
	* add plugin
	* run > mvn mybatis-generator:generate
	
* note: ERROR: java.lang.indexOutOfBoundsException: toIndex = 5 
	occured because pageable max was not reached

* AllArgsConstructor not processing for mvn build?
