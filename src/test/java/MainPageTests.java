import org.example.MainPage;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

//хром
@RunWith(Parameterized.class)
public class MainPageTests {

    public static WebDriver driver;
    public static MainPage objMainPage; // главная страница
    public static List<WebElement> faqElements; // список вопроса фака
    private final int index; // индекс из списка вопросов
    private final String textOfQuestion; // текст вопроса
    private final String checkedText; // проверяемый текст
    private static boolean isDebugging; // процесс отладки

    // конструктор, j  инициализирует параметры: индекс, текст вопроса, ответ
    public MainPageTests(int index, String textOfQuestion, String checkedText) {
        this.index = index;
        this.textOfQuestion = textOfQuestion;
        this.checkedText = checkedText;
    }

    @Parameterized.Parameters(name = "Проверка вопросов и ответов: " +
            "Индекс вопроса: {0}; " +
            "Текст вопроса: {1}; " +
            "Текст ответа: {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},  //такто живу
        };
    }

    @BeforeClass
    public static void initialSetup() {

        isDebugging = false;

        // Открытие страницы скутера
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver = new Augmenter().augment(driver);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        objMainPage = new MainPage(driver);
        objMainPage.waitForLoadFaq();

        // все вопросы
        faqElements = objMainPage.getFaqItems();

        if (isDebugging)
            System.out.println("Количество вопросов: "+faqElements.size());
    }

    @Test
    public void faqTest() {

        WebElement faqElement = faqElements.get(index);
        boolean buttonClickable = objMainPage.isButtonClickable(faqElement);
        assertTrue("Номер "+index+" не кликабелен", buttonClickable);

        if (!buttonClickable) return;

        faqElement.click();

        String faqQuestion;
        faqQuestion = objMainPage.getQuestionText(faqElement);
        String faqAnswer;
        faqAnswer = objMainPage.getAnswerText(faqElement);

        if (isDebugging) {
            System.out.println(faqQuestion);
            System.out.println(faqAnswer);
        }

        MatcherAssert.assertThat("Текст вопроса не совпадает: ", faqQuestion, containsString(textOfQuestion));
        MatcherAssert.assertThat("Текст ответа не совпадает: ", faqAnswer, containsString(checkedText));
    }

    @AfterClass
    public static void tearDown() {
        if (driver!=null)
        driver.quit();
    }
}

