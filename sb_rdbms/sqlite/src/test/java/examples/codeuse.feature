# requires server for 'https://jsonplaceholder.typicode.com'. 

Feature: java interop

Background: bridge getCustMinis
	* def pathFile = 'com.basics.dbsqlite.DbHelper'
	* def getCustMinis = function(args) { return (Java.type(pathFile)).getCustomerMinis(args); }
	# def getMethod = function(args) { return new (Java.type(pathFile))().getCustomerMinis(args); }

Scenario: #1 Sqlite call getCustMinis	
	* def timeNow = new Date().toISOString()
	* print timeNow 	
	* def result = call getCustMinis "SELECT * FROM Customers ORDER BY firstName ASC"
	* print 'typeOf: ' + karate.typeOf(result)	
	# json jsons = result
	* def jsons = karate.fromString(result)
	* print 'typeOf: ' + karate.typeOf(jsons)		
	* print jsons
	* print 'jsons.length: ' + jsons.length
	* def firstnames = $jsons[*].firstname
	* print firstnames	
	* eval jsons.forEach( ndx => karate.log(ndx.firstname) )

@ignore
Scenario: #2 Mongo Call

	Given url 'http://localhost:3000'
  	Given path 'register'
	And header Content-type = 'application/json; charset=UTF-8'
	
	* def randomuuid = function(){ return java.util.UUID.randomUUID() + '' }
	* def email =  lastName + "@mymail.com"
	* def lastName = call randomuuid
	And request {email: '#(email)' , password: '12345', first_name: "xyz123", last_name: '#(lastName)' }
	When method POST
	Then status 201
	And match response contains {first_name: 'xyz123' } 
  
	* def result = call getUserRecord {db: "test", coll: "users", email: '#(email)'}
	* print result
	* json jresult = result
	* print jresult
	* match jresult contains { "last_name": '#(lastName)' }
	* def nowtime = Date.now()
	* assert jresult.created_at.$date < nowtime 

