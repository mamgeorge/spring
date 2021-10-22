/*
	cd c:\workspace\github\spring_annotations
	mvn compile
	java -cp %JAVA_TEMPCP% com.basics.util.UtilityExtra
	
	https://www.key-shortcut.com/en/writing-systems/%E6%96%87%E5%AD%97-chinese-cjk/cjk-characters-1
	https://www.compart.com/en/unicode/U+001B
	
*/
package com.basics.util;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class UtilityExtra {

	public static final String[] CODEPAGES =
		{"US-ASCII", "UTF-8", "UTF-16", "UTF-32", "Cp850", "Big5", "GB2312"};
	private static final String GRN = "\u001b[32m";
	private static final String RST = "\u001b[30m";

	public static void main(String[] args)
	{
		new UtilityExtra().codePager("约翰");
		System.out.println("DONE");
	}

	private void codePager(String txtVal) {
		//
		System.out.println(GRN + "■■■■" + RST);
		PrintStream printStream = null;
		String charSet = "";
		for (String codePage : CODEPAGES) {
			//
			charSet = StandardCharsets.UTF_8.displayName(); // charSet = codePage;
			try {
				printStream = new PrintStream(System.out, true, charSet);
				printStream.println(txtVal + "\t\t" + codePage);
			} catch (Exception ex) {
				System.err.println(ex.getMessage() + " / " + codePage);
			}
		}
	}
}
