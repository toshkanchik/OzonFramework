package ru.appline.ozoneFramework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.ozoneFramework.managers.PageMngr;

import java.util.concurrent.TimeUnit;

import static ru.appline.ozoneFramework.managers.DriverMngr.getDriver;
//import static ru.appline.ozoneFramework.managers.PageMngr.getBasketPage;
import static ru.appline.ozoneFramework.managers.PropMngr.GetPropMngr;

public class BasePage {
    @FindBy(xpath = "//a[@data-widget='cart']")
    WebElement MenuBasket;

    protected PageMngr pages = PageMngr.getPageMngr();
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 10, 1000);
    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public <T extends BasePage> T checkPageLoaded(){return null;}

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToElementOffsetJs(WebElement element, int offset) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("javascript:window.scrollBy(0, "+ offset +");");
//        wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("javascript:window.scrollBy(0, "+ offset +");"));
    }

    protected WebElement elementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void inputTextField(WebElement field, String value) {
        scrollToElementJs(field);
        elementToBeClickable(field).click();
        field.sendKeys(Keys.CONTROL + "A");
        field.sendKeys(value);
    }

    protected boolean isElementOnPage(WebElement elem, String path){
        boolean flag = false;
        try {
            getDriver().manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
            elem.findElement(By.xpath(path));
            flag = true;
        } catch (NoSuchElementException ignore) {
        } finally {
            getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(GetPropMngr().getProperty("implicitly.wait")), TimeUnit.SECONDS);
        }
        return flag;
//        try{
//            elem.findElement(By.xpath(path));
//        }catch(NoSuchElementException ignore){
//            return false;
//        }
//        return true;
//        return !elem.findElements(By.xpath(path)).isEmpty();
    }

    public BasketPage goToBasket(){
        elementToBeClickable(MenuBasket);
        MenuBasket.click();
        return pages.getBasketPage().checkPageLoaded();
    }
}
