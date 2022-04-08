import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SimpleTest {
    private static WebDriver driver;
    private static String url_main = "https://the-internet.herokuapp.com/";
    private static String driverProperty = "webdriver.chrome.driver";
    private static String driverPath = "src/driver/chromedriver";
    String linktext_auth = "Form Authentication";
    String pathname_auth = "login";
    String pathname_secure = "secure";
    String login_input_id = "username";
    String password_input_id = "password";
    String mail_login = "tomsmith";
    String password = "SuperSecretPassword!";
    String button_classname = "radius";
    String logout_button_selector = "#content > div > a";
    String attributeValue = "value";

    @BeforeClass
    public static WebDriver setup() {
        System.setProperty(driverProperty,driverPath);
        driver = new ChromeDriver();
        driver.get(url_main);
        return driver;
    }

    @BeforeMethod
    public void refresh(){
        driver.get(url_main);
    }

    @Test
    public void goToFormAuth(){
        driver.findElement(By.linkText(linktext_auth)).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(pathname_auth));
    }

    @Test
    public void getLoginInputAndType(){
        driver.findElement(By.linkText(linktext_auth)).click();
        driver.findElement(By.id(login_input_id)).sendKeys(mail_login);
        String inputValue = driver.findElement(By.id(login_input_id)).getAttribute(attributeValue);
        Assert.assertEquals(mail_login,inputValue);
    }

    @Test
    public void getPasswordInputAndType(){
        driver.findElement(By.linkText(linktext_auth)).click();
        driver.findElement(By.id(password_input_id)).sendKeys(password);
        String inputValue = driver.findElement(By.id(password_input_id)).getAttribute(attributeValue);
        Assert.assertEquals(password,inputValue);
    }

    @Test
    public void login(){
        driver.findElement(By.linkText(linktext_auth)).click();
        driver.findElement(By.id(login_input_id)).sendKeys(mail_login);
        driver.findElement(By.id(password_input_id)).sendKeys(password);
        driver.findElement(By.className(button_classname)).click();
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.contains(pathname_secure);
    }

    @Test
    public void logout(){
        driver.findElement(By.linkText(linktext_auth)).click();
        driver.findElement(By.id(login_input_id)).sendKeys(mail_login);
        driver.findElement(By.id(password_input_id)).sendKeys(password);
        driver.findElement(By.className(button_classname)).click();
        driver.findElement(By.cssSelector(logout_button_selector)).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains(pathname_secure));
    }

    @AfterClass
    public void cleanUp(){
        driver.quit();
    }
}
