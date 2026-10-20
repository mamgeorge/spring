Feature: Verify login Authentication

Background: Initialize Authentication
	* print 'Authentication Login'
	* def reqLogon = { username: '#(username)' , password: '#(password)', expiresInMins: '#(expiresInMins)' }

@ignore
Scenario: Call login to get auth token
	* print 'ignore reqLogon: ' + reqLogon

@login @ignore
Scenario: Call login to get auth token
	Given url 'https://dummyjson.com/auth/'
	And path 'login'
	And header Content-type = 'application/json; charset=UTF-8'
	And request reqLogon
	When method POST
	Then status 200
	And print response
	And print 'response.accessToken: ' + response.accessToken
