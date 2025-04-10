package Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BrowserConfigurations {

	public WebDriver driver;
	WebDriverWait wait;

	//LoginPage login;
	/*
	@BeforeMethod
	public void startUpactions() throws InterruptedException, IOException {

		driver.get("https://www.amazon.in/");
		// login method
		
		System.out.println("startup");

	}*/

	
	
	@BeforeTest
	public void setUp() {
	//setup the web driver
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	//configure web driver
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.manage().window().maximize();
	driver.get("https://www.amazon.in/");
	//initializing the page object
	//LoginPage login = new LoginPage(driver);
	}
	
	@AfterClass
	public void tearDownActions() throws InterruptedException, IOException {

		//driver.quit();

	}

	
}