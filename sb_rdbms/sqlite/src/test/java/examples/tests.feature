# @2026 Martin George 
# mvn -Dtest=TestRunner tests
Feature: User API

Background:
	* url 'https://jsonplaceholder.typicode.com'

Scenario: List all users
	Given path 'users'
	When method GET
	Then status 200
	And match response == '#array'
	* print 'response.length: ', response.length

@ignore
Scenario: Get Name One 
	Given path 'users/1'
	When method GET
	Then status 200
	And match response.name == 'Leanne Graham'

@ignore
Scenario: Get Name Two
	Given path 'users/2'
	When method GET
	Then status 200
	And match response.name == 'Ervin Howell'

@ignore
Scenario Outline: Get Name Any 
	Given path 'users/<id>'
	When method GET
	Then status 200
	And match response.name == '<name>'

    Examples:
	| id | name				|
	| 2  | Ervin Howell		|
	| 3  | Clementine Bauch	|
	| 9  | Glenna Reichert	|
		
