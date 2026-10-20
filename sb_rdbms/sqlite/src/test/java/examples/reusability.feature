Feature: Verify reusability

Background: Initialize stuff
	Given url 'https://dummyjson.com/auth/'	
	* def reqLogin = { username: 'isabellad', password: 'isabelladpass', expiresInMins: '30' }
 	* def signinToken = callonce read('login.feature@login') reqLogin
	* def authToken = signinToken.response.accessToken

Scenario: #1 verify authenticated call

	Given path 'me'
	And header Content-type = 'application/json; charset=UTF-8'
	And header x-access-token = authToken
	And header Authorization = 'Bearer ' + authToken
	When method GET
	Then status 200
	And print response
	And print response.address.city

Scenario: #2 verify authenticated call
	Given path 'me'
	And header Content-type = 'application/json; charset=UTF-8'
	And header x-access-token = authToken
	And header Authorization = 'Bearer ' + authToken
	When method GET
	Then status 200
	And print response.bank.cardType