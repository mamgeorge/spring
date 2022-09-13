package com.basics.testmore.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilityExtraTest {

	public static final Logger LOGGER = Logger.getLogger(UtilityExtraTest.class.getName());

	public final static String valueId = Instant.now().toString();

	@Test void main( ) {

		UtilityExtra utilityExtra = new UtilityExtra();
		UtilityExtra.main(new String[]{ "" });
		assertNotNull(utilityExtra);
	}

	@Test void logging_fromProperties( ) {

		// should set this up in Spring App
		String LOG_FORMAT = "1[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] \n\t 2[%2$s] \n\t 3[%3$s] \n\t 4[%4$s] \n\t 5[%5$s] \n\t 6[%6$s]";
		System.setProperty("java.util.logging.SimpleFormatter.format", LOG_FORMAT);

		LOGGER.info(String.format("{%s}", "Any Message Possible, valueId={" + UtilityExtraTest.valueId + "} !"));
		assertNotNull(LOGGER);
	}

	@Test void logging_fromHandler( ) {

		Logger loggerMain = Logger.getLogger(" com.basics.testmore");
		loggerMain.setUseParentHandlers(false);

		ConsoleHandler consoleHandler = new ConsoleHandler();
		SimpleFormatterImpl simpleFormatterImpl = new SimpleFormatterImpl();
		consoleHandler.setFormatter(simpleFormatterImpl);
		loggerMain.addHandler(consoleHandler);

		loggerMain.info(String.format("\t %s", "Any Message Possible!"));
		assertNotNull(consoleHandler);
	}
}

class SimpleFormatterImpl extends SimpleFormatter {

	/*
		https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
		https://docs.oracle.com/javase/8/docs/api/java/util/logging/SimpleFormatter.html

		1 format	Formatter format string in SimpleFormatter.format property or the default format
		2 date		Date object representing event time of the log record
		3 source	string representing caller if available, otherwise the loggers name
		4 logger	loggers name
		5 level		log level
		6 message	formatted message from Formatter.formatMessage(LogRecord) method; uses java.text formatting not Formatter format
		7 thrown	string representing throwable and backtrace with newline if any, otherwise an empty string
	*/
	private static final String logFormatMeth =
		"[%1$tF %1$tT] [%2$-7s] %3$s %4$s valueId={" + UtilityExtraTest. valueId + "}, %n";

	@Override
	public synchronized String format(LogRecord logRecord) {

		return String.format(logFormatMeth,
			new Date(logRecord.getMillis()),
			logRecord.getLevel().getLocalizedName(),
			logRecord.getSourceClassName() + "." + logRecord.getSourceMethodName(),
			logRecord.getMessage()
		);
	}
}