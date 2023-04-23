package Home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class User_Sanity {
WebDriver driver;

@BeforeMethod
public void launchBrowser() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().window().maximize();
}


@AfterMethod
public void closeBrowser() {
driver.close();
}

@Test (priority=1)
public void verifyLaunchApp() throws InterruptedException {
driver.get("http://gcreddy.com/project/");
Thread.sleep(2000);

String pageTitle = driver.getTitle();
Assert.assertEquals("GCR Shop", pageTitle);
}

@Test (priority=2)
public void verifyElements() throws InterruptedException {
driver.get("http://gcreddy.com/project/");
Thread.sleep(2000);

boolean element1 = driver.findElement(By.linkText("create an account")).isDisplayed();
Assert.assertEquals(element1, true);

boolean element2 = driver.findElement(By.linkText("login")).isDisplayed();
Assert.assertEquals(element2, true);
}

@Test (priority=3)
public void verifyCustomerReg() throws InterruptedException {
driver.get("https://gcreddy.com/project/create_account.php");
driver.findElement(By.xpath("//*[@id='bodyContent']/form/div/div[2]/table/tbody/tr[1]/td[2]/input[2]")).click();
driver.findElement(By.name("firstname")).sendKeys("abcd");
driver.findElement(By.name("lastname")).sendKeys("xyz");
driver.findElement(By.id("dob")).sendKeys("10/10/1990");

driver.findElement(By.name("email_address")).sendKeys("decseleniuma2020@gmail.com");
driver.findElement(By.name("street_address")).sendKeys("abcd sdf");
driver.findElement(By.name("postcode")).sendKeys("500072");
driver.findElement(By.name("city")).sendKeys("Hyderabad");
driver.findElement(By.name("state")).sendKeys("Telangana");

Select dropDown = new Select (driver.findElement(By.name("country")));
dropDown.selectByVisibleText("India");

driver.findElement(By.name("telephone")).sendKeys("9876765654");

driver.findElement(By.name("password")).sendKeys("abcd321");
driver.findElement(By.name("confirmation")).sendKeys("abcd321");
driver.findElement(By.id("tdb4")).click();

driver.findElement(By.name("password")).sendKeys("abcd321");
driver.findElement(By.name("confirmation")).sendKeys("abcd321");
driver.findElement(By.id("tdb4")).click();
Thread.sleep(3000);

String message = driver.findElement(By.tagName("h1")).getText();
//System.out.println(message);

Assert.assertEquals(message, "Your Account Has Been Created!");
}

@Test (priority=4)
public void verifyLogin() {
driver.get("https://gcreddy.com/project/login.php");
driver.findElement(By.name("email_address")).sendKeys("decseleniuma2020@gmail.com");
driver.findElement(By.name("password")).sendKeys("abcd321");
driver.findElement(By.id("tdb1")).click();

boolean elementExists = driver.findElement(By.linkText("Log Off")).isDisplayed();
Assert.assertEquals(elementExists, true);
}

@Test (priority=5)
public void verifyShoppingCart() {
driver.get("https://gcreddy.com/project/index.php");
driver.findElement(By.xpath("//*[@id='columnRight']/div/div[1]/a")).click();
String message = driver.findElement(By.xpath("//*[@id='bodyContent']/div/div")).getText();
//System.out.println(message);

Assert.assertTrue(message.contains("Your Shopping Cart is empty!"));
}

@Test (priority=6)
public void verifyCheckout() throws InterruptedException {
driver.get("https://gcreddy.com/project/index.php");
driver.findElement(By.linkText("login")).click();
driver.findElement(By.name("email_address")).sendKeys("decselenium2020@gmail.com");
driver.findElement(By.name("password")).sendKeys("abcd321");
driver.findElement(By.id("tdb1")).click();

driver.findElement(By.xpath("//*[@id='bodyContent']/div/div[2]/table/tbody/tr/td[3]/a[1]/img")).click();
driver.findElement(By.id("tdb5")).click();
Thread.sleep(2000);
driver.findElement(By.id("tdb6")).click();
Thread.sleep(2000);
driver.findElement(By.id("tdb6")).click();
Thread.sleep(2000);
driver.findElement(By.id("tdb6")).click();
Thread.sleep(2000);
driver.findElement(By.id("tdb5")).click();
}

}
