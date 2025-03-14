import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.MainPage;
import org.example.OrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

//в фаерфоксе, тк не робит
@RunWith(Parameterized.class)
public class OrderPageTests {

    public static WebDriver driver;
    public static MainPage objMainPage;
    public OrderPage objOrderPage;
    private final int indexButton;
    private final String name;
    private final String lastName;
    private final String adress;
    private final String metro;
    private final String phoneNumber;
    private final String dateOfOrder;
    private final String period;
    private final String colour;
    private final String comment;

    public OrderPageTests(int indexButton, String name, String lastName, String adress, String metro, String phoneNumber,
                          String dateOfOrder, String period, String colour, String comment) {
        this.indexButton = indexButton;
        this.name = name;
        this.lastName = lastName;
        this.adress = adress;
        this.metro = metro;
        this.phoneNumber = phoneNumber;
        this.dateOfOrder = dateOfOrder;
        this.period = period;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Оформление заказа: " +
            "Способ вызова: {0}; " +
            "Имя: {1}; " +
            "Фамилия: {2}; " +
            "Адрес: {3}; " +
            "Метро: {4}; " +
            "Телефон: {5}; " +
            "Когда нужен: {6}; " +
            "Срок аренды: {7}; " +
            "Цвет: {8}; " +
            "Комментарий: {9}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Валерий", "Жмышенко", "Боровая,54", "Речной вокзал", "88005553535", "20.03.2025", "сутки", "black", "Первый прогон"},
                {1, "Луффи", "МонкиД", "Гранд лайн, 100", "Проспект Мира", "12345678901", "15.04.2025", "шестеро суток", "grey", "Второй прогон"},
        };


    }

    @Before
    public void startUp() {
        WebDriverManager.firefoxdriver().setup();  // Устанавливаем драйвер для Firefox
        driver = new FirefoxDriver();
        String site = "https://qa-scooter.praktikum-services.ru/";
        driver.get(site);
    }

    @Test
    public void orderTest() {
        objMainPage = new MainPage(driver);
        objMainPage.waitForLoadPage();
        objMainPage.clickGetCookie();
        objMainPage.clickOrder(indexButton);

        objOrderPage = new OrderPage(driver);
        objOrderPage.waitForLoadOrderPage();
        objOrderPage.setDataInFieldsAndClickButtonNext(name, lastName, adress, metro, phoneNumber);
        objOrderPage.waitForLoadRentPage();
        objOrderPage.setDataInRentFieldsAndClickOrderButton(dateOfOrder, period, colour, comment);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement orderPlacedElement = wait.until(ExpectedConditions.visibilityOfElementLocated(objOrderPage.orderPlaced));

        assertTrue("Заказ оформлен", orderPlacedElement.isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
