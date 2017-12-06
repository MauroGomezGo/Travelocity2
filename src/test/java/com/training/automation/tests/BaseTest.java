package com.training.automation.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.training.automation.pages.Browsers;
import com.training.automation.pages.MyDriver;
import com.training.automation.pages.PaymentPage;
import com.training.automation.pages.ResultPageFlights;
import com.training.automation.pages.TravelocityHomePage;
import com.training.automation.pages.TripDetailPage;

public class BaseTest {

	MyDriver myDriver;

	protected TravelocityHomePage travelocityHome;
	protected ResultPageFlights resultFlights;
	protected TripDetailPage tripDetail;
	protected PaymentPage payment;

	@BeforeSuite(alwaysRun = true)
	@Parameters("browser")
	public void beforeSuite(Browsers browser) {
		myDriver = new MyDriver(browser);
		travelocityHome = new TravelocityHomePage(myDriver.getDriver());

	}
	
	@AfterSuite(alwaysRun=true,enabled=false)
	public void afterSuite() {
		travelocityHome.dispose();
	}
	
	public TravelocityHomePage getTravelocityHomePage() {
		return travelocityHome;
	}
	
	public ResultPageFlights getResultPageFlights() {
		return resultFlights;
	}
	
	public TripDetailPage getTripDetailPage() {
		return tripDetail;
	}
	
	public PaymentPage getPaymentPage() {
		return payment;
	}
	

}
