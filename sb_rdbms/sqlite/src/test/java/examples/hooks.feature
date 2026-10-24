Feature: Verify posts api for mysocial

Background: Initialize stuff
	* configure retry = { count: 10, interval: 5000 }
	* configure afterScenario = function(){ karate.log('#### AFTER SCENARIO:', karate.scenario.name); }	
	* configure afterFeature = function(){ karate.log('#### AFTER FEATURE:', karate.feature.name); }

Scenario: Verify #1 users works
	Given url postService
	Given path 'users'
	And retry until responseStatus == 200
	When method GET
	And match response == '#array'
	* print 'typeOf: ' + karate.typeOf(response)
	* print 'name: ' + response[0].name

Scenario: Verify #2 with retry
	Given url postService
	Given path 'users'
	And retry until responseStatus == 200
	When method GET
	* print 'typeOf: ' + karate.typeOf(response)
	* print 'name: ' + response[1].name