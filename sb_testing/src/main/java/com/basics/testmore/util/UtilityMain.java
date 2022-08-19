package com.basics.testmore.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static javax.xml.xpath.XPathConstants.NODESET;
import static javax.xml.xpath.XPathConstants.STRING;

public class UtilityMain {
	//
	public static final Logger LOGGER = Logger.getLogger(UtilityMain.class.getName());

	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String CONTENTTYPE_FORM = "application/x-www-form-urlencoded";
	public static final String CONTENTTYPE_MULTI = "multipart/form-data; boundary=";
	public static final String COLORS = "Black: \u001b[30m, Red: \u001b[31m, Green: \u001b[32m, Yellow: \u001b[33m, Blue: \u001b[34m, Magenta: \u001b[35m, Cyan: \u001b[36m, White: \u001b[37m ";

	public static final String GREEN = "\u001b[32,1m";
	public static final String RESET = "\u001b[0m";
	public static final String DLM = "\n";
	public static final String PAR = "\n\t";
	public static final String CRLF = "\r\n";

	public static void main(String[] args) {
		//
		colorize();
		System.out.println(showTime());
		System.out.println(GREEN + "DONE" + RESET);
	}

	//#### basics
	public static String showSys() {
		//
		Map<String, String> mapEnv = System.getenv();
		Map<String, String> mapEnvTree = new TreeMap<>(mapEnv);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("[");
		// env.forEach( ( key , val ) -> stringBuffer.append( key + ": " + val + dlm ) );
		mapEnvTree.forEach((key, val) -> {
			//
			val = val.replace("\\", "/");
			val = val.replace("\"", "'");
			stringBuffer.append("{\"").append(key).append("\":\"").append(val).append("\"},");
		});
		stringBuffer.append("\n{\"" + "USERNAME" + "\":\"").append(System.getenv("USERNAME")).append("\"}");
		stringBuffer.append("\n]");
		return stringBuffer.toString();
	}

	public static String showTime() {
		//
		LocalDateTime localDateTime = LocalDateTime.now();
		// txtLine = new Date( ).toString( );
		return ISO_DATE_TIME.format(localDateTime);
	}

	private static void colorize() {
		//
		StringBuilder txtLine = new StringBuilder();
		String[] colorDuo;
		String colorVal;
		for (String color : COLORS.split(",")) {
			colorDuo = color.split(":");
			colorVal = colorDuo[1].replaceAll("4", "3");
			txtLine.append(colorVal).append("■").append(RESET);
		}
		System.out.println(txtLine);
	}

	//#### files
	public static String getFileLines(String fileName, String delim) {
		//
		// https://mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
		String txtLines = "";
		if (delim == null || delim.equals("")) {
			delim = DLM;
		}
		//
		List<String> list;
		try (BufferedReader bReader = Files.newBufferedReader(Paths.get(fileName))) {
			//
			list = bReader.lines().collect(Collectors.toList());
			txtLines = String.join("\n", list);
			txtLines = txtLines.replaceAll("\n", delim);
		} catch (IOException ex) {
			LOGGER.warning(ex.getMessage());
		}
		//
		return txtLines;
	}

	public static String getFileLocal(String fileName, String delim) {
		//
		// https://howtodoinjava.com/java/io/read-file-from-resources-folder/
		// File file = ResourceUtils.getFile("classpath:config/sample.txt")
		String txtLines = "";
		if (delim == null || delim.equals("")) { delim = DLM; }
		try {
			File file = new File(fileName);
			txtLines = new String(Files.readAllBytes(file.toPath()), UTF_8);
			txtLines = txtLines.replaceAll("\n", delim);
		} catch (IOException ex) { LOGGER.warning(ex.getMessage()); }
		return txtLines;
	}

	//#### url
	public static String urlGet(String link) {
		//
		String txtLines;
		try {
			URL url = new URL(link);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("User-Agent", USER_AGENT);
			httpConn.setConnectTimeout(5000);
			httpConn.setReadTimeout(5000);
			//
			int responseCode = httpConn.getResponseCode();
			System.out.println("sends GET to: " + url);
			System.out.println("responseCode: " + responseCode);
			//
			InputStream inputStream = httpConn.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder stringBuilder = new StringBuilder();
			String txtLine;
			while ((txtLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(txtLine);
			}
			txtLines = stringBuilder.toString();
		} catch (IOException ex) {
			txtLines = ex.getMessage();
			LOGGER.log(Level.SEVERE, "#### ERROR: {0} ", txtLines);
		}
		return txtLines;
	}

	public static String urlPost(String link, String postParms) {
		//
		// http://zetcode.com/java/getpostrequest/
		String txtLines = "";
		try {
			URL url = new URL(link);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("User-Agent", USER_AGENT);
			httpConn.setRequestProperty("Content-Type", CONTENTTYPE_FORM);
			//	httpConn.setRequestProperty( "Authorization", "JWT " + jwtSourceId );
			//
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(postParms.getBytes());
			outputStream.flush();
			outputStream.close();
			//
			int responseCode = httpConn.getResponseCode();
			System.out.println("Sending POST : " + url);
			System.out.println("Response code: " + responseCode);
			//
			InputStream inputStream = httpConn.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader bufferedReader;
			StringBuilder stringBuffer = new StringBuilder();
			String txtLine;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				//
				bufferedReader = new BufferedReader(isr);
				while ((txtLine = bufferedReader.readLine()) != null) {
					stringBuffer.append(txtLine);
				}
				bufferedReader.close();
				txtLines = stringBuffer.toString();
			} else {
				LOGGER.info("POST failed to: " + link);
			}
		} catch (IOException ex) {
			txtLines = ex.getMessage();
			LOGGER.log(Level.SEVERE, "#### ERROR: {0} ", txtLines);
		}
		return txtLines;
	}

	public static String urlPostFile(String link, String postParms, String pathTxt, String pathBin) {
		//
		// https://www.baeldung.com/httpclient-multipart-upload
		// https://stackoverflow.com/questions/2469451/upload-files-from-java-client-to-a-http-server
		String txtLines;
		//
		File fileTxt = new File(pathTxt);
		File fileBin = new File(pathBin);
		String boundary = Long.toHexString(System.currentTimeMillis()); // random boundary
		URLConnection urlConn;
		try {
			URL url = new URL(link);
			urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-Type", CONTENTTYPE_MULTI + boundary);
			//	urlConn.setRequestProperty( "Authorization", "JWT " + jwtSourceId );
			//
			System.out.println("0 urlConn.getOutputStream( )");
			OutputStream outputStream = urlConn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(outputStream, UTF_8);
			PrintWriter writer = new PrintWriter(osw, true);
			//
			System.out.println("1 Send normal parms");
			writer.append("--").append(boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=").append(String.valueOf(UTF_8)).append(CRLF);
			writer.append(CRLF).append(postParms).append(CRLF).flush();
			//
			System.out.println("2 Send text file in charset UTF_8");
			writer.append("--").append(boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"").append(fileTxt.getName()).append("\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=").append(String.valueOf(UTF_8)).append(CRLF);
			writer.append(CRLF).flush();
			Files.copy(fileTxt.toPath(), outputStream);
			outputStream.flush(); // Important before continuing with writer!
			writer.append(CRLF).flush(); // CRLF indicates end of boundary
			//
			System.out.println("3 Send binary file");
			writer.append("--").append(boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"").append(fileBin.getName()).append("\"").append(CRLF);
			String fileBinContentType = URLConnection.guessContentTypeFromName(fileBin.getName());
			writer.append("Content-Type: ").append(fileBinContentType).append(CRLF);
			writer.append("Content-Transfer-Encoding: binary").append(CRLF);
			writer.append(CRLF).flush();
			Files.copy(fileBin.toPath(), outputStream);
			outputStream.flush(); // Important before continuing with writer!
			writer.append(CRLF).flush(); // CRLF indicates end of boundary
			//
			System.out.println("4 end of multipart/form-data");
			writer.append("--").append(boundary).append("--").append(CRLF).flush();
			//
			System.out.println("5 request lazily fired to get response info");
			int responseCode = ((HttpURLConnection) urlConn).getResponseCode();
			txtLines = "responseCode: " + responseCode; // should be 200
		} catch (IOException ex) {
			txtLines = ex.getMessage();
			LOGGER.log(Level.SEVERE, "#### ERROR: {0} ", txtLines);
		}
		//
		return txtLines;
	}

	//#### reflection
	public static String getField(Object object, String nameField) {
		//
		String txtLine = "";
		try {
			Class<?> clazz = object.getClass();
			Field field = clazz.getDeclaredField(nameField);
			field.setAccessible(true);
			Object objectField = field.get(object);
			txtLine = objectField.toString();
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			LOGGER.severe(ex.getMessage());
		}
		return txtLine;
	}

	public static Object getMethod(Class<?> clazz, String nameMethod, Object... objectParms) {
		//
		Object objectReturn = "";
		try {
			int parmsCount = 0;
			Object objectItem = null;
			if (objectParms != null && objectParms.length > 0) {
				parmsCount = objectParms.length;
			}
			Class<?>[] classArray = new Class<?>[parmsCount];
			for (int ictr = 0; ictr < parmsCount; ictr++) {
				try {
					assert objectParms != null;
					objectItem = objectParms[ictr];
				} catch (NullPointerException ex) {
					LOGGER.info(ex.getMessage());
				}
				if (objectItem == null) {
					classArray = new Class<?>[0];
					objectParms = null;
				} else {
					classArray[ictr] = objectItem.getClass();
				}
			}
			//
			// Class clazz = object.getClass();
			Object objectInstance = clazz.getDeclaredConstructor().newInstance();
			Method method = clazz.getDeclaredMethod(nameMethod, classArray);
			method.setAccessible(true);
			objectReturn = method.invoke(objectInstance, objectParms);
		} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException |
				InvocationTargetException | InstantiationException ex) {
			LOGGER.severe(ex.getMessage());
		}
		return objectReturn;
	}

	public static String exposeObject(Object object) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		Set<String> set = new TreeSet<>();
		Method[] methods = object.getClass().getDeclaredMethods();
		//
		Object[] args = null;
		int maxlen = 35;
		String FRMT = "\t%03d %-30s | %-45s | %02d | %s \n";
		AtomicInteger atomicInteger = new AtomicInteger();
		Arrays.stream(methods).forEach(mthd -> {
			//
			Object objectVal = "";
			String returnType = mthd.getReturnType().toString();
			if (returnType.length() > maxlen) {
				returnType = returnType.substring(returnType.length() - maxlen);
			}
			mthd.setAccessible(true);
			boolean boolClass = mthd.getReturnType().toString().startsWith("class");
			boolean boolCount = mthd.getParameterCount() == 0;
			if (boolClass & boolCount) {
				try {
					objectVal = mthd.invoke(object, args);
				} catch (IllegalAccessException | InvocationTargetException ex) {
					LOGGER.info(ex.getMessage());
				}
				if (objectVal == null) {
					objectVal = "NULL or EMPTY";
				}
			}
			boolean boolAccess = mthd.getName().startsWith("access$");
			if (!boolAccess) {
				set.add(String.format(FRMT, atomicInteger.incrementAndGet(),
						mthd.getName(), returnType, mthd.getParameterCount(), objectVal));
			}
		});
		//

		stringBuilder.append(object.getClass().getName()).append(" has: [").append(methods.length)
				.append("] methods\n\n");
		set.stream().sorted().forEach(stringBuilder::append);
		return stringBuilder + "\n";
	}

	public static void putObject(Object object, String objectName, Object objectValue) {
		//
		try {
			Class<?> clazz = object.getClass();
			Field field = clazz.getDeclaredField(objectName);
			field.setAccessible(true);
			field.set(object, objectValue);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			LOGGER.severe(ex.getMessage());
		}
	}

	//#### xml/yml/json
	public static List<File> getFilesFromZip(String fileName) {
		//
		// https://www.baeldung.com/java-compress-and-uncompress
		List<File> list = new ArrayList<>();
		int BUFFER_SIZE = 4096;
		try {
			//
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			File fileZip = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
			FileInputStream fis = new FileInputStream(fileZip);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry zipEntry = zis.getNextEntry();
			//
			FileOutputStream fos;
			byte[] bytes;
			int intReadLen;
			File file;
			String fileItem;
			while (zipEntry != null) {
				//
				fileItem = zipEntry.getName();
				file = new File(fileItem);
				bytes = new byte[BUFFER_SIZE];
				fos = new FileOutputStream(file);
				while ((intReadLen = zis.read(bytes)) > 0) {
					fos.write(bytes, 0, intReadLen);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
				list.add(file);
			}
		} catch (IOException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return list;
	}

	public static String getZipFileList(String fileName, String fileItem, String delim) {
		//
		StringBuilder txtLines = new StringBuilder();
		if (delim == null || delim.equals("")) {
			delim = DLM;
		}
		//
		List<File> list = UtilityMain.getFilesFromZip(fileName);
		for (File file : list) {
			txtLines.append(file.getName()).append(delim);
		}
		return txtLines.toString();
	}

	public static File putFilesIntoZip(List<File> list) { /* ??? */
		//
		// https://www.baeldung.com/java-compress-and-uncompress
		System.out.println(list);
		File file = list.get(0);
		System.out.println(file.getName());
		return null;
	}

	public static String getXmlFileNode(String xmlfile, String xpathTxt, String delim) {
		//
		// https://howtodoinjava.com/xml/evaluate-xpath-on-xml-string/
		StringBuilder txtLines = new StringBuilder();
		if (xpathTxt == null || xpathTxt.equals("")) {
			xpathTxt = "/catalog/book/title";
		}
		if (delim == null || delim.equals("")) {
			delim = DLM;
		}
		//
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder(); // PCE
			Document document = documentBuilder.parse(xmlfile); // SAXException
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xPath = xpf.newXPath();
			txtLines = new StringBuilder((String) xPath.evaluate(xpathTxt, document, STRING));
			//
			String xpathNode = "/catalog/book/@id";
			NodeList nodeList = (NodeList) xPath.evaluate(xpathNode, document, NODESET);
			for (int ictr = 0; ictr < nodeList.getLength(); ictr++) {
				txtLines.append(delim).append(nodeList.item(ictr).getNodeValue());
			}
		} catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines.toString();
	}

	public static String getXmlNode(String xml, String xpathTxt) {
		//
		// https://howtodoinjava.com/xml/evaluate-xpath-on-xml-string/
		String txtLines = "";
		try {
			StringReader stringReader = new StringReader(xml);
			InputSource inputSource = new InputSource(stringReader);
			XPath xPath = XPathFactory.newInstance().newXPath();
			txtLines = xPath.evaluate(xpathTxt, inputSource);
		} catch (XPathExpressionException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines;
	}

	public static String convertXml2Json(String xml) {
		//
		String json = "";
		int INDENT_FACTOR = 4;
		//
		try {
			JSONObject jsonObject = XML.toJSONObject(xml);
			json = jsonObject.toString(INDENT_FACTOR);
		} catch (JSONException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return json;
	}

	public static String convertJson2Xml(String json) {
		//
		String xml = "";
		try {
			JSONObject jsonObj = new JSONObject(json);
			xml = XML.toString(jsonObj);
		} catch (JSONException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return xml;
	}

	public static String formatXml(String xmlOld) {
		//
		String xml = "";
		Document document = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			StringReader stringReader = new StringReader(xmlOld);
			InputSource inputSource = new InputSource(stringReader);
			document = documentBuilder.parse(inputSource);
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			LOGGER.warning(ex.getMessage());
		}
		try {
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory tf = TransformerFactory.newInstance();
			// tf.setAttribute( "indent-number", 4 );
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DOMSource domSource = new DOMSource(document);
			transformer.transform(domSource, xmlOutput);
			xml = xmlOutput.getWriter().toString();
		} catch (TransformerException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return xml;
	}

	public static String parseYaml2JsonNode(String yamlFileName, String applicationNode) {
		//
		String txtLine = "";
		try {
			File file = new File(yamlFileName);
			FileInputStream fis = new FileInputStream(file);
			//
			// create yaml from file
			YAMLFactory yamlFactory = new YAMLFactory();
			ObjectMapper objectMapperYaml = new ObjectMapper(yamlFactory);
			Object objectYaml = objectMapperYaml.readValue(fis, Map.class);
			// System.out.println( "yaml: " + objectYaml );
			//
			// convert yaml to json
			ObjectMapper objectMapperJson = new ObjectMapper();
			String json = objectMapperJson.writeValueAsString(objectYaml);
			// System.out.println( "json: " + json );
			//
			// convert json to node object; "path" also works, "at" does not
			ObjectMapper objectMapperNode = new ObjectMapper();
			JsonNode jsonNode = objectMapperNode.readTree(json);
			txtLine = (jsonNode.get(applicationNode)).asText();
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
		return txtLine;
	}

	public static String parseJsonList2List(String jsonArr, int listFormat) {
		//
		StringBuilder txtLines = new StringBuilder();
		ObjectMapper objectMapperHtml = new ObjectMapper();
		TypeReference<ArrayList<LinkedHashMap<String, String>>> typeReference
				= new TypeReference<ArrayList<LinkedHashMap<String, String>>>() {
		};
		ArrayList<LinkedHashMap<String, String>> arrayList;
		LinkedHashMap<String, String> linkedHashMap;
		Set<?> set;
		String txtKey;
		String txtVal;
		Object objVal;
		String PFX = PAR;
		String MID = " : ";
		String SFX = "";
		if (listFormat > 0) {
			PFX = "<tr><th>";
			MID = "</th<td>";
			SFX = "</td></tr>";
		}
		try {
			arrayList = objectMapperHtml.readValue(jsonArr, typeReference);
			for (LinkedHashMap<String, String> object : arrayList) {
				//
				linkedHashMap = object;
				set = linkedHashMap.keySet();
				txtKey = set.toString().substring(1, set.toString().length() - 1);
				objVal = linkedHashMap.get(txtKey);
				txtVal = objVal.toString();
				txtLines.append(PFX).append(txtKey).append(MID).append(txtVal).append(SFX);
			}
			System.out.println("txtLines: " + txtLines);
		} catch (JsonProcessingException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines.toString();
	}
}
//----
