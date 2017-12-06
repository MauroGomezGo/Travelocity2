package com.training.automation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.training.automation.pages.TravelocityHomePage;
import junit.framework.Assert;

public class TravelocityHomeTest extends BaseTest {

	@DataProvider(name = "home1")
	public Object[][] exersice1_1() {
		return new Object[][] { { "LAS", "LAX", 2, 7, "1" } };

	}

	@DataProvider(name = "sortValues")
	public Object[][] exersice1_2() {
		return new Object[][] { { "Price (Lowest)" }, { "Price (Highest)" }, { "Duration (Shortest)" },
				{ "Duration (Longest)" }, { "Departure (Earliest)" }, { "Departure (Latest)" },
				{ "Arrival (Earliest)" }, { "Arrival (Latest)" }, };

	}

	@DataProvider(name = "sortBy")
	public Object[][] exersice1_3() {
		return new Object[][] { { "Duration (Shortest)" } };

	}

	@DataProvider(name = "departure")
	public Object[][] exercise1_4() {
		return new Object[][] { { 1, "Duration (Shortest)" } };
	}

	@DataProvider(name = "return")
	public Object[][] exercise1_5() {
		return new Object[][] { { 3, "Duration (Shortest)" } };
	}

	@Test(dataProvider = "home1", priority = 1)
	public void exercise_1_1(String from, String to, int monthsFromNow, int tripDuration, String passengersAdults) {
		TravelocityHomePage travelocityHome = getTravelocityHomePage();
		travelocityHome.roundtrip();
		travelocityHome.fillDestintation(from, to);
		travelocityHome.selectDate(monthsFromNow, tripDuration);
		travelocityHome.adults(passengersAdults);
		resultFlights = travelocityHome.searchButton();
		Assert.assertNotNull("No fue posible realizar la busqueda", resultFlights);
	}

	@Test(dataProvider = "sortValues", priority = 2)
	public void exersice_1_2_a(String sortOption) {
		resultFlights.verifySortBox(sortOption);
		Assert.assertEquals("The sort box doesn't contain the value " + sortOption, sortOption,
				resultFlights.getSortValue());
	}

	@Test(priority = 3)
	public void exersice_1_2_bcd() {
		Assert.assertTrue("One or more offers doesn't have \"Select\" button", resultFlights.verifySelectButton());
		Assert.assertTrue("One or more offers doesn't have \"Flight duration\"",
				resultFlights.verifyFlightDestination());
		Assert.assertTrue("One or more offers doesn't have \"Flight details and baggage fees\"",
				resultFlights.verifyDetails());
	}

	@Test(dataProvider = "sortBy", priority = 4, enabled = true)
	public void exercise_1_3(String sortOption) {
		Assert.assertTrue("Sort action Fail", resultFlights.sortBy(sortOption));

	}

	@Test(priority = 5)
	public void exersice_1_3_a() {
		Assert.assertTrue("The offers are wrong sorted",resultFlights.verifyODS());
	}

	@Test(dataProvider = "departure", priority = 6)
	public void exersice_1_4(int pos, String sortOption) {
		Assert.assertTrue("Selecting Offer to departure fail",resultFlights.selectOffer(pos));

	}
	
	@Test(dataProvider = "return", priority = 7)
	public void exersice_1_5(int pos, String sortOption) {
		
		Assert.assertTrue("Selecting Offer to return fail",resultFlights.selectOfferBucle(pos));
		tripDetail = resultFlights.closeModalBucle();
	}
	
	@Test(priority = 8)
	public void exercise_1_67() {
		Assert.assertTrue("The Trip Page doesn't contain the Trip Total",tripDetail.verifyTripTotal());
		Assert.assertTrue("The Trip Page doesn't contain the Departure Info",tripDetail.verifyDepartureInfo());
		Assert.assertTrue("The Trip Page doesn't contain the Return Info",tripDetail.verifyReturnInfo());
		Assert.assertTrue("The Trip Page doesn't contain the Price Guarantee Text",tripDetail.verifyPriceGText());
		payment = tripDetail.continueBooking();
		
	}
	
	@Test(priority = 9)
	public void exersice_1_8() {
		SoftAssert	form = new SoftAssert();
		form.assertTrue(payment.verifyFirstName(),"The Payment Page doesn't contain the First Name input");
		form.assertTrue(payment.verifyMiddleName(),"The Payment Page doesn't contain the Middle Name input");
		form.assertTrue(payment.verifyLastName(),"The Payment Page doesn't contain the Last Name input");
		form.assertTrue(payment.verifyCountryCode(),"The Payment Page doesn't contain the Country Code dropdown list");
		form.assertTrue(payment.verifyPhoneNumber(),"The Payment Page doesn't contain the Phone Number input");
	}

}
