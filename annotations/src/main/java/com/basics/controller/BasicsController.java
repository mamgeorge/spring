// workspace\github\spring_annotations\src\main\java\com\basics\controller\BasicsController.java

package com.basics.controller;

import com.basics.model.City;
import com.basics.services.ICityService;
import com.basics.util.UtilityMain;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.logging.Logger;

import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@RestController
public class BasicsController {

	@Autowired
	private ICityService cityService;
	@Autowired
	private ApplicationContext applicationContext;

	@Value("${server.servlet.context-path}")
	private String CONTEXT_PATH;

	public String getContextPath() {
		return CONTEXT_PATH;
	}

	private static final Logger LOGGER = Logger.getLogger(BasicsController.class.getName());
	private static final String RETURN_LINK = "<br /><a href = '/' >return</a><br />";
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 5;

	@GetMapping({"/", "/index"})
	public ModelAndView root() {
		//
		System.out.println("index");
		return new ModelAndView("index", new HashMap<>());
	}

	@GetMapping({"/entity"})
	public ResponseEntity<MultiValueMap<String,String>> showEntity() {

		System.out.println("entity");
		MultiValueMap<String, String> MVP = new LinkedMultiValueMap<>();
		MVP.add(HttpHeaders.ACCEPT_ENCODING, MimeTypeUtils.APPLICATION_JSON_VALUE);
		MVP.add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);

		ResponseEntity<MultiValueMap<String,String>> responseEntity
				= ResponseEntity.ok(MVP);
		return responseEntity;
	}

	@GetMapping("/cities")
	public ModelAndView showCities() {
		//
		// Set<Integer> subset = ImmutableSet.copyOf(Iterables.limit(set, MAX_DISPLAY));
		System.out.println("cities");
		List<City> cities = cityService.findAll();
		List<City> subCities = new ArrayList<>(cities.subList(0, MAX_DISPLAY));
		subCities.sort(Comparator.comparing(City::getName));
		//
		HashMap<String, Object> params = new HashMap<>();
		params.put("cities", subCities);
		params.put("cityCount", cities.size());
		return new ModelAndView("showCities", params);
	}

	@GetMapping("/cityIds")
	public ModelAndView showCityIds(@RequestParam(name = "id") String id) {
		//
		System.out.println("city: [" + id + "]");
		Long longId = null;
		try {
			longId = Long.parseLong(id);
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = (long)SAMPLE_ITEM;
		}
		City city = cityService.findById(longId);
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("showCity");
		modelAndView.addObject("city", city);
		return modelAndView;
	}

	@RequestMapping("/cityPth/{id}")
	public ModelAndView showCityPth(@PathVariable String id) {
		/*
			https://stackoverflow.com/questions/36325529/spring-controller-method-called-twice
		
			@GetMapping( value = { "/cityPth", "/cityPth/{id}" } )
			@RequestMapping( value = "/cityPth/{id}" , method = RequestMapping.GET )
			@RequestMapping(path = "/cityPth/{id}", produces = "application/json; charset=UTF-8")
			public ModelAndView showCityPth( @PathVariable Optional<String> id ) {
			public ModelAndView showCityPth( @PathVariable(required = false) String id ) {
			public ModelAndView showCityPth( @PathVariable("id") String id ) {
		*/
		System.out.println("city: [" + id + "]");
		Long longId;
		try {
			longId = Long.parseLong(id);
		} // id is normal; id.get() used with Optional
		catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = (long) SAMPLE_ITEM;
		}
		City city = cityService.findById(longId);
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("city", city);
		modelAndView.setViewName("showCity");
		return modelAndView;
	}

	@GetMapping("/timer")
	public String showTimer() {
		//
		System.out.println("timer");
		System.out.println(UtilityMain.showTime());
		return UtilityMain.showTime() + RETURN_LINK;
	}

	@GetMapping("/utils")
	public String showUtils() {
		//
		String txtlines = "";
		System.out.println("utils");
		txtlines = UtilityMain.getFileLocal("", "<br />");
		System.out.println(txtlines);
		return RETURN_LINK + txtlines + RETURN_LINK;
	}

	@GetMapping("/ping")
	public String ping() {

		System.out.println("ping");
		// SpringApplication exit(applicationContext);
		// System exit(0);
		return "ping";
	}

	@CrossOrigin
	@RequestMapping("/errors") // not "/error"
	public ModelAndView handleError(HttpServletRequest request) {
		//
		Object status = request.getAttribute(ERROR_STATUS_CODE);
		LOGGER.warning("status: " + status);
		if (status != null) {
			//
			String statusCode = status.toString();
			LOGGER.warning("statusCode: " + statusCode);
			// if (statusCode == HttpStatus.NOT_FOUND.value()) {
			// if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
		}
		return new ModelAndView("error", new HashMap<>());
	}
}