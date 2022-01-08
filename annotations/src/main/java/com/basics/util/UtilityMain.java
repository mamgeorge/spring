/*
	cd c:\workspace\github\spring_annotations
*/
package com.basics.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

public class UtilityMain {

	public static final Logger LOGGER = Logger.getLogger(UtilityMain.class.getName());

	public static final String USER_AGENT_VAL = "Mozilla/5.0";
	public static final String CONTENTTYPE_MULTI = "multipart/form-data; boundary=";

	public static final String GREEN = "\u001b[32,1m";
	public static final String RESET = "\u001b[0m";
	public static final String EOL = "\n";
	public static final String PAR = "\n\t";
	public static final String CRLF = "\r\n";

	public static final String COLORS =
			"Black: \u001b[30m, Red: \u001b[31m, Green: \u001b[32m, Yellow: \u001b[33m, Blue: \u001b[34m, Magenta: \u001b[35m, Cyan: \u001b[36m, White: \u001b[37m ";

	public static void main(String[] args) {
		//
		colorize();
		System.out.println(showTime());
		System.out.println(GREEN + "DONE" + RESET);
	}

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

	public static String getFileLines(String fileName, String delim) {
		//
		// https://mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
		String txtLines = "";
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

	public static String getFileLocal(String pathFile, String delim) {
		//
		// https://howtodoinjava.com/java/io/read-file-from-resources-folder/
		// File file = ResourceUtils.getFile("classpath:config/sample.txt")
		String txtLines = "";
		try {
			File fileLocal = new File(pathFile);
			File pathFileLocal = new File(fileLocal.getAbsolutePath());
			txtLines = new String(Files.readAllBytes(pathFileLocal.toPath()), UTF_8);
			txtLines = Files.readString(pathFileLocal.toPath(), UTF_8);
		} catch (IOException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines;
	}

	public static String urlGet(String link) {
		//
		String txtLines;
		try {
			URL url = new URL(link);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(GET.toString());
			httpConn.setRequestProperty(USER_AGENT, USER_AGENT_VAL);
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
			httpConn.setRequestMethod(POST.toString());
			httpConn.setRequestProperty(USER_AGENT, USER_AGENT_VAL);
			httpConn.setRequestProperty(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
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
			StringBuilder stringBuilder = new StringBuilder();
			String txtLine;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				//
				bufferedReader = new BufferedReader(isr);
				while ((txtLine = bufferedReader.readLine()) != null) {
					stringBuilder.append(txtLine);
				}
				bufferedReader.close();
				txtLines = stringBuilder.toString();
			} else {
				System.out.println("POST failed to: " + link);
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
		String PATH_PREF = "";
		//
		File fileTxt = new File(PATH_PREF + pathTxt);
		File fileBin = new File(PATH_PREF + pathBin);
		String boundary = Long.toHexString(System.currentTimeMillis()); // random boundary
		URLConnection urlConn;
		try {
			URL url = new URL(link);
			urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty(CONTENT_TYPE, CONTENTTYPE_MULTI + boundary);
			//	urlConn.setRequestProperty( "Authorization", "JWT " + jwtSourceId );
			//
			System.out.println("0 urlConn.getOutputStream( )");
			OutputStream outputStream = urlConn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(outputStream, UTF_8);
			PrintWriter writer = new PrintWriter(osw, true);
			//
			System.out.println("1 Send normal parms");
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + UTF_8).append(CRLF);
			writer.append(CRLF).append(postParms).append(CRLF).flush();
			//
			System.out.println("2 Send text file in charset UTF_8");
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\""
					+ fileTxt.getName() + "\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + UTF_8).append(CRLF);
			writer.append(CRLF).flush();
			Files.copy(fileTxt.toPath(), outputStream);
			outputStream.flush(); // Important before continuing with writer!
			writer.append(CRLF).flush(); // CRLF indicates end of boundary
			//
			System.out.println("3 Send binary file");
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\""
					+ fileBin.getName() + "\"").append(CRLF);
			String fileBinContentType = URLConnection.guessContentTypeFromName(fileBin.getName());
			writer.append("Content-Type: " + fileBinContentType).append(CRLF);
			writer.append("Content-Transfer-Encoding: binary").append(CRLF);
			writer.append(CRLF).flush();
			Files.copy(fileBin.toPath(), outputStream);
			outputStream.flush(); // Important before continuing with writer!
			writer.append(CRLF).flush(); // CRLF indicates end of boundary
			//
			System.out.println("4 end of multipart/form-data");
			writer.append("--" + boundary + "--").append(CRLF).flush();
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

	public static List<File> getFilesFromZip(String fileName) {
		//
		// https://www.baeldung.com/java-compress-and-uncompress
		List<File> list = new ArrayList<>();
		int BUFFER_SIZE = 4096;
		String DIR_TEMP = "src/main/resources/temp/";
		String fileItem;
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
			while (zipEntry != null) {
				//
				fileItem = zipEntry.getName();
				file = new File(DIR_TEMP + fileItem);
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

	public static String exposeObject(Object object) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		Set set = new TreeSet();
		Method[] methods = object.getClass().getDeclaredMethods();
		//
		Object[] args = null;
		int MAXLEN = 35;
		String FRMT = "\t%02d %-25s | %-35s | %02d | %s \n";
		AtomicInteger atomicInteger = new AtomicInteger();
		Arrays.stream(methods).forEach(mthd -> {
			//
			Object objectVal = "";
			String returnType = mthd.getReturnType().toString();
			if (returnType.length() > MAXLEN) {
				returnType = returnType.substring(returnType.length() - MAXLEN);
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
		set.stream().sorted().forEach(val -> stringBuilder.append(val));
		return stringBuilder + "\n";
	}

	public static String getZipFileList(String fileName, String delim) {
		//
		StringBuilder txtLines = new StringBuilder();
		List<File> list = UtilityMain.getFilesFromZip(fileName);
		for (File file : list) {
			txtLines.append(file.getName()).append(delim);
		}
		return txtLines.toString();
	}

	public static File putFilesIntoZip() { /* ??? */
		//
		// https://www.baeldung.com/java-compress-and-uncompress
		return null;
	}

	public static String getXmlFileNode(String xmlfile, String xpathTxt, String delim) {
		//
		// https://howtodoinjava.com/xml/evaluate-xpath-on-xml-string/
		StringBuilder txtLines = new StringBuilder();
		if (xpathTxt == null || xpathTxt.equals("")) {
			xpathTxt = "/catalog/book/title";
		}
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
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(yamlFileName);
			//
			// create yaml from file
			YAMLFactory yamlFactory = new YAMLFactory();
			ObjectMapper objectMapperYaml = new ObjectMapper(yamlFactory);
			Object objectYaml = objectMapperYaml.readValue(inputStream, Map.class);
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
		} catch (JsonParseException ex) {
			LOGGER.warning(ex.getMessage());
		} catch (JsonMappingException ex) {
			LOGGER.warning(ex.getMessage());
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
		//
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
			for (LinkedHashMap<String, String> linkedHashMap : arrayList) {
				//
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
