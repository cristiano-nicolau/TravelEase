package hw.tqs.selenium;


import java.util.function.IntPredicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ReservationPage {
    private WebDriver driver;

    public ReservationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectSeat(int seatNumber) {
        findElementByCssSelector(".seat:nth-child(" + seatNumber + ") > label").click();
    }

    public void clickConfirmButton() {
        findElementByXpath("//button[contains(text(), 'Submit')]").click();
    }

    public void fillPassengerDetails(String name, String email, String phone, String nif, String address, String city,
            String zip, String cardNumber) {
        findElementByName("name").sendKeys(name);
        findElementByName("email").sendKeys(email);
        findElementByName("phone").sendKeys(phone);
        findElementByName("nif").sendKeys(nif);
        findElementByName("address").sendKeys(address);
        findElementByName("city").sendKeys(city);
        findElementByName("zip").sendKeys(zip);
        Select cardTypeDropdown = new Select(findElementByName("cardType"));
        cardTypeDropdown.selectByValue("visa");
        findElementByName("cardNumber").sendKeys(cardNumber);
    }

    public void clickYourTicketsButton() {
        findElementByCssSelector("a > strong").click();
    }

    public void clickFinalizeButton() {
        findElementByCssSelector("#yourtickets > strong").click();
    }

    public void clickFirstRenderedDiv() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("document.querySelector('div.w3-bar-item').click();");
    }

    private WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement findElementByName(String name) {
        return driver.findElement(By.name(name));
    }

    private WebElement findElementByCssSelector(String selector) {
        return driver.findElement(By.cssSelector(selector));
    }

    public String getPassengerName() {
        return findElementByName("name").getAttribute("value");       
    }

    public String getPassengerEmail() {
        return findElementByName("email").getAttribute("value");
    }

    public String getPassengerPhone() {
        return findElementByName("phone").getAttribute("value");
    }

    public String getPassengerNif() {
        return findElementByName("nif").getAttribute("value");
    }

    public String getPassengerAddress() {
        return findElementByName("address").getAttribute("value");
    }

    public String getPassengerCity() {
        return findElementByName("city").getAttribute("value");
    }

    public String getPassengerZip() {
        return findElementByName("zip").getAttribute("value");
    }

    public String getPassengerCardNumber() {
        return findElementByName("cardNumber").getAttribute("value");
    }
}