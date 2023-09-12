package com.basics.securing.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class UtilityMain {

	private UtilityMain() { }

	public static final Logger LOGGER = Logger.getLogger(UtilityMain.class.getName());

	public static final String EOL = "\n";

	//#### basics
	public static String showSys( ) {
		//
		Map<String, String> mapEnv = System.getenv();
		Map<String, String> mapEnvTree = new TreeMap<>(mapEnv);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		AtomicInteger aint = new AtomicInteger();
		mapEnvTree.forEach((key, val) -> {
			//
			val = val.replace("\\", "/");
			val = val.replace("\"", "'");
			stringBuilder.append(String.format("\t%03d %-20s : %s%n", aint.incrementAndGet(), key, val));
		});
		stringBuilder.append("]\n");
		stringBuilder.append("\tUSERNAME: ").append(System.getenv("USERNAME")).append(EOL);
		return stringBuilder.toString();
	}

	public static String getFileLocal(String pathFile) {
		//
		// https://howtodoinjava.com/java/io/read-file-from-resources-folder/
		// File file = ResourceUtils.getFile("classpath:config/sample.txt")
		String txtLines = "";
		try {
			File fileLocal = new File(pathFile);
			File pathFileLocal = new File(fileLocal.getAbsolutePath());
			txtLines = Files.readString(pathFileLocal.toPath());
		}
		catch (IOException | NullPointerException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines;
	}

	public static String exposeObject(Object object) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		Set<String> setLines = new TreeSet<>();
		Method[] methods = object.getClass().getDeclaredMethods();
		List<Method> listMethods = new ArrayList<>(Arrays.asList(methods));
		listMethods.sort(Comparator.comparing(Method::getName));
		//
		int maxLen = 35;
		AtomicInteger usedMethods = new AtomicInteger();
		String frmt = "%-30s | %-35s | %02d | %s \n";
		listMethods.forEach(method -> {
			//
			String methodName = method.getName();
			boolean boolAccess = methodName.startsWith("access$") || methodName.startsWith("$$$");
			if ( !boolAccess ) {
				usedMethods.incrementAndGet();
				Object objectVal = "";
				String returnType = method.getReturnType().toString();
				if ( returnType.length() > maxLen ) {
					returnType = returnType.substring(returnType.length() - maxLen);
				}
				method.setAccessible(true);
				Object[] args;
				if ( method.getParameterCount() > 0 ) {
					if ( method.getParameterTypes()[0].getName().contains("String") ) {
						args = new Object[]{ "RANDOM: " + getRandomString(8) };
					} else if ( method.getParameterTypes()[0].getName().contains("Date") ) {
						args = new Object[]{ new Date() };
					} else if ( method.getParameterTypes()[0].getName().contains("int") ) {
						args = new Object[]{ (int) Math.round(Math.random() * 4000) };
					} else {
						String parmname = method.getParameterTypes()[0].getName();
						args = new Object[]{ parmname };
					}
				} else { args = null; }
				try {
					objectVal = method.invoke(object, args);
					if ( objectVal == null && method.getParameterCount() != 0 )
					{ objectVal = args[0]; }
				}
				catch (IllegalAccessException | InvocationTargetException ex) {
					LOGGER.info(methodName + " | " + ex.getMessage());
				}
				catch (IllegalArgumentException iae) { objectVal = "REQUIRES: " + args[0]; }
				setLines.add(
					String.format(frmt, methodName, returnType, method.getParameterCount(), objectVal));
			}
		});
		//
		stringBuilder.append(object.getClass().getName()).append(" has: [").append(usedMethods)
			.append("] methods\n\n");

		AtomicInteger atomicInteger = new AtomicInteger();
		setLines.forEach(val -> stringBuilder.append(String.format("\t %02d %s",
			atomicInteger.incrementAndGet(), val)));
		return stringBuilder + EOL;
	}

	public static ConfigurableEnvironment getEnvironment( ) {

		// context comes from webmvc starter; may be included in others
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		ConfigurableEnvironment environment = acac.getEnvironment();
		MutablePropertySources mutablePropertySources = environment.getPropertySources();

		Map<String, Object> map = new HashMap<>();
		map.put("any.prop.path", "anyproperty");
		map.put("spring.application.id", "MLG_PROG");

		MapPropertySource mapPropertySource = new MapPropertySource("testEnvironment", map);
		mutablePropertySources.addFirst(mapPropertySource);
		return environment;
	}

	public static String getRandomString(int num) {
		//
		StringBuilder txtRandom = new StringBuilder();
		Random random = new Random();
		char[] chars =
			( "1234567890abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWZYZ" ).toCharArray();
		for ( int ictr = 0; ictr < num; ictr++ ) {
			txtRandom.append(chars[random.nextInt(chars.length)]);
		}
		return txtRandom.toString();
	}
}
