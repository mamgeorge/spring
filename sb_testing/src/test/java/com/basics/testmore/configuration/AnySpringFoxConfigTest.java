package com.basics.testmore.configuration;

import com.basics.testmore.util.UtilityMain;
import org.junit.jupiter.api.Test;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnySpringFoxConfigTest {

	@Test void customApi( ) {

		SpringFoxConfig anySpringFoxConfig = new SpringFoxConfig();
		Docket docket = anySpringFoxConfig.customApi();

		String txtLines = UtilityMain.exposeObject(docket);
		System.out.println(txtLines);
		assertNotNull(docket);
	}
}
