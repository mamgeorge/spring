package com.basics.testmore.util;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

public class LoggingTest {

	public static final Logger LOGGER = Logger.getLogger(LoggingTest.class.getName());

	public final static String valueId = Instant.now().toString();

	@Test void main( ) {

		UtilityExtra utilityExtra = new UtilityExtra();
		UtilityExtra.main(new String[]{ "" });
		assertNotNull(utilityExtra);
	}

	@Test void logging_changeLevel( ) {

		// java
		Logger LOGGER_JAVA = Logger.getLogger(LoggingTest.class.getName());
		System.out.println("LOGGER_JAVA.getLevel(): " + LOGGER_JAVA.getLevel());
		Level level = Level.ALL;
		LOGGER_JAVA.setLevel(level);

		Logger LOGGER_ANON = Logger.getAnonymousLogger();
		System.out.println("LOGGER_ANON.getLevel(): " + LOGGER_ANON.getLevel());

		Logger LOGGER_GLOBAL = Logger.getGlobal();
		System.out.println("LOGGER_GLOBAL.getLevel(): " + LOGGER_GLOBAL.getLevel());

		// JUNIT
		org.junit.platform.commons.logging.Logger LOGGER_JUNIT = LoggerFactory.getLogger(LoggingTest.class);
		System.out.println("LOGGER_JUNIT.toString(): " + LOGGER_JUNIT);

		// logback
		ch.qos.logback.classic.Logger LOGGER_LOGBACK =
			(ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ROOT_LOGGER_NAME);
		System.out.println("LOGGER_LOGBACK.getLevel(): " + LOGGER_LOGBACK.getLevel());

		ch.qos.logback.classic.Level level_that = ch.qos.logback.classic.Level.toLevel("ALL");
		LOGGER_LOGBACK.setLevel(level_that);

		// stuff
		Supplier supplier = null;
		LOGGER_JAVA.info("LOGGER_THIS");
		LOGGER_LOGBACK.info("LOGGER_LOGBACK");
		assertNotNull(LOGGER_JAVA);
	}

	@Test void logging_fromProperties( ) {

		// should set this up in Spring App
		String LOG_FORMAT =
			"1[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] \n\t 2[%2$s] \n\t 3[%3$s] \n\t 4[%4$s] \n\t 5[%5$s] \n\t 6[%6$s]";
		System.setProperty("java.util.logging.SimpleFormatter.format", LOG_FORMAT);

		LOGGER.info(String.format("{%s}", "Any Message Possible, valueId={" + LoggingTest.valueId + "} !"));
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
		"[%1$tF %1$tT] [%2$-7s] %3$s %4$s valueId={" + LoggingTest.valueId + "}, %n";

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