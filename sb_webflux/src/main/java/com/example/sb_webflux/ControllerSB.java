package com.example.sb_webflux;

import com.example.sb_webflux.model.City;
import com.example.sb_webflux.model.ICityService;
import java.time.Instant;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// spring-boot-starter-web conflicts with spring-boot-starter-webflux
// Controller returns Dispatch or VC ViewController; needs ResponseBody when returning objects to bypass VC
// RestController returns domain objects; only handles webpages with ModelAndView
@RestController
public class ControllerSB {

	@Autowired private ICityService cityService;
	private static final String RETURN = "<br /><a href = '/index'>return</a>";

	@GetMapping("/")
	public Publisher<String> showHome() {
		//
		String txtLines = "Home Page! " + Instant.now() + RETURN;
		Mono<String> mono = Mono.just(txtLines);
		return mono;
	}

	@GetMapping("/home") // cant use ModelAndView in webflux; have to use Router
	public String home() {
		//
		System.out.println("home");
		// ModelAndView MAV = new ModelAndView("index", new HashMap<>());
		return "home";
	}

	@GetMapping("/showCity/{id}")
	private Mono<City> showCity(@PathVariable String id) {
		//
		Long cityId = Long.parseLong(id);
		Mono<City> mono = cityService.findById(cityId);
		return mono;
	}

	@GetMapping("/showCities")
	private Flux<City> showCities() {
		//
		Flux<City> cities = cityService.findAll();
		return cities;
	}
}

