# @2026 Martin George 
# mvn -Dtest=TestRunner https
Feature: User API for GETs

Background: Set base url
	* url 'https://jsonplaceholder.typicode.com'

Scenario: Verify GETs
	Given path 'posts'
	And header x-transaction-id = 'abc-1234'
	When method GET			 
	Then status 200
	And match response == '#array'
	And match response[0] == { "userId": '#number', "id": '#number', "title": "#string", "body": "#string" } 
	And match response[*] contains { "userId": 1, "id": '#number', "title": "#string", "body": "#string" } 
	And match each response == { "userId": '#number', "id": '#number', "title": "#string", "body": "#string" }
						
	And assert response.length > 0
	And assert responseTime < 10000
	And assert response[0].title.length > 4
	* print 'response.length: ', response.length
	* print 'response[10].title: ', response[0].title
	* print 'responseCookies: ', responseCookies
	* print 'requestTimeStamp: ', requestTimeStamp
	* print 'responseHeaders: ', responseHeaders['Content-Type'][0]
	* print 'responseHeaders: ', responseHeaders	