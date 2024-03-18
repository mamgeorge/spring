package com.camunda.academy;

import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

@Getter @Setter
public class PayAppConfiguration {

	public static final String FRMT = "\t%-20s %s\n";
	public static final String FRMJ = "\t\"%s\" : \"%s\",\n";
	public static final String EOL = "\n";
	public static final String ZEEBE_CLOUD_REGION = "zeebe.client.cloud.region";
	public static final String ZEEBE_CLUSTERID_ADDRESS = "zeebe.client.cloud.clusterId";
	public static final String ZEEBE_CLIENT_ID = "zeebe.client.cloud.clientId";
	public static final String ZEEBE_CLIENT_SECRET = "zeebe.client.cloud.clientSecret";
	public static final String ZEEBE_AUTHORIZATION_SERVER_URL = "zeebe.client.cloud.serverUrl";
	public static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.token.audience";
	public static final String ZEEBE_TOKEN_PORT = "zeebe.token.port";

	private static final String PATHFILE_LOCAL = "src/main/resources/";
	public static final String RESOURCES_FILE = PATHFILE_LOCAL + "application.properties";

	private String zeebeCloudRegion = "";
	private String zeebeClusterIdAddress = "";
	private String zeebeClientId = "";
	private String zeebeClientSecret = "";
	private String zeebeAuthServerUrl = "";
	private String zeebeTokenAudience = "";
	private int zeebeTokenPort = 0;
	private String zeebeClusterIdAddressFull = "";

	public PayAppConfiguration( ) {

		Properties properties = getResourceProps(RESOURCES_FILE);

		zeebeCloudRegion = properties.getProperty(ZEEBE_CLOUD_REGION);
		zeebeClusterIdAddress = properties.getProperty(ZEEBE_CLUSTERID_ADDRESS);
		zeebeClientId = properties.getProperty(ZEEBE_CLIENT_ID);
		zeebeClientSecret = properties.getProperty(ZEEBE_CLIENT_SECRET);
		zeebeAuthServerUrl = properties.getProperty(ZEEBE_AUTHORIZATION_SERVER_URL);
		zeebeTokenAudience = properties.getProperty(ZEEBE_TOKEN_AUDIENCE);
		zeebeTokenPort = Integer.parseInt(properties.getProperty(ZEEBE_TOKEN_PORT));

		zeebeClusterIdAddressFull = zeebeClusterIdAddress + "." + zeebeCloudRegion
			+ "." + zeebeTokenAudience + ":" + zeebeTokenPort;
	}

	public Properties getResourceProps(String resourcesFile) {

		Properties properties = new Properties();
		try {
			File file = new File(resourcesFile);
			InputStream inputStream = Files.newInputStream(file.toPath());

			properties.load(inputStream);
			inputStream.close();
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return properties;
	}

	public OAuthCredentialsProvider getOacProvider( ) {

		OAuthCredentialsProvider oacProvider =
			new OAuthCredentialsProviderBuilder()
				.authorizationServerUrl(zeebeAuthServerUrl)
				.audience(zeebeTokenAudience)
				.clientId(zeebeClientId)
				.clientSecret(zeebeClientSecret)
				.build();

		return oacProvider;
	}

	@Override public String toString( ) {

		String txtLines = "{" + EOL;
		txtLines += String.format(FRMJ, "zeebeCloudRegion", zeebeCloudRegion);
		txtLines += String.format(FRMJ, "zeebeClusterIdAddress", zeebeClusterIdAddress);
		txtLines += String.format(FRMJ, "zeebeClientId", zeebeClientId);
		txtLines += String.format(FRMJ, "zeebeAuthServerUrl", zeebeAuthServerUrl);
		txtLines += String.format(FRMJ, "zeebeTokenAudience", zeebeTokenAudience);
		txtLines += "\t\"zeebeTokenPort\" : "+ zeebeTokenPort+ EOL;
		txtLines += String.format(FRMJ, "zeebeClusterIdAddressFull", zeebeClusterIdAddressFull);
		txtLines += "}";

		return txtLines;
	}
}
