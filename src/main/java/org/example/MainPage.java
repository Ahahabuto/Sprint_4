package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    // вебдрайвер
    private final WebDriver driver;

    // секция "Вопросы о важном"
    private final By sectionFaq = By.xpath(".//div[starts-with(@class,'Home_FAQ')]");

    // Каждый элемент секции "Вопросы о важном"
    private final By accordionItem = By.className("accordion__item");

    // Кнопка оформления заказа в шапке
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class,'Header_Nav')]//button[text()='Заказать']");

    // кнопка с вопросом
    private final By accordionButton = By.className("accordion__button");

    // панель с ответом на вопрос
    private final By accordionPanel = By.className("accordion__panel");

    // изображение самоката
    private final By scooterImage = By.xpath(".//img[@alt = 'Scooter blueprint']");

    // принять куки
    private final By acceptCookieButton = By.id("rcc-confirm-button");

    // Кнопка оформления заказа в теле сайта под "Как это работает"
    private final By orderButtonBody = By.xpath(".//div[contains(@class,'FinishButton')]//button[text()='Заказать']");

    // Конструктор класса, принимает драйвер
    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    // метод для ожидания загрузки главной страницы
    public void waitForLoadPage() {
        WebElement imageElement = driver.findElement(scooterImage);  // Находим элемент изображения samokata
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(scooterImage));  // Ждем, пока изображение станет видимым
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", imageElement);  // Прокручиваем страницу до изображения
    }

    // метод для ожидания загрузки "Вопросы о важном"
    public void waitForLoadFaq() {
        WebElement faqElement = driver.findElement(sectionFaq);  // Находим элемент секции
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(sectionFaq));  // Ждем, пока секция станет видимой
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqElement);  // Прокручиваем страницу до секции
    }

    // метод для проверки существования элемента на странице по локатору
    public boolean isElementExtist(By locatorBy) {
        try {
            driver.findElement(locatorBy);
            return true; // Если элемент найден, возвращаем труе
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    // клик по кукам
    public void clickGetCookie() {
        if (isElementExtist(acceptCookieButton))
            driver.findElement(acceptCookieButton).click();
    }

    // метод для получения эл-тов "вопрос ответ"
    public List<WebElement> getFaqItems() {
        return driver.findElements(accordionItem);
    }

    // метод для получения текста вопроса из аккордеона
    public String getQuestionText(WebElement faqElement) {
        return faqElement.findElement(accordionButton).getText();
    }

    // метод для получения текста ответа в выпадающем списке
    public String getAnswerText(WebElement faqElement) {
        return faqElement.findElement(accordionPanel).getText();
    }

    // метод для проверки можно ли нажать на кнопку в эл-те аккордеона
    public boolean isButtonClickable(WebElement faqElement) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(faqElement.findElement(accordionButton)));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }


    // метод для клика по кнопке заказа в зависимости от того, где оная находится: шапка/тело
    public void clickOrder(int indexButton) {
        switch (indexButton) {
            case 0: // if shapka button
                driver.findElement(orderButtonHeader).click();
                break;
            case 1: // if telo button
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                WebElement orderButton = driver.findElement(orderButtonBody);
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (orderButton.isDisplayed()));
                orderButton.click();
                break;
            }
        }
    }
