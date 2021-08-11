//    Задание 2
//  Напиши автотест на Гугл-поиск. В поисковую строку вводятся слова
//  «купить кофемашину bork c804», результатов больше 10 и в выдаче
//  присутствует mvideo.ru.
//
//    Вариант 1

//    В этом варианте я имею ввиду, что результаты поиска считаются не на одной странице, а всего.

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GoogleSearchTest {
    ChromeDriver driver;
    String mvideo = "https://www.mvideo.ru";

    private static boolean isRequest(List<WebElement> list, String str) {
        for (WebElement element : list) {
            if (element.getText().contains(str)) {

                return true;
            }
        }

        return false;
    }

    private static int getResult(WebElement quantity) {
        String sentence = quantity.getText();
        String[] words = sentence.replace(",", "").trim().split(" ");
        Integer totalAnswer = Integer.parseInt(words[1]);

        return totalAnswer;
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
        WebElement input = driver.findElement(By.className("gLFyf"));
        input.sendKeys("купить кофемашину bork c804\n");

        List<WebElement> resultSearch = driver.findElements
                (By.xpath("//div[@class='g']//a[contains(@href,'https')]"));
        WebElement quantity = driver.findElement(By.id("result-stats"));

        Assert.assertTrue(isRequest(resultSearch, mvideo));
        Assert.assertTrue(driver.findElement(By.xpath("//tbody//a[@aria-label='Page 2']")).isDisplayed());
        Assert.assertTrue(getResult(quantity) > 10);
    }
}



