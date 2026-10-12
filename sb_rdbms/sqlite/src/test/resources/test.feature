Feature: User API

Background:
	* url 'https://jsonplaceholder.typicode.com'

Scenario: List all users
	Given path 'users'
	When method GET
	Then status 200
	And match response == '#array'

Scenario: Get Name One 
	Given path 'users/1'
	When method GET
	Then status 200
	And match response.name == 'Leanne Graham'

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
		
