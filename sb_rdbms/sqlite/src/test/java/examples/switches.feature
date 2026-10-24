Feature: Verify posts api for mysocial
	# mvn test "-Dkarate.options=classpath:examples/switches.feature" -Dtest=TestRunner -Dkarate.env=dev
	# in this case, dev uses response[9].email, int uses response.users[9].email

Background: Initialize stuff
	* configure retry = { count: 10, interval: 5000 }

Scenario: #1 Verify users, with retry
	Given url postService
	Given path 'users'
	And retry until responseStatus == 200
	When method GET
	* print 'print: ' + karate.toString( response )
	* print 'response.length: ', response.length
	* print 'response[0].name: ' + karate.toString(response[0].name)	

@ignore
Scenario: #2 Verify users with details
	* print 'env: ' + env
	Given url postService
	Given path 'users'
	When method GET
	Then status 200
	And match response == '#? karate.typeOf(_) == "map" || karate.typeOf(_) == "list"'
	* print 'varTime: ' + varTime
	* def valType = karate.typeOf(response)
	* print 'typeOf: ' + valType
	* def valEmail = ''
	* if (valType == 'list') { valEmail = response[9].email } else { valEmail = response.users[9].email }
	* print 'email: ' + valEmail