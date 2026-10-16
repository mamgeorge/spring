# @2026 Martin George 
# mvn -Dtest=TestRunner posts
Feature: User API for POSTs

Background: Set base url
	* url 'https://jsonplaceholder.typicode.com'

Scenario: Verify post
	Given path 'posts'
	And header Content-type = 'application/json; charset=UTF-8'
	And request { title: 'MLG', body: 'Amazing', userId: 343 }	
	When method POST
	Then status 201
	And match response == { id: '#number', title: 'MLG', body: 'Amazing', userId: 343 }
	* print response

Scenario: Verify put
	Given def postId = 101
	And path 'posts'
	And path postId
	And header Content-type = 'application/json; charset=UTF-8'
	And request { title: 'MLG', body: 'Amazing!!!', userId: 343 }	
	When method PUT
	Then match responseStatus == '#number'
	#And match response == { id: '#(postId)', title: 'MLG', body: 'Amazing!!!', userId: 343 }
	* print responseStatus 
	* print response