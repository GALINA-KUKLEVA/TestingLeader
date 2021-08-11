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

public class YandexMailTest {

//    Задание 1.
//    Напиши автотест на вход в почтовый ящик на www.yandex.ru.



//    Поскольку при создании случайных логина и пароля я в какой то момент застреваю на проверке пользователя вручную
//            (предлагается ввести код, отправленный на телефон или ввести символы с картинки,
//             что автоматически я делать не умею), я создала личный почтовый ящик на Яндексе и его тестировала.
//
//    Ниже привожу блок кода для создания случайного пользователя:
//    private void createUser() {
//
//        driver.get("https://yandex.ru");
//        driver.findElement(By.cssSelector(".sc-jrsJCI")).click();
//        driver.get("https://passport.yandex.ru");// Open website
//        driver.findElement(By.xpath("//*[@id='passp:exp-register']")).click();// Find the link to registration form  // Click the link
//        driver.findElement(By.cssSelector("#firstname")).sendKeys(firstName);
//        driver.findElement(By.cssSelector("#lastname")).sendKeys(lastName);
//        driver.findElement(By.cssSelector("#login")).sendKeys(randomEmail);// Find the email form field  // Type the random email to the form
//        driver.findElement(By.cssSelector("#password")).sendKeys(randomPassword);
//        driver.findElement(By.cssSelector("#password_confirm")).sendKeys(randomPassword);
//        driver.findElement(By.cssSelector("#phone")).sendKeys(phoneNumber);
//        driver.findElement(By.xpath("//button[@type='submit']")).click();
//    }

//    Так как я не хочу делиться личными данными  своего почтового ящика,
//    предлагаю ввести данные от любого ящика в Яндексе и проверить таким образом тесты.
//    Но не думаю, что они будут стабильны из-за защиты от ботов.



    private WebDriver driver;
    private WebDriverWait wait;
    private String login = "";
    private String passwd = "";

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/gkukl/Desktop/QA/WebDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    protected WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, 10);
        }

        return wait;
    }

    @Test
    public void enterYandexMailPositive() {
        driver.get("https://yandex.ru/");
        driver.findElement(By.cssSelector(".desk-notif-card__login-new-item_enter")).click();
        driver.findElement(By.id("passp:sign-in")).click();
        driver.findElement(By.cssSelector("#passp-field-login")).sendKeys("(!ввести логин!)");
        driver.findElement(By.id("passp:sign-in")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("passp-field-passwd")))
                .sendKeys(passwd + "\n");

        WebElement userName = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("a[role='button'] > .desk-notif-card__user-name.username")));
        Assert.assertEquals(userName.getText(), login);
    }

    @Test
    public void inputInvalidLogin() {
        driver.get("https://yandex.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".desk-notif-card__login-new-item_enter")).click();
        driver.findElement(By.id("passp:sign-in")).click();
        driver.findElement(By.cssSelector("#passp-field-login")).sendKeys("(!ввести неправильный логин!)");
        driver.findElement(By.id("passp:sign-in")).click();

        Assert.assertEquals(driver.findElement(By.id("field:input-login:hint")).getText(), "Логин не указан");
    }

    @Test
    public void inputInvalidPassword() {
        driver.get("https://yandex.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".desk-notif-card__login-new-item_enter")).click();
        driver.findElement(By.id("passp:sign-in")).click();
        driver.findElement(By.cssSelector("#passp-field-login")).sendKeys("(!ввести логин!)");
        driver.findElement(By.id("passp:sign-in")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("passp-field-passwd")))
                .sendKeys("(! ввести неправильный пароль!)");

        Assert.assertEquals(getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.id("field:input-passwd:hint"))).getText(), "Неверный пароль");
        Assert.assertEquals(driver.findElements(
                By.cssSelector("a[role='button'] > .desk-notif-card__user-name.username")).size(), 0);
    }
}
