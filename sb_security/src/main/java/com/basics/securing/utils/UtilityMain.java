package com.basics.securing.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class UtilityMain {

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
			// stringBuffer.append("{\"" + key + "\":\"" + val + "\"},");
			stringBuilder.append(String.format("\t%03d %-20s : %s\n", aint.incrementAndGet(), key, val));
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
}
