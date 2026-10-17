# @2026 Martin George 
# mvn -Dtest=TestRunner examples
Feature: User API for Loops

Background: Set base url
	* url 'https://jsonplaceholder.typicode.com'

Scenario Outline: #1 Verify examples
	Given path 'posts'
	And path <postId>
	When method GET
	Then status 200
	And match response contains { "userId": '#number', "id": <postId>, "title":  '#string'  }

	Examples:
	| postId |  
	|  1	|
	|  2	|
	|  3	|

Scenario Outline: #2 Verify that a new post for userID <userId> can be created
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And request { title: <title>, body: <body>, userId: <userId> }
	When method POST
	Then status 201
	And match response == { id: '#number', title: <title>,  body: <body>,  userId: <userId> }

	Examples:
	| userId  |  title	  | body  |
	|  1	  |  title1	 | body1 |
	|  2	  |  title2	 | body2 |
	|  3	  |  title3	 | body3 |

Scenario Outline: #3 Verify that a new post for userID <userId> can be created
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And request { title: <title>, body: <body>,	userId: <userId> }
	When method post
	Then status 201
	And match response == { id: '#number', title: <title>,  body: <body>,  userId: <userId> }
	
	Examples:
	| read('posts_data.json')|	
