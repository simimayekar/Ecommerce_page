package Test;

import org.testng.annotations.Test;

import Pages.LoginPage;

public class Login_Test extends BrowserConfigurations{
	
	@Test(enabled = true)
	public void Searchforphone() {
		LoginPage login = new LoginPage(driver);
		
		login.GoToMobile();
		login.Searchitem("Mobile", 15000, 20000);
		login.opentheproduct("Redmi");
		login.captureinfo();
		login.Addphonetocart("Redmi");
		login.comparehashmaps();
		
	}

}
