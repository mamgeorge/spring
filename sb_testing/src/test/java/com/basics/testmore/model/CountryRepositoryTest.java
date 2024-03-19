package com.basics.testmore.model;

import com.basics.testmore.repository.CountryRepository;
import com.basics.testmore.util.UtilityMain;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.LOGGER;
import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest @Disabled( "avoids loading instance" )
class CountryRepositoryTest {

	@Resource private CountryRepository countryRepository;

	// @BeforeAll public void setUp( ) throws Exception { }

	@Test void testSettersGetters( ) {
		//
		Country country = new Country();
		System.out.println(UtilityMain.exposeObject(country));

		assertNotNull(country);
	}

	@Test void findAll( ) {
		//
		String txtLines = "";
		Iterable<Country> iterable = countryRepository.findAll();
		List<Country> countries = new ArrayList<Country>();
		for ( Country country : iterable ) {
			//
			txtLines += "\n";
			txtLines += "\t" + country.getId();
			txtLines += "\t" + country.getContinent();
			txtLines += "\t" + country.getAbbr();
			countries.add(country);
		}
		txtLines += PAR + countries.size();
		LOGGER.info(txtLines);
		assertNotNull(iterable);
	}
}
