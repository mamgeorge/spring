package com.basics.securing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;

import com.basics.securing.model.Country;

// https://bezkoder.com/spring-boot-upload-csv-file/
public class CSVHelper {

	private static final Logger LOGGER = Logger.getLogger( CSVHelper.class.getName( ) );
	public static String MIME_TYPE = "text/csv";

	public static boolean hasCSVFormat( MultipartFile multipartFile ) {
		//
		boolean hasCSVFormat = false;
		if( multipartFile == null ) { } else {
			//
			if( multipartFile.getContentType( ).equals(MIME_TYPE) ) { return true; }
		}
		return hasCSVFormat;
	}

	// https://www.baeldung.com/spring-classpath-file-access
	public static InputStream getLocalFile( ) {
		//
		// new ClassPathResource(filename,this.getClass().getClassLoader());
		InputStream inputStream = null;
		String filename = "countries.csv";
		try { 
			ClassPathResource classPathResource = new ClassPathResource( filename );
			inputStream = classPathResource.getInputStream(); 
		}
		catch (IOException ex) { LOGGER.warning( ex.getMessage() ); }
		return inputStream;
	}

	// https://datahub.io/JohnSnowLabs/country-and-continent-codes-list
	public static List<Country> csvToCountries( InputStream inputStream ) {
		//
		LOGGER.info( "reading inputStream into csvToCountries" );
		List<Country> countries = new ArrayList<Country>();
		try {

			InputStreamReader isr = new InputStreamReader( inputStream, "UTF-8") ;
			BufferedReader bufferedReader = new BufferedReader( isr );
			CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
			CSVParser csvParser = new CSVParser( bufferedReader, csvFormat );
			//
			countries = new ArrayList<Country>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int ictr = 0;
			for (CSVRecord csvRecord : csvRecords) {
				//
				// System.out.println( "\t" + csvRecord.toString() );
				Country country = new Country(
					//
					// Long.parseLong( csvRecord.get("id") ),
					Long.valueOf( ++ictr ),
					csvRecord.get("continent"),
					csvRecord.get("abbr"),
					csvRecord.get("country"),
					csvRecord.get("code2"),
					csvRecord.get("code3"),
					Long.parseLong( csvRecord.get("number") )
				);
				countries.add(country);
			}
			System.out.println( "\n#### countries.size(): " + countries.size() + " ####\n" );
		}
		catch (IOException ex) { LOGGER.warning( ex.getMessage() ); }
		return countries;
	}
}