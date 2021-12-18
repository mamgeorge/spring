package com.basics.securing; // .controller;

import java.util.List;
import java.util.logging.Logger;
import java.io.InputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.basics.securing.model.Country;
import com.basics.securing.services.ICountryService;
import com.basics.securing.utils.CSVHelper;

@RestController
public class AnyController {
	//
	private static final String RETURN_LINK = "<br /><a href = '/'>return</a><br />";
	private static final Logger LOGGER = Logger.getLogger( AnyController.class.getName( ) );
	private static final int MAX_DISPLAY = 20;
	
	@Autowired ICountryService countryService;

	@RequestMapping(method = RequestMethod.GET, value = { "/checks"} )
	public String getChecks() {
		//
		LOGGER.info( "getChecks" );
		return "Swagger: Hello World" + RETURN_LINK;
	}

	@RequestMapping(value = "/count")
	public ResponseEntity<String> getCount( ) throws IOException {
		//
		LOGGER.info( "getCount" );
		List<Country> countries = (List<Country>) countryService.findAll();
		int countrySize = 0;
		if (countries == null) { } else { countrySize = countries.size(); }
		String message = "countries.size(): " + countrySize;
		LOGGER.info( message );
		// return ResponseEntity.ok( message + RETURN_LINK );
		return ResponseEntity.status(HttpStatus.OK).body( message + RETURN_LINK );
	}
	
	@GetMapping(value = "/countries")
	public ModelAndView getCountries( ) throws IOException {
		//
		LOGGER.info( "getCountries" );
		List<Country> countryList = (List<Country>) countryService.findSome( MAX_DISPLAY );
		String message = "countryList.size(): " + countryList.size();
		LOGGER.info( message );
		// return ResponseEntity.ok( message + RETURN_LINK );
		ModelAndView mav = new ModelAndView();
		mav.setViewName("countries");
		mav.addObject("countryList", countryList);
		mav.addObject("countriesTotal", countryService.getTotal() );
		// mav.addObject("count", countryList.size() );
		return mav;
	}

	@RequestMapping(value = "/country/{idPath}")
	public ResponseEntity<String> getCountryPath(@PathVariable Long idPath) throws IOException {
		//
		LOGGER.info( "getCountryPath" );
		Country country = countryService.findById(idPath);
		String countryText = "";
		if (country == null) { } else { countryText = country.toString(); }
		String message = "country.toString(): " + countryText + " / " + idPath;
		LOGGER.info( message );
		return ResponseEntity.status(HttpStatus.OK).body( message + RETURN_LINK );
	}
	
	@RequestMapping(value = "/country")
	public ResponseEntity<String> getCountryParm(@RequestParam Long id) throws IOException {
		//
		LOGGER.info( "getCountryParm" );
		Country country = countryService.findById(id);
		String countryText = "";
		if (country == null) { } else { countryText = country.toString(); }
		String message = "country.toString(): " + countryText + " / " + id;
		LOGGER.info( message );
		return ResponseEntity.status(HttpStatus.OK).body( message + RETURN_LINK );
	}	
	
	@RequestMapping( "/uploader" )
	public ResponseEntity<String> getUploader( ) {
		//
		String message = "";
		ResponseEntity<String> responseEntity = null;
		LOGGER.info( "getUploader" );
		message = "Attempting default csv file!";
		try {
			countryService.load( CSVHelper.getLocalFile() );
			message = "Uploaded default successfully!";
			LOGGER.info( message );
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(message + RETURN_LINK);
		}
		catch (Exception ex) {
			message = "Could not upload the default!";
			LOGGER.warning( message );
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message + RETURN_LINK);
		}
		return responseEntity;
	}
	
	// https://bezkoder.com/spring-boot-upload-csv-file/
	@RequestMapping( "/uploaded" )
	public ResponseEntity<String> getUploaded( @RequestParam("file") MultipartFile multipartFile ) {
		//
		String message = "";
		ResponseEntity<String> responseEntity = null;
		LOGGER.info( "getUploaded" );
		if ( CSVHelper.hasCSVFormat( multipartFile ) ) {
			//
			try {
				InputStream inputStream = multipartFile.getInputStream( );
				countryService.load( inputStream );
				message = "Uploaded file successfully: " + multipartFile.getOriginalFilename( );
				LOGGER.info( message );
				responseEntity = ResponseEntity.status(HttpStatus.OK).body( message+ RETURN_LINK);
			}
			catch (Exception ex) {
				message = "Could not upload the file: " + multipartFile.getOriginalFilename();
				LOGGER.warning( ex.getMessage( ) );
				responseEntity = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body( message+ RETURN_LINK);
			}
		}
		else {
			//
			message = "Could not upload the file!" + RETURN_LINK;
			LOGGER.warning( message );
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		return responseEntity;
	}	
}
