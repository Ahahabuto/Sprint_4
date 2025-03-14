package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;

    // элементы страницы заказа
    private final By orderHeader = By.xpath(".//div[(text()= 'Для кого самокат')]");
    private final By inputName = By.xpath("//input[@placeholder ='* Имя']");
    private final By inputLastName = By.xpath(".//input[@placeholder ='* Фамилия']");
    private final By inputAdress = By.xpath(".//input[@placeholder = '* Адрес: куда привезти заказ']");
    private final By inpputMetro = By.xpath(".//input[@placeholder ='* Станция метро']");
    private final By inputPnoneNumber = By.xpath(".//input[@placeholder ='* Телефон: на него позвонит курьер']");
    private final By buttonNext = By.xpath(".//div[starts-with(@class,'Order_NextButton')]//button[contains(text(), 'Далее')]");
    private final By rentHeader = By.xpath(".//div[(text()= 'Про аренду')]");
    private final By inputCalendar = By.xpath("//input[@placeholder ='* Когда привезти самокат']");
    private final By inputRentalPeriod = By.className("Dropdown-placeholder");
    private final By inputComment = By.xpath(".//input[@placeholder ='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//div[starts-with(@class,'Order_Buttons')]//button[contains(text(), 'Заказать')]");
    private final By yesButton = By.xpath("//button[contains(text(), 'Да')]");
    public By orderPlaced = By.xpath("//div[(text()= 'Заказ оформлен')]");

    public OrderPage(WebDriver driver){
        this.driver = driver;
    }

    public OrderPage() {
        driver = null;
    }

    // метод ддля ожидания загрузки страницы заказа
    public void waitForLoadOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(getTitleOrder()));
    }

    // Метод для заполнения полей и нажатие на кнопку Далее
    public void setDataInFieldsAndClickButtonNext(String name, String lastName, String address, String metro, String phoneNumber) {
        getName().sendKeys(name);
        getLastName().sendKeys(lastName);
        getAddress().sendKeys(address);
        getMetro().sendKeys(metro, Keys.ARROW_DOWN, Keys.ENTER);
        getPhoneNumber().sendKeys(phoneNumber);
        getButtonNext().click();
    }

    // метод для ожидания загрузки страницы аренды
    public void waitForLoadRentPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(getTitleRent()));
    }

    // метод для заполнения полей на странице аренды и оформления заказа
    public void setDataInRentFieldsAndClickOrderButton(String calendarDate, String rentalPeriod, String colour, String comment) {
        getCalendar().sendKeys(calendarDate, Keys.ARROW_DOWN, Keys.ENTER);
        getRentalPeriod().click();
        getDays(rentalPeriod).click();
        getColour(colour).click();
        getComment().sendKeys(comment);
        getOrderButton().click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> (getButtonYes().isDisplayed()));
        getButtonYes().click();
    }

    public By getTitleOrder() {
        return orderHeader;
    }

    public By getTitleRent() {
        return rentHeader;
    }

    public WebElement getName() {
        return driver.findElement(inputName);
    }

    public WebElement getLastName() {
        return driver.findElement(inputLastName);
    }

    public WebElement getAddress() {
        return driver.findElement(inputAdress);
    }

    public WebElement getMetro() {
        return driver.findElement(inpputMetro);
    }

    public WebElement getPhoneNumber() {
        return driver.findElement(inputPnoneNumber);
    }

    public WebElement getCalendar() {
        return driver.findElement(inputCalendar);
    }

    public WebElement getRentalPeriod() {
        return driver.findElement(inputRentalPeriod);
    }

    // Метод для получения элемента с конкретным количеством дней аренды
    public WebElement getDays(String valueDays) {
        return driver.findElement(By.xpath(".//div[@class='Dropdown-menu']/div[text()='"+valueDays+"']"));
    }

    public WebElement getColour(String colourName) {
        return driver.findElement(By.id(colourName));
    }

    public WebElement getComment() {
        return driver.findElement(inputComment);
    }

    public WebElement getButtonNext() {
        return driver.findElement(buttonNext);
    }

    public WebElement getOrderButton() {
        return driver.findElement(orderButton);
    }

    public WebElement getButtonYes() {
        return driver.findElement(yesButton);
    }
}
