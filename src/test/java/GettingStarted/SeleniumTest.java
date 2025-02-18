package GettingStarted;

import com.google.errorprone.annotations.Var;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.JsonToWebElementConverter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.sql.SQLOutput;

public class SeleniumTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver= new ChromeDriver();
        driver.navigate().to("https://test.rack-eye.com");
        driver.manage().window().maximize();
       String pageTitle= driver.getTitle();
        boolean IsPageContainsTitle= pageTitle.contains("Conek Cloud");

           System.out.println("value"+IsPageContainsTitle);
      for(String handle: driver.getWindowHandles()) {
          System.out.println("handle"+handle);
      }
        System.out.println("mY CURRENT URL"+driver.getCurrentUrl());
        //driver.close();
    }
}
