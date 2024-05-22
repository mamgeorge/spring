package com.example.embedded.configuration;

import com.example.embedded.model.City;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilitiesTest {

	@Test void getSqlInserts_test( ) {

		String txtLines = getSqlInserts(City.class, 10);
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	// utils
	public static String getSqlInserts(Class clazz, final int lenRows) {

		StringBuilder st = new StringBuilder("INSERT INTO ");

		Annotation[] annotationTbl = clazz.getAnnotations();
		Arrays.asList(annotationTbl).forEach(annotation -> {
			if ( annotation.annotationType() == Table.class ) {
				st.append(( (Table) annotation ).name() + " ");
			}
		});
		st.append(getSqlColumns(clazz));
		st.append("VALUES \n");
		st.append(getSqlValues(clazz, lenRows));
		return st.toString();
	}

	private static String getSqlColumns(Class clazz) {

		StringBuilder sc = new StringBuilder("( ");
		Field[] fields = clazz.getDeclaredFields();
		AtomicInteger acol = new AtomicInteger();
		Arrays.stream(fields).forEach(field -> {

			Annotation[] annotationCol = field.getAnnotations();
			Arrays.stream(annotationCol).forEach(annotation -> {

				if ( annotation.annotationType() == Column.class ) {
					acol.incrementAndGet();
					sc.append(( (Column) annotation ).name());
					if ( acol.get() >= fields.length ) { sc.append(" "); } else { sc.append(", "); }
				}
			});
		});
		sc.append(" ) ");
		return sc.toString();
	}

	private static String getSqlValues(Class clazz, int lenRows) {

		Random rnd = new Random();
		StringBuilder sv = new StringBuilder();
		AtomicInteger arow = new AtomicInteger();
		Field[] fields = clazz.getDeclaredFields();
		int maxLng = 1000;
		int maxStr = 20;
		boolean[] firstField = { true };

		for ( int rctr = 0; rctr < lenRows; rctr++ ) {

			sv.append("( ");
			AtomicInteger aval = new AtomicInteger();
			Arrays.stream(fields).forEach(field -> {

				int valInt = rnd.nextInt(maxLng);
				long valLng = rnd.nextLong(maxLng);
				String string = getRndString(maxStr);
				Date date = new Date();
				Timestamp timestamp = Timestamp.from(Instant.now());

				aval.incrementAndGet();
				if ( firstField[0] ) { sv.append(arow.incrementAndGet()); firstField[0] = false; } else {
					if ( field.getType().toString().contains("int") ) {
						sv.append(valInt);
					} else if ( field.getType().toString().contains("long") ) {
						sv.append(valLng);
					} else if ( field.getType().toString().contains("Long") ) {
						sv.append(valLng);
					} else if ( field.getType().toString().contains("String") ) {
						sv.append(string);
					} else if ( field.getType().toString().contains("Date") ) {
						sv.append(date);
					} else if ( field.getType().toString().contains("Timestamp") ) {
						sv.append(timestamp);
					} else {
						System.out.println("ERROR: " + field.getType() + " NOT ACCOUNTED FOR!");
						aval.incrementAndGet();
					}
				}
				if ( aval.get() >= fields.length ) { sv.append("\t"); } else { sv.append(",\t"); }
			});
			if ( rctr >= lenRows - 1 ) { sv.append(");\n"); } else { sv.append("),\n"); }
		}
		return sv.toString();
	}

	public static String getRndString(int lenMax) {

		String txtLine = "";
		int lenMin = 10;
		String characters = "abcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		int lenRnd = rnd.nextInt(lenMax);
		if ( lenRnd < lenMin ) { lenRnd = 5; }

		for ( int ictr = 0; ictr < lenRnd; ictr++ ) {

			int rndTxt = rnd.nextInt(characters.length());
			txtLine += characters.substring(rndTxt, rndTxt + 1);
		}
		return txtLine;
	}
}
