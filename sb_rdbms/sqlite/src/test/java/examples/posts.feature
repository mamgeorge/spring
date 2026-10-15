# @2026 Martin George 
# mvn -Dtest=TestRunner posts
Feature: User API for POSTs

Background: Set base url
	* url 'https://jsonplaceholder.typicode.com'

Scenario: Verify posts
	Given path 'posts'
	And header x-transaction-id = 'abc-1234'
	When method GET			# should be post!
	Then status 200
	And match response == '#array'
	And assert response.length > 0
	And assert responseTime < 10000
	And assert response[0].title.length > 4
	* print 'response.length: ', response.length
	* print 'response[10].title: ', response[0].title
	* print 'responseCookies: ', responseCookies
	* print 'requestTimeStamp: ', requestTimeStamp
	* print 'responseHeaders: ', responseHeaders['Content-Type'][0]
	* print 'responseHeaders: ', responseHeaders	