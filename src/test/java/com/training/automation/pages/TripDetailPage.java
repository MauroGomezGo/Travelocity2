package com.training.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TripDetailPage extends BasePage {

	public TripDetailPage(WebDriver pDriver) {
		super(pDriver);

	}

	boolean contain;

	private WebElement element;
	private WebElement continueBooking;

	public boolean verify(String elementSelector) {
		contain = false;
		try {
			handleMultipleWindows("Trip Detail | Travelocity");
			element = getDriver().findElement(By.cssSelector(elementSelector));
			contain = true;
		} catch (Exception e) {
			contain = false;
		}

		return contain;

	}
	
	public boolean verifyTripTotal() {
		return verify(".tripTotalPrice.visuallyhidden");
	}
	
	public boolean verifyDepartureInfo() {
		return verify(".flex-card.flex-tile.details.OD0");
	}
	
	public boolean verifyReturnInfo() {
		return verify(".flex-card.flex-tile.details.OD1");
	}

	public boolean verifyPriceGText() {
		return verify(".priceGuarantee");
	}
	
	public void click(WebElement element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	public PaymentPage continueBooking() {
		continueBooking = getDriver().findElement(By.cssSelector(".btn-secondary.btn-action.bookButton.fare"));
		click(continueBooking);
		
		return new PaymentPage(getDriver());
	}
	
	


}
