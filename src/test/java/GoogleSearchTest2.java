//    Задание 2
//  Напиши автотест на Гугл-поиск. В поисковую строку вводятся слова
//  «купить кофемашину bork c804», результатов больше 10 и в выдаче
//  присутствует mvideo.ru.
//
//    Вариант 2

//    В этом варианте я имею ввиду, что результаты поиска считаются на одной странице.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GoogleSearchTest2 {
    WebDriver driver;
    List<WebElement> list;
    String mvideo = "https://www.mvideo.ru";
    private WebDriverWait wait;

    private static boolean isResultContainsMVideo(List<WebElement> list, String str) {
        for (WebElement element : list) {
            if (element.getText().contains(str)) {
                return true;
            }
        }
        return false;
    }

    protected WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, 10);
        }
        return wait;
    }

    @BeforeMethod
    private void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/gkukl/Desktop/QA/WebDriver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void afterTest() {
        driver.close();
    }

    @Test
    public void searchGoogle() {
        driver.get("https://www.google.com/");

        WebElement buttonSettings = driver.findElement(By.xpath("//button[@jsname ='pzCKEc']"));
        buttonSettings.click();
        WebElement buttonSearchSettings = driver.findElement(By.xpath("//a[text() ='Search settings']"));
        buttonSearchSettings.click();
        WebElement resultSlider = driver.findElement(By.xpath("//*[@id='result_slider']/ol/li[2]"));
        resultSlider.click();
        WebElement buttonSave = driver.findElement(
                By.xpath("//*[@id='form-buttons']/div[1]"));
        buttonSave.click();
        getWait().until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        WebElement input = driver.findElement(By.className("gLFyf"));
        input.sendKeys("купить кофемашину bork c804\n");
        List<WebElement> resultSearch = driver.findElements
                (By.xpath("//div[@class='g']/div/div/div/a[contains(@href,'https')]"));

        Assert.assertTrue(resultSearch.size() > 10);
        Assert.assertTrue(isResultContainsMVideo(resultSearch, mvideo));
    }
}
