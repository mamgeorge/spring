# @2026 Martin George 
# mvn -Dtest=TestRunner examples
Feature: User API for Loops

Background: Set base url
	* url 'https://jsonplaceholder.typicode.com'

Scenario Outline: #1 loop attribute
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

@ignore
Scenario Outline: #2 loop attributes
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

@ignore
Scenario Outline: #3 read data from file
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And request { title: <title>, body: <body>,	userId: <userId> }
	When method post
	Then status 201
	And match response == { id: '#number', title: <title>,  body: <body>,  userId: <userId> }
	
	Examples:
	| read('posts_data.json')|	

@ignore
Scenario Outline: #4 request attributes embedded
	Given path 'posts'
	And def rqst = {userId : '#(userId)' , body:'#(body)', title:'#(title)' }
	And header Content-type = 'application/json; charset=UTF-8'
	And request rqst
	When method POST
	Then status 201
	And match response == { id: '#number', title: <title>,  body: '#(body)',  userId: <userId> }

	Examples:
		| userId!	| title		| body  |
		| 1			| title1	| body1 |
		| 2			| title2	| body2 |
		| 3			| title3	| body3 | 	

@ignore
Scenario Outline: #5 request object embedded, dynamic attribute
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And def rqst1 = {userId : '#(user.userId)' , body:'#(user.body)', title:'#(user.title)' }
	And set rqst1.hashtag = 'database'
	And request rqst1
	When method POST
	Then status 201
    And match response ==
	""" { id: '#number', title: '#(user.title)', 
		body: '#(user.body)',  userId: '#(user.userId)', hashtag: '##string' } """
	* print response

	Examples:
	| user!											|
	| {userId: 1, title: 'title1', body: 'body1'}	|
	| {userId: 2, title: 'title2', body: 'body2'}	|

@ignore
Scenario Outline: #6 by condition  
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And def rqst1 = {userId : '#(user.userId)' , body:'#(user.body)', title:'#(user.title)' }
	And def setHashtag = function(k, v){ rqst1[k] = v }
	And if(rqst1.userId == 1) { setHashtag('hashtag', 'manual') } else { setHashtag('hashtag', 'database') }
	And request rqst1
	When method POST
	Then status 201
    And match response ==
	""" { id: '#number', title: '#(user.title)', 
		body: '#(user.body)',  userId: '#(user.userId)', hashtag: '##string' } """
	* print response

	Examples:
	| user!											|
	| {userId: 1, title: 'title1', body: 'body1'}	|
	| {userId: 2, title: 'title2', body: 'body2'}	|	

@ignore
Scenario Outline: #7 delete
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And def rqst1 = {userId : '#(user.userId)' , body:'#(user.body)', title:'#(user.title)' }
	* def setHashtag = function(k, v){ rqst1[k] = v }
	And eval delete rqst1.body
	And request rqst1
	When method POST
	Then status 201
    And match response ==
	""" { id: '#number', title: '#(user.title)', 
		userId: '#(user.userId)', hashtag: '##string' } """
	* print response

	Examples:
	| user!											|
	| {userId: 1, title: 'title1', body: 'body1'}	|
	| {userId: 2, title: 'title2', body: 'body2'}	|		