package com.basics.testmore.configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.logging.Logger;

import static com.basics.testmore.util.UtilityMain.EOL;
import static com.basics.testmore.util.UtilityMain.TAB;

@Component // ensure the SpringApplication has @EnableScheduling
public class ScheduledTasks {

	private static final Logger LOGGER = Logger.getLogger(ScheduledTasks.class.getName());
	private static final int LOG_SECONDS_5 = 5 * 1000;
	private static final int LOG_MINUTES = 60 * 1000;
	private static final int LOG_HOURLY = 60 * 60 * 1000;
	private static final int LOG_DAILY = 24 * 60 * 60 * 1000;
	private static final String FRMT = "######## %-13s: %-30s %04d";
	private static final String PREFIX_SEC = TAB + FRMT + EOL;
	private static final String PREFIX_DEF = EOL + TAB + FRMT;
	private int ictr = 0;

	// @Scheduled( fixedRate = LOG_SECONDS_5 )
	public void logSeconds_5( ) {
		System.out.printf(PREFIX_SEC, "LOG_SECONDS_5", Instant.now(), ++ictr);
	}

	@Scheduled( initialDelay = LOG_MINUTES, fixedRate = LOG_MINUTES )
	public void logMinutes( ) {
		LOGGER.info(String.format(PREFIX_DEF, "LOG_MINUTES", Instant.now(), ++ictr));
	}

	@Scheduled( initialDelay = LOG_DAILY, fixedRate = LOG_DAILY ) // fixedDelay
	public void logDaily( ) {
		LOGGER.info(String.format(PREFIX_DEF, "LOG_DAILY", Instant.now(), ++ictr));
	}

	// @Scheduled( cron="5 * * * * 2") // Tue
	@Scheduled( cron = "0 0 23 * * 6,7" )
	public void logWeekend( ) {
		LOGGER.info(String.format(PREFIX_DEF, "LOG_WEEKEND", Instant.now(), ++ictr));
	}
}
