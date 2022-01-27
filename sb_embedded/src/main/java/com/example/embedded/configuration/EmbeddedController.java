package com.example.embedded.configuration;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.embedded.model.City;
import com.example.embedded.model.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EmbeddedController {

	@Autowired private ICityService cityService;
	@Autowired private ApplicationContext applicationContext; //added for close

	private static final String FRMT = "<pre>\t%02d %-15s %9d</pre>\n";
	private static final String HEADER = "<h3>SbController</h3>";
	private static final String RETURN = "<br /><a href = '/home'>return</a>";
	private static final int MAX_DISPLAY = 20;

	@GetMapping({"/", "/root"})
	public String root() {
		//
		System.out.println("root");
		return Instant.now() + RETURN;
	}

	@GetMapping({"/home", "/index"})
	public ModelAndView home() {
		//
		System.out.println("index");
		ModelAndView MAV = new ModelAndView("index", new HashMap<>());
		return MAV;
	}

	@GetMapping("/showCities")
	public String showCities() {
		//
		System.out.println("showCities");
		List<City> cities = cityService.findAll();
		List<City> subCities = new ArrayList<City>(cities.subList(0, MAX_DISPLAY));
		//
		StringBuilder stringBuilder = new StringBuilder();
		subCities.forEach(city -> stringBuilder
			.append(String.format(FRMT, city.getId(), city.getName(), city.getPopulation())));
		return HEADER + stringBuilder + RETURN;
	}

	@GetMapping("/showCity")
	public City showCity() {
		//
		System.out.println("showCity");
		Long cityId = 14L; // Long.parseLong(id)
		City city = cityService.findById(cityId);
		return city;
	}

	@GetMapping("/showCity/{cityId}") // id is normal; id.get() used with Optional
	public ModelAndView showCity(@PathVariable String cityId) {
		//
		System.out.println("showCity/"+ cityId + "");
		Long longId = Long.parseLong(cityId);
		City city = cityService.findById(longId);
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("city", city);
		modelAndView.setViewName("city");
		return modelAndView;
	}

	@GetMapping("/exit") public void exit() {
		//
		System.out.println("EXIT");
		SpringApplication.exit(applicationContext);
		System.exit(0);
	}
}
