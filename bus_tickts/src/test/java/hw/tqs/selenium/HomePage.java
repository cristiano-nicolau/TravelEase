package hw.tqs.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("http://127.0.0.1:5500/frontend/index.html");
    }

    public void selectDeparture(String departure) {
        findElementById("departureOneWay").selectByVisibleText(departure);
    }

    public String getSelectedDeparture() {
        Select select = findElementById("departureOneWay");
        return select.getFirstSelectedOption().getText();
    }

    public void selectArrival(String arrival) {
        findElementById("arrivalOneWay").selectByVisibleText(arrival);
    }

    public String getSelectedArrival() {
        Select select = findElementById("arrivalOneWay");
        return select.getFirstSelectedOption().getText();
    }

    public void selectDepartureDate(String date) {
        driver.findElement(By.id("departureDateOneWay")).click();
        driver.findElement(By.id("departureDateOneWay")).sendKeys(date);
    }

    public String getSelectedDepartureDate() {
        return driver.findElement(By.id("departureDateOneWay")).getAttribute("value");
    }

    public void clickOneWayButton() {
        findElementByCssSelector("#One\\ Way > .w3-row-padding > .w3-button").click();
    }

    public void clickNextButton() {
        findElementByCssSelector(".w3-third:nth-child(2) .w3-button").click();
    }

    private Select findElementById(String id) {
        WebElement element = driver.findElement(By.id(id));
        return new Select(element);
    }

    private WebElement findElementByCssSelector(String selector) {
        return driver.findElement(By.cssSelector(selector));
    }
}

