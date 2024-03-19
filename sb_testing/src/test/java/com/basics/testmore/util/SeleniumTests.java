package com.basics.testmore.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.basics.testmore.util.UtilityMain.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled( "interferes with maven testing results" )
class SeleniumTests {

	public static final boolean IS_OPTIONS = true;
	public static final boolean IS_DESCAP = false;
	public static final boolean IS_SUBMIT = true;

	String[] BROWSER = { "edge", "chrome", "firefox" };
	public static final String[] URL_WEBPAGES = { "http://localhost:2000",
		"http://www.selenium.dev/selenium/web/web-form.html" };
	public static final String STARTLINES = EOL + StringUtils.repeat("-", 40) + EOL;

	/*
		MicrosoftEdge Version: 114.0.1823.41 (Official build) (64-bit)
		Google Chrome Version: 114.0.5735.110 (Official Build) (64-bit)
		RemoteWebDriver only possible if Selenium server exists

		pom.xml
			org.seleniumhq.selenium:selenium-api,reomote-driver:4.10.0
			?org.seleniumhq.selenium:selenium-http-jdk-client:4.5.0

		USERNAME: mamge, OS: Windows_NT, os.arch: amd64, os.name: Windows 10, os.version: 10.0

		org.openqa.selenium.SessionNotCreatedException: Could not start a new session.
			Possible causes are invalid address of the remote server or browser start-up failure.

			ChromeDriver < ChromiumDriver < RemoteWebDriver < WebDriver
			setting driver "messed it up": "webdriver.chrome.driver", DRIVER_CHROME_EXE
	*/
	@Test void selenium_basic( ) {

		String browser = BROWSER[1];
		String txtLines = STARTLINES + "selenium_basic (" + browser + ")" + EOL;

		ChromeOptions options = new ChromeOptions();

		if ( IS_OPTIONS ) {

			// options.PageLoadStrategy = PageLoadStrategy.NORMAL;
			options.addArguments("--no-sandbox");
			options.addArguments("--headless");
			options.addArguments("--incognito");
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-dev-shm-usage");

			options.addArguments("--disable-in-process-stack-trace");
			options.addArguments("--disable-logging");
			options.addArguments("--log-level=3");
			options.addArguments("--output=/dev/null");
			options.addArguments("--silent");
		}

		if ( IS_DESCAP ) {
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setPlatform(Platform.WIN10);
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			desiredCapabilities.setBrowserName(browser);
			options.merge(desiredCapabilities);
		}

		WebDriver webDriver = new ChromeDriver(options);
		webDriver.get(URL_WEBPAGES[0]);
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));

		txtLines += "getCurrentUrl: " + webDriver.getCurrentUrl() + EOL;
		txtLines += "getWindowHandle: " + webDriver.getWindowHandle() + EOL;
		txtLines += "getTitle: " + webDriver.getTitle() + EOL;
		// txtLines += "getPageSource: " + webDriver.getPageSource() + EOL;

		if ( IS_SUBMIT ) {
			try {
				WebElement txtBoxUser = webDriver.findElement(By.name("my-text"));
				WebElement txtBoxPass = webDriver.findElement(By.name("my-password"));
				WebElement submitButton = webDriver.findElement(By.className("btn-outline-primary"));

				String password = RandomStringUtils.randomAlphabetic(8);
				txtLines += "########: " + password + EOL;
				txtBoxUser.clear();
				txtBoxPass.clear();
				txtBoxUser.sendKeys(System.getenv("USERNAME"));
				txtBoxPass.sendKeys(password);
				submitButton.click();

				txtLines += "getTitle: " + webDriver.getTitle() + EOL;
			}
			catch (NoSuchElementException ex) { System.out.println("NSEE ERROR: " + ex.getMessage()); }
		}

		try { TimeUnit.SECONDS.sleep(2); }
		catch (InterruptedException ex) { System.out.println("IE ERROR: " + ex.getMessage()); }

		webDriver.quit();
		System.out.println(txtLines);
		assertNotNull(webDriver);
	}

	@Test void selenium_Edge( ) {

		String txtLines = STARTLINES + "selenium_Edge" + EOL;
		EdgeOptions options = new EdgeOptions();
		options.addArguments("--headless");

		WebDriver webDriver = new EdgeDriver(options);
		webDriver.get(URL_WEBPAGES[1]);
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
		txtLines += "getTitle: " + webDriver.getTitle() + EOL;

		webDriver.quit();
		System.out.println(txtLines);
		assertNotNull(webDriver);
	}

	@Test void selenium_Chrome( ) {

		String txtLines = STARTLINES + "selenium_Chrome" + EOL;
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");

		WebDriver webDriver = new ChromeDriver(options);
		webDriver.get(URL_WEBPAGES[1]);
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
		txtLines += "getTitle: " + webDriver.getTitle() + EOL;

		webDriver.quit();
		System.out.println(txtLines);
		assertNotNull(webDriver);
	}

	@Test void selenium_Firefox( ) {

		String txtLines = STARTLINES + "selenium_Firefox" + EOL;
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless");

		WebDriver webDriver = new FirefoxDriver(options);
		webDriver.get(URL_WEBPAGES[1]);
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
		txtLines += "getTitle: " + webDriver.getTitle() + EOL;

		webDriver.quit();
		System.out.println(txtLines);
		assertNotNull(webDriver);
	}
}

