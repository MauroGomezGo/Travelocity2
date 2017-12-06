package com.training.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaymentPage extends BasePage {

	public PaymentPage(WebDriver pDriver) {
		super(pDriver);

	}

	boolean contain = false;
	WebElement element;

	public boolean verify(String elementSelector) {

		boolean breakIt = true;
		int count = 0;
		contain = false;
		while (true) {
			breakIt = true;
			try {
				handleMultipleWindows("Travelocity: Payment");
				getWait().until(ExpectedConditions.elementToBeClickable(By.id("complete-booking")));
				element = getDriver().findElement(By.cssSelector(elementSelector));
				contain = true;
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")||e.getMessage().contains("unable to locate")) {
					System.out.println(e);
					handleMultipleWindows("Travelocity: Payment");
					element = getDriver().findElement(By.id("modalCloseButton"));
					element.click();
					count++;
					if (count == 2) {
						breakIt = true;
					} else {
						breakIt = false;
					}
					
				}
			}
			if (breakIt) {
				break;
			}
		}

		return contain;

	}

	public boolean verifyFirstName() {

		return verify("[id=\"firstname[0]\"]");

	}
	
	public boolean verifyMiddleName() {

		return verify("[name=\"tripPreferencesRequest.airTripPreferencesRequest.travelerPreferences[0].middleName\"]");

	}
	
	public boolean verifyLastName() {

		return verify("[id=\"lastname[0]\"]");

	}
	
	public boolean verifyCountryCode() {

		return verify("[name=\"tripPreferencesRequest.airTripPreferencesRequest.travelerPreferences[0].phoneCountryCode\"]");

	}
	
	public boolean verifyPhoneNumber() {

		return verify("[id=\"phone-number[0]\"]");

	}
	

}
