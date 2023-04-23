package Home;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Admin_Login {
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
driver.get("https://gcreddy.com/project/admin/");
Thread.sleep(2000);
String pageTitle = driver.getTitle();
Assert.assertEquals(pageTitle, "osCommerce Online Merchant Administration Tool");
}

@Test (priority=2)
public void verifyElements() throws InterruptedException {
driver.get("https://gcreddy.com/project/admin/");
Thread.sleep(2000);

boolean ele1 = driver.findElement(By.name("username")).isDisplayed();
Assert.assertEquals(ele1, true);

boolean ele2 = driver.findElement(By.name("password")).isDisplayed();
Assert.assertEquals(ele2, true);

boolean ele3 = driver.findElement(By.id("tdb1")).isDisplayed();
Assert.assertEquals(ele3, true);
}

@Test (priority=3)
public void verifyPageRedirection() throws InterruptedException {
driver.get("https://gcreddy.com/project/admin/");
Thread.sleep(2000);
driver.findElement(By.linkText("Online Catalog")).click();
Thread.sleep(2000);
String url = driver.getCurrentUrl();

Assert.assertEquals(url, "https://gcreddy.com/project/");
}

@Test (priority=4)
public void verifyAdminLogin() throws InterruptedException {
driver.get("https://gcreddy.com/project/admin/");
driver.findElement(By.name("username")).sendKeys("gcreddy");
driver.findElement(By.name("password")).sendKeys("Temp@2020");
driver.findElement(By.id("tdb1")).click();
Thread.sleep(2000);
String url = driver.getCurrentUrl();

Assert.assertEquals(url, "https://gcreddy.com/project/admin/index.php");
}

@Test (priority=5)
public void verifyLogoff() throws InterruptedException {
driver.get("https://gcreddy.com/project/admin/");
driver.findElement(By.name("username")).sendKeys("gcreddy");
driver.findElement(By.name("password")).sendKeys("Temp@2020");
driver.findElement(By.id("tdb1")).click();

Assert.assertEquals(driver.getCurrentUrl(), "https://gcreddy.com/project/admin/index.php");
Thread.sleep(3000);
driver.findElement(By.linkText("Logoff")).click();
Thread.sleep(3000);
Assert.assertEquals(driver.getCurrentUrl(), "https://gcreddy.com/project/admin/login.php");
}

@Test (priority=6)
public void verifyErrorMessage() {
driver.get("https://gcreddy.com/project/admin/");
driver.findElement(By.name("username")).sendKeys("abcdef");
driver.findElement(By.name("password")).sendKeys("Temp@0000");
driver.findElement(By.id("tdb1")).click();

String errorMessage = driver.findElement(By.className("messageStackError")).getText();

Assert.assertTrue(errorMessage.contains("Error: Invalid administrator login attempt."));
}

@Test (priority=7)
public void adminLoginLocking() throws IOException, InterruptedException {
FileReader file = new FileReader("C:\\Users\\user\\Desktop\\input.txt");
BufferedReader br = new BufferedReader (file);
int count =0;
int iteration=0;
String line;

while ((line=br.readLine()) !=null) {
count =count+1;

if (count>1) {
String inputData[] = line.split(", ", 2);
iteration=iteration+1;

driver.get("https://gcreddy.com/project/admin/");
driver.findElement(By.name("username")).sendKeys(inputData[0]);
driver.findElement(By.name("password")).sendKeys(inputData[1]);
driver.findElement(By.id("tdb1")).click();

String errorMessage = driver.findElement(By.className("messageStackError")).getText();
Thread.sleep(3000);
if (iteration<=3) {
Assert.assertTrue(errorMessage.contains("Error: Invalid administrator login attempt."));
}
else if (iteration>3) {
Assert.assertTrue(errorMessage.contains("Error: The maximum number of login attempts has been reached. Please try again in 5 minutes."));
}
br.close();
file.close();
}
}
}

}