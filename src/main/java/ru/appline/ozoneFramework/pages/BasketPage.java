package ru.appline.ozoneFramework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.ozoneFramework.uils.Product;

import java.util.List;

import static ru.appline.ozoneFramework.managers.DriverMngr.getDriver;

public class BasketPage extends BasePage{
    @FindBy(xpath = "//div[@data-widget='header']//*[contains(text(), 'Корзина')]")
    private WebElement basketHeader;

    @FindBy(xpath = "//span[contains(text(), 'Ваша корзина')]/following-sibling::span")
    private WebElement itemsInBasketNum;

    @FindBy(xpath = "//span[contains(text(), 'Удалить выбранные')]")
    private WebElement removeSelectedButton;

    @FindBy(xpath = "//div[@data-test-id='modal-container']//button//div[text()='Удалить']/../..")
    private WebElement confirmRemove;

    @Override
    @SuppressWarnings("unchecked")
    public BasketPage checkPageLoaded() {
//        if(basketHeader.isDisplayed()) return this;
//        Assertions.fail("Страница корзины не была загружена");
        try{
            wait.until(ExpectedConditions.elementToBeClickable(basketHeader));
        }catch (TimeoutException e){
            Assertions.fail("Страница корзины не была загружена");
        }
        return this;
    }

    public BasketPage checkAllItemsInBasket(List<Product> itemsList){
        for(Product item : itemsList){
            try {
                getDriver().findElement(By.xpath("//*[text()='" + item.getName() + "']"));
            }catch(NoSuchElementException e){
                Assertions.fail("В корзине отсутствует товар " + item.getName());
            }
        }
        return this;
    }

    public BasketPage checkItemsInBasketNum(List<Product> itemsList){
        String[] numbers = itemsInBasketNum.getText().split("[^\\d]");

        Assertions.assertEquals(itemsList.size(), Integer.parseInt(numbers[0]),"Количество товаров в корзне не верно");
//        Assertions.assertTrue(()->{
//            itemsInBasketNum.getText().contains(Integer.toString(itemsList.size()))
//            return true;
//        }, "Количество товаров в корзне не верно");
        return this;
    }

    public BasketPage removeAll(){
        elementToBeClickable(removeSelectedButton);
        removeSelectedButton.click();
        elementToBeClickable(confirmRemove);
        confirmRemove.click();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-widget='emptyCart']")));
        }catch (org.openqa.selenium.TimeoutException e){
            Assertions.fail("Корзина не очистилась");
        }
        return this;
    }
}
