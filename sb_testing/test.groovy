// cd c:\workspace\github\spring_testing
// run thru shell
// > groovy test , echo \u001b[31m, \u001b[37m, ^<ESC^>[32m [32mGreen[0m

import groovy.sql.Sql

import java.time.LocalDateTime

import static java.nio.charset.StandardCharsets.UTF_8

@Grapes([
	@Grab( 'org.xerial:sqlite-jdbc:3.7.2' ),
	@Grab( 'org.apache.derby:derby:10.12.1.1' ),
	@Grab( 'org.apache.derby:derbyclient:10.13.1.1' ), // for java 8; derbyshared & derbytools for J9
	@Grab( 'mysql:mysql-connector-java:8.0.27' ),
	@Grab( 'org.hsqldb:hsqldb:2.6.1' ), // @Grab( 'com.h2database:h2' ),
	@GrabConfig( systemClassLoader=true )
])

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME

class DBase {

	// https://www.guru99.com/groovy-interview-questions.html
	// java org.apache.derby.tools.sysinfo

	//mysql	:'jdbc:mysql://mysqlUser:mysqlPass@localhost:3306/mydb',
	static def mysqlUser = System.getenv( 'MYSQL_USER' )
	static def mysqlPass = System.getenv( 'MYSQL_PASS' )

	static def driver = [

		sqlite	: 'org.sqlite.JDBC',
		derby	: 'org.apache.derby.jdbc.EmbeddedDriver',
		derbyCl	: 'org.apache.derby.jdbc.ClientDriver',
		hsql	: 'org.hsqldb.jdbc.JDBCDriver',
		mysql	: 'com.mysql.cj.jdbc.Driver',
	]

	static def server = [
		// url
		sqlite	:'jdbc:sqlite:C:/workspace/dbase/sqlite/chinook.db',
		derby	:'jdbc:derby:c:/dbase/derby/db-derby-10.12.1.1-bin/demo/databases/toursdb',
		derbyCl	:'jdbc:derby://localhost:1527/toursdb;create=true',
		hsql	:'jdbc:hsqldb:mem:cities',
		mysql   : 'jdbc:mysql://' + mysqlUser + ':' + mysqlPass + '@localhost:3306/mydb'
	]
	static def query = [

		sqlite	:'SELECT * FROM customers',
		derby	:'SELECT * FROM cities',
		hsql	:'SELECT * FROM cities',
		mysql	:'SELECT * FROM history'
	]
	static def results = [ ]
}

class Testor { static String getStr(def str) { return 'Hello ' + str } }

println '----------------'
DLM = '\n'

void basics() {
	//
	print DLM + 'basics' + DLM
	def filename = 'readme.md', ictr =0, lns = ''

	// strings
	print DLM + '1'; print ' ' + 'HelloWorld: ' + new Date()
	print DLM + '2'; print ' ' + ISO_DATE_TIME.format( LocalDateTime.now() )
	print DLM + '3'; TimeZone.getAvailableIDs().toList().subList(1, 8).sort().each { print (' ' + ++ictr + ' ' + it+ ', ') }

	// files
	print DLM + '4'; print ' ' + new File( filename ).readLines()
	print DLM + '5'; def txt = new File( filename ).text ; print ' ' + txt.substring(0, 11)
	print DLM + '6'; new File( filename ).eachLine( UTF_8.toString() ) { lns += it }; print ' ' + lns.substring(0, 11)

	// numbers
	print DLM + '7'; def val = [ 14, 35, -7, 46, 98 ].min(); print ' ' + val
	print DLM + '8'; def var = [ 14, 35, -7, 46, 98 ].max(); print ' ' + var
	print DLM + '9'; [ 49, 58, 76, 82, 88, 90 ].split { print ' ' + (String) (it > 60) }
	print DLM + '0'; print ' ' + 'DERBY_HOME: ' + System.getenv( 'DERBY_HOME' )
	print DLM + '0'; print ' ' + 'MYSQL_USER: ' + System.getenv( 'MYSQL_USER' )
	print DLM
}

void showDBase() {
	//
	print DLM + 'database' + DLM
	def sql
	//
	print DLM + 'sqlite' + DLM
	sql = Sql.newInstance ( DBase.server.sqlite, '', '', DBase.driver.sqlite )
	sql.eachRow( DBase.query.sqlite ) { print "$it.CustomerId:${it.LastName} | " }
	print DLM
	//
	try {
		print DLM + 'derby' + DLM
		sql = Sql.newInstance ( DBase.server.derby, '', '', DBase.driver.derby )
		sql.eachRow( DBase.query.derby ) { print "$it.city_id:${it.city_name} |" }
		print DLM
	}
	catch ( Exception ex ) { println "ERROR: " + ex.getMessage() }
	//
	try {
		print DLM + 'hsql' + DLM
		sql = Sql.newInstance(DBase.server.hsql, '', '', DBase.driver.hsql)
		sql.eachRow(DBase.query.hsql) { print "$it.city_id:${it.city_name} | " }
		print DLM
	}
	catch ( Exception ex ) { println "ERROR: " + ex.getMessage() }
	//
	try {
		print DLM + 'mysql' + DLM
		sql = Sql.newInstance ( DBase.server.mysql, '', '', DBase.driver.mysql )
		sql.eachRow( DBase.query.mysql ) { print "$it.eramain:${it.eramain} | " }
		print DLM
	}
	catch ( Exception ex ) { println "ERROR: " + ex.getMessage() }
}

void showProps() {
	//
	// C:\workspace\github\spring_testing\src\main\resources
	print DLM + 'readProps' + DLM
	def fileName = 'C:/workspace/github/spring/sb_testing/src/main/resources/application.yml'
	Properties properties = new Properties()
	File propertiesFile = new File( fileName )
	propertiesFile.withInputStream { properties.load( it ) }
	def val = 'datasource.platform'
	println "property: " + val + " = " + properties."$val"
	assert properties."$val" == 'h2'
	try { assert properties."$val" == 'h2' : "props should be h2" }
	catch ( AssertionError ex ) { println "ERROR: " + ex.getMessage() }
	// assert properties.'$val' == 'h4'
	print DLM
}

println Testor.getStr( System.getProperty( 'user.name' ) )
// basics()
// showDBase()
// showProps()
