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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static javax.xml.xpath.XPathConstants.NODESET;
import static javax.xml.xpath.XPathConstants.STRING;

public class UtilityConversion {
	//
	public static final Logger LOGGER = Logger.getLogger(UtilityConversion.class.getName());

	public static final String DLM = "\n";
	public static final String PAR = "\n\t";

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
			while ( zipEntry != null ) {
				//
				fileItem = zipEntry.getName();
				file = new File(fileItem);
				bytes = new byte[BUFFER_SIZE];
				fos = new FileOutputStream(file);
				while ( ( intReadLen = zis.read(bytes) ) > 0 ) {
					fos.write(bytes, 0, intReadLen);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
				list.add(file);
			}
		}
		catch (IOException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return list;
	}

	public static String getZipFileList(String fileName, String fileItem, String delim) {
		//
		StringBuilder txtLines = new StringBuilder();
		if ( delim == null || delim.equals("") ) {
			delim = DLM;
		}
		//
		List<File> list = UtilityConversion.getFilesFromZip(fileName);
		for ( File file : list ) {
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
		if ( xpathTxt == null || xpathTxt.equals("") ) {
			xpathTxt = "/catalog/book/title";
		}
		if ( delim == null || delim.equals("") ) {
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
			for ( int ictr = 0; ictr < nodeList.getLength(); ictr++ ) {
				txtLines.append(delim).append(nodeList.item(ictr).getNodeValue());
			}
		}
		catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException ex) {
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
		}
		catch (XPathExpressionException ex) {
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
		}
		catch (JSONException ex) {
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
		}
		catch (JSONException ex) {
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
		}
		catch (ParserConfigurationException | SAXException | IOException ex) {
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
		}
		catch (TransformerException ex) {
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
			txtLine = ( jsonNode.get(applicationNode) ).asText();
		}
		catch (IOException ex) {
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
		if ( listFormat > 0 ) {
			PFX = "<tr><th>";
			MID = "</th<td>";
			SFX = "</td></tr>";
		}
		try {
			arrayList = objectMapperHtml.readValue(jsonArr, typeReference);
			for ( LinkedHashMap<String, String> object : arrayList ) {
				//
				linkedHashMap = object;
				set = linkedHashMap.keySet();
				txtKey = set.toString().substring(1, set.toString().length() - 1);
				objVal = linkedHashMap.get(txtKey);
				txtVal = objVal.toString();
				txtLines.append(PFX).append(txtKey).append(MID).append(txtVal).append(SFX);
			}
			System.out.println("txtLines: " + txtLines);
		}
		catch (JsonProcessingException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines.toString();
	}
}
//----
