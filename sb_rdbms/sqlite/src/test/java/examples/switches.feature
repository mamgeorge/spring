Feature: Verify posts api for mysocial
	# mvn test "-Dkarate.options=classpath:examples/switches.feature" -Dtest=TestRunner -Dkarate.env=dev
	# in this case, dev uses response[9].email, int uses response.users[9].email

Background: Initialize stuff
	* configure retry = { count: 10, interval: 5000 }

Scenario: Verify users
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
