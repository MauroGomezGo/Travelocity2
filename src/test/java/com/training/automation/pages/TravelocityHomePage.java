package com.training.automation.pages;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class TravelocityHomePage extends BasePage {

	public TravelocityHomePage(WebDriver pDriver) {
		super(pDriver);
		pDriver.get("https://www.travelocity.com/");
	}

	int currentDay;
	String month;
	Date now = new Date();
	Calendar dCalendar = Calendar.getInstance();

	@FindBy(id = "flight-origin-hp-flight")
	private WebElement flyingFrom;

	@FindBy(id = "flight-destination-hp-flight")
	private WebElement flyingTo;

	@FindBy(id = "flight-departing-hp-flight")
	private WebElement departingDate;

	@FindBy(id = "flight-returning-hp-flight")
	private WebElement returningDate;

	private WebElement nextMonth;

	private List<WebElement> dayOfMonth;

	@FindBy(id = "tab-flight-tab-hp")
	private WebElement flightTab;

	@FindBy(id = "flight-type-roundtrip-label-hp-flight")
	private WebElement roundtrip;

	@FindBy(id = "tab-package-tab-hp")
	private WebElement flighPlusHotelTab;

	@FindBy(id = "primary-header-hotel")
	private WebElement hotelsPage;

	private WebElement searchButton;

	public void click(WebElement element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void roundtrip() {
		
		click(flightTab);
		click(roundtrip);
	}

	private WebElement adults;

	public void fillDestintation(String from, String to) {

		flyingFrom.sendKeys(from);
		flyingTo.sendKeys(to);

	}

	// Dividir en metodos mas especificos, si queda tiempo

	public void selectDate(int monthsFromNow, int tripDuration) {

		dCalendar.setTime(now);
		currentDay = dCalendar.get(Calendar.DAY_OF_MONTH);
		dCalendar.add(Calendar.DAY_OF_MONTH, tripDuration);
		dCalendar.getTime();
		int currentDayPlusTrip = dCalendar.get(Calendar.DAY_OF_MONTH);
		boolean breakIt = true;
		getWait().until(ExpectedConditions.elementToBeClickable(departingDate));
		departingDate.click();
		while (monthsFromNow != 0) {

			breakIt = true;
			try {
				nextMonth = getDriver().findElement(By.className("datepicker-next"));
				getWait().until(ExpectedConditions.elementToBeClickable(nextMonth));
				nextMonth.click();
				monthsFromNow--;
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}

				if (breakIt) {
					break;
				}

			}
		}

		month = getDriver().findElement(By.cssSelector("button[class='datepicker-cal-date']"))
				.getAttribute("data-month");
		String stringDay = String.valueOf(currentDay);
		int counter = 0;
		String machete = "button[data-month='" + month + "']";
		dayOfMonth = getDriver().findElements(By.cssSelector(machete));
		for (WebElement day : dayOfMonth) {
			counter++;
			day.getText();

			if (day.getText().equals(stringDay) || counter == dayOfMonth.size()) {
				getWait().until(ExpectedConditions.elementToBeClickable(day));
				day.click();
				break;
			}

		}

		getWait().until(ExpectedConditions.elementToBeClickable(returningDate));
		returningDate.click();
		breakIt = true;
		while (true) {
			breakIt = true;
			try {
				stringDay = String.valueOf(currentDayPlusTrip);
				dayOfMonth = getDriver().findElements(By.tagName("td"));
				for (WebElement day : dayOfMonth) {
					day.getText();
					if (day.getText().equals(stringDay)) {
						day.click();
						break;
					}
				}
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}
			if (breakIt) {
				break;
			}

		}

	}

	public void adults(String passengersAdults) {

		adults = getDriver().findElement(By.id("flight-adults-hp-flight"));
		Select select = new Select(adults);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			option.getText();
			if (option.getText().equals(passengersAdults)) {
				getWait().until(ExpectedConditions.elementToBeClickable(option));
				option.click();
				break;
			}

		}

	}

	public ResultPageFlights searchButton() {
		getWait().until(ExpectedConditions.visibilityOf(adults));
		searchButton = getDriver().findElement(By.xpath("//*[@id=\"gcw-flights-form-hp-flight\"]/div[8]/label/button"));
		getWait().until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
		return new ResultPageFlights(getDriver());
	}

}
