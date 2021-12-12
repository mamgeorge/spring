package com.example.sb_webflux;

import com.example.sb_webflux.configuration.HandlerGreeting;
import com.example.sb_webflux.configuration.RouterAny;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.test.publisher.TestPublisher;

import static org.mockito.Mockito.mock;

/*
 * https://medium.com/@nandan.abhi10/testing-reactive-microservice-in-spring-boot-unit-testing-fe453887ffa1
 * https://medium.com/@knoldus/spring-webflux-testing-your-router-functions-with-webtestclient-22c1564468b
 * https://dev.to/asutkarpeeyush/spring-webflux-testing-with-mockito-48em
 */
public class FluxTest {

	private static final String ASSERTION = "ASSERTION";
	private static final String EOL = "\n";
	private static final String TAB = "\t";

	@Test public void test_Flux() {
		//
		String txtLines = "test_Flux" + EOL;
		//
		TestPublisher<String> testPublisher = TestPublisher.create();
		Flux<String> fluxPublish = testPublisher.flux();
		//
		Flux<String> fluxString = Flux.just("Strings etc.");
		//	Flux.subscribe(System.out::println);
		//
		txtLines += TAB + "fluxPublish: " + fluxPublish + EOL;
		txtLines += TAB + "fluxString : " + fluxString + EOL;
		//
		System.out.println(txtLines);
		Assert.isTrue(txtLines.contains("Flux"), ASSERTION);
	}

	@Test public void testHND_displayHello() {
		//
		String txtLines = "testHND_displayHello" + EOL;
		ServerRequest serverRequest = mock(ServerRequest.class);
		HandlerGreeting handlerGreeting = new HandlerGreeting();
		txtLines += TAB + "handlerGreeting: " + handlerGreeting.displayHello(serverRequest);
		//
		System.out.println(txtLines);
		Assert.isTrue(txtLines.contains("MonoJust"), ASSERTION);
	}

	@Test public void testRTR_routes() {
		//
		String txtLines = "testRTR_routes" + EOL;
		RouterFunction<ServerResponse> rf_HTML = new RouterAny().route4Html();
		RouterFunction<ServerResponse> rf_City = new RouterAny().route4City();
		txtLines += TAB + rf_HTML.toString() + EOL;
		txtLines += TAB + rf_City.toString() + EOL;
		//
		System.out.println(txtLines);
		Assert.isTrue(txtLines.length() >= 4, ASSERTION);
	}
}
