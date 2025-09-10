package com.basics.dbsqlite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static org.aspectj.util.LangUtil.EOL;

public class ReflectionHelper {

	public static final Logger LOGGER = Logger.getLogger(ReflectionHelper.class.getName());
	public static final Random RANDOM = new Random();

	public static String getField(Object object, String nameField) {

		String txtLine = "";
		try {
			Class<?> clazz = object.getClass();
			Field field = clazz.getDeclaredField(nameField);
			field.setAccessible(true);
			Object objectField = field.get(object);
			txtLine = objectField.toString();
		}
		catch (NoSuchFieldException | IllegalAccessException ex) {
			LOGGER.severe(ex.getMessage());
		}
		return txtLine;
	}

	public static Object getMethod(Class<?> clazz, String nameMethod, Object... objectParms) {

		Object objectReturn = "";
		try {
			int parmsCount = 0;
			Object objectItem = null;
			if ( objectParms == null || objectParms.length == 0 ) {
				System.out.println("...no args...");
			}
			else {
				parmsCount = objectParms.length;
			}
			Class<?>[] classArray = new Class<?>[parmsCount];
			for ( int ictr = 0; ictr < parmsCount; ictr++ ) {
				try {
					if ( objectParms == null ) { System.out.println("objectParms == null"); }
					else {
						objectItem = objectParms[ictr];
					}
				}
				catch (NullPointerException ex) {
					LOGGER.info(ex.getMessage());
				}
				if ( objectItem == null ) {
					classArray = new Class<?>[0];
					objectParms = null;
				}
				else {
					classArray[ictr] = objectItem.getClass();
				}
			}

			Object objectInstance = clazz.getDeclaredConstructor().newInstance();
			Method method = clazz.getDeclaredMethod(nameMethod, classArray);
			method.setAccessible(true);
			objectReturn = method.invoke(objectInstance, objectParms);
		}
		catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException |
		       InvocationTargetException | InstantiationException ex) {
			LOGGER.severe(ex.getMessage());
		}
		return objectReturn;
	}

	public static String exposeObject(Object object) {

		StringBuilder stringBuilder = new StringBuilder();
		Set<String> setLines = new TreeSet<>();
		Method[] methods = object.getClass().getMethods(); // getDeclaredMethods()
		List<Method> listMethods = new ArrayList<>(Arrays.asList(methods));
		listMethods.sort(Comparator.comparing(Method::getName));

		int MAXLEN = 35;
		AtomicInteger usedMethods = new AtomicInteger();
		String FRMT = "%-30s | %-35s | %02d | %s \n";
		listMethods.forEach(method -> {

			String methodName = method.getName();
			boolean boolAccess = methodName.startsWith("access$")
				|| methodName.startsWith("$$$")
				|| methodName.startsWith("hashCode")
				|| methodName.startsWith("notify")
				|| methodName.equals("getClass")
				|| methodName.equals("wait")
				|| methodName.equals("equals")
				|| methodName.equals("toString")
				;
			if ( !boolAccess ) {
				usedMethods.incrementAndGet();
				Object objectVal = "";
				String returnType = method.getReturnType().toString();
				if ( returnType.length() > MAXLEN ) {
					returnType = returnType.substring(returnType.length() - MAXLEN);
				}
				method.setAccessible(true);
				Object[] args;
				if ( method.getParameterCount() > 0 ) {
					if ( method.getParameterTypes()[0].getName().contains("String") ) {
						args = new Object[]{ "RANDOM: " + getRandomString(8) };
					}
					else if ( method.getParameterTypes()[0].getName().contains("Date") ) {
						args = new Object[]{ new Date() };
					}
					else if ( method.getParameterTypes()[0].getName().contains("int") ) {
						args = new Object[]{ RANDOM.nextInt(4000) };
					}
					else {
						String parmname = method.getParameterTypes()[0].getName();
						args = new Object[]{ parmname };
					}
				}
				else { args = null; }
				try {
					objectVal = method.invoke(object, args);
					if ( objectVal == null && ( method.getParameterCount() != 0 ) ) {
						assert args != null;
						objectVal = args[0];
					}
				}
				catch (IllegalAccessException | InvocationTargetException ex) {
					LOGGER.info(methodName + " | " + ex.getMessage());
				}
				catch (IllegalArgumentException IAE) {
					objectVal = "REQUIRES: " + Objects.requireNonNull(
						args)[0];
				}
				setLines.add(
					String.format(FRMT, methodName, returnType, method.getParameterCount(), objectVal));
			}
		});

		stringBuilder.append(object.getClass().getName()).append(" has: [").append(usedMethods)
			.append("] methods\n\n");

		AtomicInteger atomicInteger = new AtomicInteger();
		setLines.forEach(val -> stringBuilder.append(String.format("\t %02d %s",
			atomicInteger.incrementAndGet(), val)));
		return stringBuilder + EOL;
	}

	public static void putObject(Object object, String objectName, Object objectValue) {

		try {
			Class<?> clazz = object.getClass();
			Field field;
			try { field = clazz.getDeclaredField(objectName); }
			catch (NoSuchFieldException ex) {
				Class<?> superClazz = clazz.getSuperclass();
				field = superClazz.getDeclaredField(objectName);
			}
			field.setAccessible(true);
			field.set(object, objectValue);
		}
		catch (NoSuchFieldException | IllegalAccessException ex) {
			LOGGER.severe(ex.getMessage());
		}
	}

	public static String getRandomString(int num) {

		StringBuilder txtRandom = new StringBuilder();
		char[] chars =
			( "1234567890abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWZYZ" ).toCharArray();
		for ( int ictr = 0; ictr < num; ictr++ ) {
			txtRandom.append(chars[RANDOM.nextInt(chars.length)]);
		}
		return txtRandom.toString();
	}
}
