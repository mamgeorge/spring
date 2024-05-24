package com.example.embedded.configuration;

import com.example.embedded.model.City;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Getter @Setter
public class SqlInsertsBuilder {

	private static final Random rnd = new Random();
	private static int MAXLNG = 1000;
	private static int MAXSTR = 10;
	private static long EPOCHMILLI_RNG = 2 * 10 * 1000 * 1000; // 200MM millis = .2 trillion ~ 55.56 hours

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
					if ( acol.get() >= fields.length ) { sc.append(" "); }
					else { sc.append(", "); }
				}
			});
		});
		sc.append(" ) ");
		return sc.toString();
	}

	private static String getSqlValues(Class clazz, int lenRows) {

		StringBuilder sv = new StringBuilder();
		AtomicInteger arow = new AtomicInteger();
		Field[] fields = clazz.getDeclaredFields();

		for ( int rctr = 0; rctr < lenRows; rctr++ ) {

			sv.append("( ");
			AtomicInteger aval = new AtomicInteger();
			Arrays.stream(fields).forEach(field -> {

				int valInt = rnd.nextInt(MAXLNG);
				long valLng = rnd.nextLong(MAXLNG);
				String string = "'" + getRndString(MAXSTR) + "'";
				String date = "'" + getRndDate() + "'";
				String timestamp = "'" + getRndTime() + "'";

				aval.incrementAndGet();
				String fieldType = field.getType().toString();
				boolean isFirstVal = aval.get() == 1 &&
					( fieldType.contains("int") || fieldType.contains("long") );
				if ( isFirstVal ) { sv.append(arow.incrementAndGet()); }
				else {
					if ( fieldType.contains("int") ) { sv.append(valInt); }
					else if ( fieldType.contains("long") ) { sv.append(valLng); }
					else if ( fieldType.contains("Long") ) { sv.append(valLng); }
					else if ( fieldType.contains("String") ) { sv.append(string); }
					else if ( fieldType.contains("Date") ) { sv.append(date); }
					else if ( fieldType.contains("Timestamp") ) { sv.append(timestamp); }
					else {
						System.out.println("ERROR: " + field.getType() + " NOT ACCOUNTED FOR!");
						aval.incrementAndGet();
					}
				}
				if ( aval.get() >= fields.length ) { sv.append("\t"); }
				else { sv.append(",\t"); }
			});
			if ( rctr >= lenRows - 1 ) { sv.append(");\n"); }
			else { sv.append("),\n"); }
		}
		return sv.toString();
	}

	private static String getRndDate( ) {

		long epochMilliNow = System.currentTimeMillis();
		long epochMilliRnd = epochMilliNow - EPOCHMILLI_RNG + rnd.nextLong(EPOCHMILLI_RNG * 2);
		ZonedDateTime zonedDateTime = Instant.ofEpochMilli(epochMilliRnd).atZone(ZoneId.systemDefault());
		LocalDate localDate = zonedDateTime.toLocalDate();

		return localDate.toString();
	}

	private static String getRndTime( ) {

		long epochMilliNow = System.currentTimeMillis();
		long epochMilliRnd = epochMilliNow - EPOCHMILLI_RNG + rnd.nextLong(EPOCHMILLI_RNG * 2);
		Timestamp timestamp = new Timestamp(epochMilliRnd);

		return timestamp.toString();
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
