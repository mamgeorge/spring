// workspace\github\spring_annotations\src\main\java\com\basics\controller\BasicsController.java

package com.basics.controller;

import com.basics.model.City;
import com.basics.services.ICityService;
import com.basics.util.UtilityMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@RestController
public class BasicsController {

	@Autowired private ICityService cityService;
	@Autowired private ApplicationContext applicationContext;

	@Value("${server.servlet.context-path}") private String CONTEXT_PATH;
	public String getContextPath() { return CONTEXT_PATH; }

	private static final Logger LOGGER = Logger.getLogger(BasicsController.class.getName());
	private static final String RETURN_LINK = "<br /><a href = '/' >return</a><br />";
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 5;

	@GetMapping({"/", "/index"})
	public ModelAndView root(Model model) {
		//
		System.out.println("index");
		return new ModelAndView("index", new HashMap<>());
	}

	@GetMapping("/cities")
	public ModelAndView showCities() {
		//
		// Set<Integer> subset = ImmutableSet.copyOf(Iterables.limit(set, MAX_DISPLAY));
		System.out.println("cities");
		List<City> cities = cityService.findAll();
		List<City> subCities = new ArrayList<City>(cities.subList(0, MAX_DISPLAY));
		Collections.sort(subCities, Comparator.comparing(City::getName));
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
		try { longId = Long.parseLong(id); } catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = Long.valueOf(SAMPLE_ITEM);
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
		Long longId = null;
		try { longId = Long.parseLong(id); } // id is normal; id.get() used with Optional
		catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = Long.valueOf(SAMPLE_ITEM);
		}
		City city = cityService.findById(longId);
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("city", city);
		modelAndView.setViewName("showCity");
		return modelAndView;
	}

	@GetMapping("/timer") public String showTimer() {
		//
		System.out.println("timer");
		System.out.println(UtilityMain.showTime());
		return UtilityMain.showTime() + RETURN_LINK;
	}

	@GetMapping("/utils") public String showUtils() {
		//
		String txtlines = "";
		System.out.println("utils");
		txtlines = UtilityMain.getFileLocal("", "<br />");
		// txtlines = UtilityMain.getZipFileList( "" , "<br />" );
		// txtlines = UtilityMain.getXmlFileNode( "" , "" , "" );
		// txtlines = UtilityMain.convertXml2Json( "" );
		// txtlines = UtilityMain.convertJson2Xml( "" );
		// txtlines = UtilityMain.formatXml( UtilityMain.convertJson2Xml( "" ) );
		System.out.println(txtlines);
		return RETURN_LINK + txtlines + RETURN_LINK;
	}

	@GetMapping("/exits") public void exits() {
		//
		System.out.println("EXIT");
		SpringApplication.exit(applicationContext);
		System.exit(0);
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