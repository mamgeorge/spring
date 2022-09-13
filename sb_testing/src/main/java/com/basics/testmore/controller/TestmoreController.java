// workspace\github\spring_annotations\src\main\java\com\basics\controller\BasicsController.java

package com.basics.testmore.controller;

import com.basics.testmore.model.City;
import com.basics.testmore.model.Country;
import com.basics.testmore.services.ICityService;
import com.basics.testmore.services.ICountryService;
import com.basics.testmore.util.UtilityMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class TestmoreController {

	@Autowired private ICityService cityService;
	@Autowired private ICountryService countryService;
	@Autowired private ApplicationContext applicationContext;
	@Autowired @Qualifier("CityBean") private City cityBean;

	@Value("${server.servlet.context-path}") private String CONTEXT_PATH;

	public String getContextPath() {
		return CONTEXT_PATH;
	}

	private static final Logger LOGGER = Logger.getLogger(TestmoreController.class.getName());
	private static final String RETURN_LINK = "<br /><a href = '/' >return</a><br />";
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 5;

	@GetMapping({"/", "/home"})
	public ModelAndView root() {
		//
		System.out.println("home");
		return new ModelAndView("home", new HashMap<>());
	}

	@GetMapping("/login") public ModelAndView login() {
		System.out.println("login authenticate");
		return new ModelAndView("login", new HashMap<>());
	}

	@GetMapping("/logins") public ModelAndView logins() {
		//
		System.out.println("logins authenticate");
		return new ModelAndView("login", new HashMap<>());
	}

	@GetMapping("/cities")
	public ModelAndView getCities() {
		//
		// Set<Integer> subset = ImmutableSet.copyOf(Iterables.limit(set, MAX_DISPLAY));
		System.out.println("getCities");
		List<City> cities = cityService.findAll();
		ArrayList<City> subCities = new ArrayList<>(cities.subList(0, MAX_DISPLAY));
		subCities.sort(Comparator.comparing(City::getName));
		//
		HashMap<String, Object> params = new HashMap<>();
		params.put("cityList", subCities);
		params.put("cityTotal", cities.size());
		return new ModelAndView("showCities", params);
	}

	@GetMapping("/cityIds")
	public ModelAndView getCityIds(@RequestParam(name = "id") String id) {
		//
		System.out.println("getCityIds: [" + id + "]");
		long longId;
		try {
			longId = Long.parseLong(id);
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = SAMPLE_ITEM;
		}
		City city = cityService.findById(longId);
		//
		System.out.println("city: [" + city + "]");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("showCity");
		modelAndView.addObject("city", city);
		return modelAndView;
	}

	@RequestMapping("/cityPth/{id}")
	public ModelAndView getCityPth(@PathVariable String id) {
		/*
			https://stackoverflow.com/questions/36325529/spring-controller-method-called-twice

			@GetMapping( value = { "/cityPth", "/cityPth/{id}" } )
			@RequestMapping( value = "/cityPth/{id}" , method = RequestMapping.GET )
			@RequestMapping(path = "/cityPth/{id}", produces = "application/json; charset=UTF-8")
			public ModelAndView showCityPth( @PathVariable Optional<String> id ) {
			public ModelAndView showCityPth( @PathVariable(required = false) String id ) {
			public ModelAndView showCityPth( @PathVariable("id") String id ) {
		*/
		System.out.println("getCityPth: [" + id + "]");
		long longId;
		try {
			longId = Long.parseLong(id);
		} // id is normal; id.get() used with Optional
		catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = SAMPLE_ITEM;
		}
		City city = cityService.findById(longId);
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("city", city);
		modelAndView.setViewName("showCity");
		return modelAndView;
	}

	@GetMapping("/cityBean")
	public ResponseEntity<String> getCityBean() {
		//
		String cityBeanName = cityBean.getName();
		System.out.println("cityBean: [" + cityBean + "]");
		return ResponseEntity.status(HttpStatus.OK).body(cityBeanName + RETURN_LINK);
	}

	@GetMapping(value = "/countries")
	public ModelAndView getCountries() {
		//
		LOGGER.info("getCountries");
		List<Country> subCountries = countryService.findSome(MAX_DISPLAY);
		//
		HashMap<String, Object> params = new HashMap<>();
		params.put("countryList", subCountries);
		params.put("countryTotal", countryService.getTotal());
		return new ModelAndView("showCountries", params);
	}

	@GetMapping(value = "/countryIds")
	public ModelAndView getCountryIds(@RequestParam(name = "id") String id) {
		//
		System.out.println("getCountryIds: [" + id + "]");
		long longId;
		try {
			longId = Long.parseLong(id);
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = SAMPLE_ITEM;
		}
		Country country = countryService.findById(longId);
		//
		System.out.println("country: [" + country + "]");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("showCountry");
		modelAndView.addObject("country", country);
		return modelAndView;
	}

	@RequestMapping(value = "/countryPth/{idPath}")
	public ResponseEntity<String> getCountryPth(@PathVariable Long idPath) {
		//
		LOGGER.info("getCountryPth: [" + idPath + "]");
		Country country = countryService.findById(idPath);
		String countryText = "";
		if (country == null) {
		} else {
			countryText = country.toString();
		}
		String message = "country.toString(): " + countryText + " / " + idPath;
		LOGGER.info(message);
		return ResponseEntity.status(HttpStatus.OK).body(message + RETURN_LINK);
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

	@CrossOrigin @RequestMapping("/errors") // not "/error"
	public ModelAndView handleError(HttpServletRequest request) {
		//
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
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