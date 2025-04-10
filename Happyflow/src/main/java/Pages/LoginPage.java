package Pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.qameta.allure.model.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import io.qameta.allure.Allure;

public class LoginPage {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor executor;
	SoftAssert softAssert = new SoftAssert();
	
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
	}
	  Map<String, String> beforeCartMap = new HashMap<>();
	  Map<String, String> afterCartMap = new HashMap<>();
	By signin = By.xpath("//span[contains(text(),'sign in')]");
	By clicksignin = By.xpath("//span[text()='Sign in']");
	By openmobile = By.xpath("//a[contains(text(),'Mobile')]");
	By searchbox = By.xpath("//input[@placeholder='Search Amazon.in']");
	By productname = By.id("title");
	By price = By.xpath("//span[contains(@class,'priceToPay')]");
	By productname2=By.xpath("//span[contains(@class,'item-product-title')]//h4");
	By price2 = By.xpath("//span[contains(@class,'product-price')]");
	By addtocart = By.xpath("//input[@title='Add to Shopping Cart' and @type='submit']");
	By gotocart = By.xpath("((//span[contains(text(),'Proceed to checkout')]/preceding::span)[last()]//preceding::input)[last()]");
	By crossicon = By.xpath("//a[contains(@id,'close')]");
	By clickoncart=By.id("nav-cart");
	By scrollablepage = By.xpath("(//div[contains(@class,'scroller-vertical')])[last()]");
	
	
	

	public void GoToMobile() {
		try {
			Allure.step("Launched the page");
			savescreenshot("Launched the page");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));  
			String pageTitle = driver.getTitle();
	        System.out.println("Page Title: " + pageTitle);
			wait.until(ExpectedConditions.elementToBeClickable(openmobile));
			driver.findElement(openmobile).click();
			waitForPageToLoad(driver);
			savescreenshot("Mobile section opened");
			Allure.step("Mobile section opened",Status.PASSED);
			
		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			Allure.step("Exception while navigating to Mobiles section " +e.toString(), Status.FAILED);
		}
	}
	
	public void Searchitem(String category, int startrange, int endrange) {
		try {
			Actions action = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(searchbox));
			String Searchfor = category+" from "+startrange+" to "+endrange;
			System.out.println(Searchfor);
			driver.findElement(searchbox).sendKeys(Searchfor);
			action.sendKeys(Keys.ENTER).build().perform();
			
			waitForPageToLoad(driver);
			
			String pageTitle = driver.getTitle();
	        System.out.println("Page Title: " + pageTitle);
			
	        softAssert.assertTrue(pageTitle.contains(Searchfor));
	        softAssert.assertAll();
	        
	        savescreenshot("Searched the item");
	        Allure.step("Searched the item",Status.PASSED);
			
		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			Allure.step("Exception while searching in the "+category+ "category" +e.toString(), Status.FAILED);
		}
	}
	
	public void opentheproduct(String brandname) {
		try {
			 String mainWindowHandle = driver.getWindowHandle();
		        System.out.println("Main window handle: " + mainWindowHandle);

		        String beforexpath ="//h2[text()='Results']//following::span[contains(text(),'";
		        String afterxpath = "')]";
		        By clickphone = By.xpath(beforexpath+brandname+afterxpath);
		        wait.until(ExpectedConditions.presenceOfElementLocated(clickphone));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(clickphone));	
		        wait.until(ExpectedConditions.elementToBeClickable(clickphone));
		        driver.findElement(clickphone).click();
		        
		        // Get all window handles
		        Set<String> allWindowHandles = driver.getWindowHandles();
		        System.out.println("All window handles: " + allWindowHandles);

		        // Switch to the new window
		        for (String handle : allWindowHandles) {
		            if (!handle.equals(mainWindowHandle)) {
		                driver.switchTo().window(handle);  // Switch to the new window
		                System.out.println("Switched to new window with handle: " + handle);
		                break;
		            }
		        }

		        // Perform actions on the new window (e.g., print the title of the new window)
		        System.out.println("New window title: " + driver.getTitle());
		        savescreenshot("Navigated to new window");
		        Allure.step("Navigated to new window",Status.PASSED);


		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			Allure.step("Exception while clicking on the product" +e.toString(), Status.FAILED);
		}
	}
	
	public void captureinfo() {
		try {
		
			 	wait.until(ExpectedConditions.presenceOfElementLocated(productname)); 
		        String productName = driver.findElement(productname).getText();
		        
		        wait.until(ExpectedConditions.presenceOfElementLocated(price)); // Example for price
		        String productPrice = driver.findElement(price).getText();
		        String productprice = productPrice+".00";
		        
		        System.out.println("Before adding to cart:");
		        System.out.println("Product Name: " + productName);
		        System.out.println("Product Price: " + productprice);
		        
		        beforeCartMap.put("Product Name", productName);
		        beforeCartMap.put("Product Price", productprice);
		        
		        savescreenshot("capture the phone details page");
		        Allure.step("capture the phone details page", Status.PASSED);
		       
		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			Allure.step("Exception while capturing product information" +e.toString(), Status.FAILED);
		}
	}
	
	public void Addphonetocart(String brandname) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(addtocart));	
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -100);");
			wait.until(ExpectedConditions.elementToBeClickable(addtocart));
			Actions action = new Actions(driver);
			//action.moveToElement(driver.findElement(addtocart)).build().perform();
			driver.findElement(addtocart).click();
		

			wait.until(ExpectedConditions.elementToBeClickable(gotocart));
//			action.moveToElement(driver.findElement(crossicon));
//			//driver.findElement(gotocart).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(addtocart));
			waitForPageToLoad(driver);
			//Thread.sleep(3000);
			//wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(scrollablepage));
		driver.navigate().refresh();
//			
//			waitForPageToLoad(driver);
			wait.until(ExpectedConditions.elementToBeClickable(clickoncart));
			driver.findElement(clickoncart).click();
			waitForPageToLoad(driver);
			
			wait.until(ExpectedConditions.presenceOfElementLocated(productname2)); 
	        String productName = driver.findElement(productname2).getText();
	        
	        wait.until(ExpectedConditions.presenceOfElementLocated(price2)); // Example for price
	        String productPrice = driver.findElement(price2).getText();
	        
	        System.out.println("After adding to cart:");
	        System.out.println("Product Name: " + productName);
	        System.out.println("Product Price: " + productPrice);
	        
	        afterCartMap.put("Product Name", productName);
	        afterCartMap.put("Product Price", productPrice);	
	        
	        savescreenshot("Item added to cart");
	        Allure.step("Item added to cart", Status.PASSED);
			
		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			Allure.step("Exception while adding phone to cart " +e.toString(), Status.FAILED);
		}
	}

	
	public void comparehashmaps() {
		try {
			 if (beforeCartMap.equals(afterCartMap)) {
		            System.out.println("The product details are the same before and after adding to the cart.");
		            Allure.step("The product details are the same before and after adding to the cart.", Status.PASSED);
		        } else {
		            System.out.println("The product details do not match before and after adding to the cart.");
		            Allure.step("The product details do not match before and after adding to the cart.", Status.FAILED);
		        }
			
		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			Allure.step("Exception while comparing hashmaps " +e.toString(), Status.FAILED);
		}
	}
	
	
	
	public static void waitForPageToLoad(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        while (!js.executeScript("return document.readyState").toString().equals("complete")) {
            try {
                Thread.sleep(1000);  // Wait for 1 second before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	public void savescreenshot(String screenshotname) {
		try {	
			File s = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileInputStream inputStream = new FileInputStream(s);
			Allure.addAttachment(screenshotname, inputStream);
			Thread.sleep(5000);
		} catch (Exception e) {
			Allure.step("Exception occured while taking screenshot " + e.toString(),Status.FAILED);
		
		}

}
}
