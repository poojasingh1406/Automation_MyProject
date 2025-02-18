package GettingStarted;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class ExcelExample {
    public static void main(String[] args) throws IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to("https://test.rack-eye.com");
        driver.manage().window().maximize();
        String pageTitle = driver.getTitle();
        WebElement Username1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        Username1.sendKeys("pooja.singh");
        WebElement Password = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        Password.sendKeys("Asafe12!");
        WebElement LoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Login']")));
        LoginButton.click();
        Actions act = new Actions(driver);
        WebElement UserTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/user-management/view-users']")));
        act.moveToElement(UserTab).click().perform();
        System.out.println("Element got clicked");
        WebElement AddUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/user-management/create-user']")));
        act.moveToElement(AddUserButton).click().perform();
        System.out.println("Element got clicked");
//GettingDataFromExcel
        String excelFilePath = "C:\\SeleniumSoftwares\\WorkSpace\\testing\\untitled\\src\\test\\java\\FrameworkRelated\\FilePath.xlsx";
        FileInputStream file = new FileInputStream((new File(excelFilePath)));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        //int Row = sheet.getLastRowNum();
        //int cell = sheet.getRow(1).getLastCellNum();
        for (int i = 1; i <=sheet.getLastRowNum(); i++) {
          Row row  =sheet.getRow(i);
                  if(row!=null)
            {
                    Cell cell= row.getCell(0);
                String Username = cell.getStringCellValue();
                System.out.println("Username from Excel: " + Username);
                    By usernameLocator = By.xpath("//input[@id='username']");
                WebElement usernameField = driver.findElement(usernameLocator);
                usernameField.clear();  // Clear the field before entering new username
                usernameField.sendKeys(Username);  // Send the username to the field
                          }

    }
        workbook.close();
        file.close();
}}
