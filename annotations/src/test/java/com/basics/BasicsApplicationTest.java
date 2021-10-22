package com.basics;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.basics.util.UtilityMain.GREEN;
import static com.basics.util.UtilityMain.LOGGER;
import static com.basics.util.UtilityMain.PAR;
import static com.basics.util.UtilityMain.RESET;

// @SpringBootTest
// @RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicsApplication.class)
public class BasicsApplicationTest {
	//
	public static void main(String[] args)
	{ System.out.println(GREEN + "BasicsApplicationTest.main" + RESET); }

	@Test public void sample() {
		//
		String txtLine = GREEN + "BasicsApplicationTest.sample" + RESET;
		LOGGER.info(PAR + txtLine);
	}

	@Test public void contextLoads() { LOGGER.info(PAR + "contextLoads!"); }
}
