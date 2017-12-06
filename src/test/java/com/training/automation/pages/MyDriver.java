package com.training.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MyDriver {
	private WebDriver driver;

	public MyDriver(Browsers browser) {
		switch (browser) {

		case CHROME:
			System.setProperty("webdriver.chrome.driver",
					"C:/Users/Edgard.Gomez/Documents/SW/Selenium/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--enable-popup-blocking");
			options.addArguments("disable-infobars");
			options.addArguments("test-type");
			options.addArguments("--start-maximized");
			options.addArguments("--allow-running-insecure-content");
			driver = new ChromeDriver(options);
			break;
		default:
			break;
		}
	}

	public WebDriver getDriver() {
		return this.driver;
	}

}
