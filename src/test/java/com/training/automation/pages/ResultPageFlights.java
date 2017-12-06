package com.training.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ResultPageFlights extends BasePage {

	public ResultPageFlights(WebDriver pDriver) {
		super(pDriver);

	}

	Boolean contain;
	String sortValue;

	private WebElement sortBox;
	private List<WebElement> offers;
	private List<WebElement> departures;
	private WebElement modal;

	public String verifySortBox(String sortOption) {

		sortBox = getDriver().findElement(By.name("sort"));
		Select select = new Select(sortBox);

		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (option.getText().equals(sortOption)) {
				sortValue = option.getText();
				break;
			}

		}
		return sortValue;
	}

	public boolean verify(String elementSelector) {

		contain = false;
		getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#xsell-banner-default")));
		offers = getDriver().findElements(By.cssSelector(".flight-module.segment.offer-listing"));
		for (WebElement offer : offers) {
			try {
				offer.findElement(By.cssSelector(elementSelector));
				contain = true;

			} catch (Exception e) {
				if (e.getMessage().contains("no such element: Unable to locate element")) {
					contain = false;
					break;
				}
			}

		}

		return contain;
	}

	public boolean verifySelectButton() {

		return contain = verify(".btn-secondary.btn-action");

	}

	public boolean verifyFlightDestination() {

		return contain = verify("[data-test-id='duration']");

	}

	public boolean verifyDetails() {

		return contain = verify(".flight-details-link");

	}

	public boolean sortBy(String sortOption) {
		boolean sortSucces = false;
		try {
			sortBox = getDriver().findElement(By.name("sort"));
			Select select = new Select(sortBox);
			select.selectByVisibleText(sortOption);
			sortSucces = true;
		} catch (Exception e) {
			sortSucces = false;
		}

		return sortSucces;

	}

	public boolean compareShortest(ArrayList<Integer> elements) {
		boolean compare = false;
		Integer element = 0;
		for (int i = 0; i < elements.size() - 1; i++) {
			if (elements.get(i) > element) {
				element = elements.get(i);
				compare = true;
			}
		}

		return compare;

	}

	public boolean verifyOrderDurationShorter() {

		int minutes;
		int hours;
		String durationM;
		String durationH;
		String[] durationSplit;
		String duration;
		WebElement durationElement;
		ArrayList<Integer> elements = new ArrayList<Integer>();

		getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#xsell-banner-default")));
		offers = getDriver().findElements(By.cssSelector("[class=\"flight-module segment offer-listing\""));
		for (WebElement offer : offers) {
			try {
				durationElement = offer.findElement(By.cssSelector("[data-test-id=\"duration\"]"));
				duration = durationElement.getText();
				durationSplit = duration.split("h");
				durationH = durationSplit[0];
				durationM = durationSplit[1].trim();
				durationM = durationM.substring(0, durationM.length() - 1);
				hours = (Integer.parseInt(durationH)) * 60;
				minutes = (Integer.parseInt(durationM));
				elements.add(hours + minutes);
			} catch (Exception e) {
				if (e.getMessage().contains("no such element: Unable to locate element")) {
					break;
				}
			}

		}

		return compareShortest(elements);

	}

	public boolean verifyODS() {
		contain = false;
		boolean breakIt = true;
		while (true) {
			contain = breakIt = true;
			try {
				verifyOrderDurationShorter();
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					contain = verifyOrderDurationShorter();
					breakIt = false;
				}
			}
			if (breakIt) {
				break;
			}
		}
		return contain;

	}

	public boolean selectOffer(int pos) {

		boolean selected = false;
		WebElement button;
		getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#xsell-banner-default")));
		departures = getDriver().findElements(By.cssSelector("[class=\"flight-module segment offer-listing\"]"));
		int count = 1;
		for (WebElement departure : departures) {
			if (count == pos) {
				handleMultipleWindows("LAS to LAX Flights | Travelocity");
				button = departure.findElement(By.cssSelector(".btn-secondary.btn-action.t-select-btn"));
				getWait().until(ExpectedConditions.elementToBeClickable(button));
				button.click();
				selected = true;
				break;
			}
			count++;
		}
		return selected;
	}

	public boolean selectOfferBucle(int pos) {
		boolean breakIt = true;
		contain = false;
		while (true) {
			breakIt = true;
			try {
				handleMultipleWindows("LAS to LAX Flights | Travelocity");
				contain = selectOffer(pos);
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					contain = selectOffer(pos);
					breakIt = false;
				}
			}
			if (breakIt) {
				break;
			}
		}
		return contain;

	}

	public void closeModal() {

		WebElement noThanks;
		WebElement title;
		boolean breakIt = true;
		while (true) {
			breakIt = true;
			try {
				handleMultipleWindows("LAS to LAX Flights | Travelocity");
				modal = getWait()
						.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.className("modal-inner"))));
				noThanks = modal.findElement(By.id("forcedChoiceNoThanks"));
				getWait().until(ExpectedConditions.elementToBeClickable(noThanks));
				noThanks.click();
				getWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("/html/head/title"))));
				title = getDriver().findElement(By.xpath("/html/head/title"));
				if (title.getText().equals("Trip Detail | Travelocity")) {
				}
				handleMultipleWindows("LAS to LAX Flights | Travelocity");
				getWait().until(ExpectedConditions.elementToBeClickable(noThanks));
				noThanks.click();
				break;
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

	public TripDetailPage closeModalBucle() {
		boolean breakIt = true;
		while (true) {
			breakIt = true;
			try {
				handleMultipleWindows("Trip Detail | Travelocity");
				closeModal();
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					handleMultipleWindows("Trip Detail | Travelocity");
					closeModal();
					breakIt = false;
				}
			}
			if (breakIt) {
				break;
			}
		}
		return new TripDetailPage(getDriver());

	}

	public String getSortValue() {
		return sortValue;
	}

}
