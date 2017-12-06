package com.training.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TravelocityHotelsPage extends BasePage {

	public TravelocityHotelsPage(WebDriver pDriver) {
		super(pDriver);

	}
	
	@FindBy(id="hotel-destination-hlp")
	private WebElement goingTo;
	
	@FindBy(xpath="//*[@id=\"gcw-hotel-form-hlp\"]/div[7]/label/button")
	private WebElement searchButton;
	
	
}
