package ru.appline.ozoneFramework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.ozoneFramework.uils.Product;

import java.util.List;

import static ru.appline.ozoneFramework.managers.DriverMngr.getDriver;
import static ru.appline.ozoneFramework.uils.MyLogger.MyLogger;

public class SearchPage extends BasePage{
    @FindBy(xpath = "//div[contains(text(),'По запросу')]")
    private WebElement catalogHeader;

    @FindBy(xpath = "//div[contains(text(),'Цена')]/..//input[@qa-id='range-to']")
    private WebElement maxPrice;

    @FindBy(xpath = "//aside")
    private WebElement filtersMenu;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']")
    private WebElement filtersBar;

    @FindBy(xpath = "//div[@data-widget='searchResultsV2']/div/div")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//a[@data-widget='cart']/span[contains(@class, 'f-caption--bold')]")
    private WebElement numInBasket;

    @FindBy(xpath = "//div[contains(@class, 'filter')]")
    private List<WebElement> filters;


    public SearchPage waitToLoad(String shouldInclude){
        try{
            wait.until(ExpectedConditions.textToBePresentInElement(catalogHeader, shouldInclude));
        } catch (org.openqa.selenium.TimeoutException ignore){
            Assertions.fail("Не загрузилась страница поиска "+ shouldInclude);
        }
        return this;
    }

    public SearchPage setMaxPrice(String value){
        inputTextField(maxPrice, value + Keys.ENTER);
        Assertions.assertEquals(maxPrice.getAttribute("value"), value, "Максимальная цена не ввелась");
        wait.until(webDriver -> doFiltersHave(value));
        return this;
    }

    public SearchPage clickFilter(String name){
        try{//                                            иначе ozon что-то подозревает    V
            filtersMenu.findElement(By.xpath(".//span[contains(text(), '" + name + "')]/../../..")).click();
        }catch(NoSuchElementException ignore){
            Assertions.fail("Фильтра '" + name + "' не найдено");
        }
        try{
            wait.until(webDriver -> doFiltersHave(name));
        }catch(org.openqa.selenium.TimeoutException ignore){
            Assertions.fail("Фильтр '" + name + "' не пременился");
        }
        return this;
    }

    public SearchPage pickEveryNItemUpToTotalM(List<Product> products, int n){
        return pickEveryNItemUpToTotalM(products, n, 0);
    }

    public SearchPage pickEveryNItemUpToTotalM(List<Product> products, int n, int m){
        boolean dontCheckMod = (n <= 1);
        boolean checkMax = (m > 0);
        int currItemNum = 1;
        for (WebElement item : itemsList){
            //проверяем порядковый номер при необходимости
            if(dontCheckMod || (currItemNum % n != 0)){
                //проверяем отсутствие пометки "Express" и наличие кнопки "В корзину"
//                if(item.findElements(By.xpath(".//span[contains(text(), 'Express')]")).isEmpty()
//                    && !item.findElements(By.xpath(".//button//div[contains(text(), 'В корзину')]")).isEmpty()){
                if(isElementOnPage(item, ".//button//div[contains(text(), 'В корзину')]")
                        && !isElementOnPage(item, ".//span[contains(text(), 'Express')]")){
                    addToBasket(item);
                    products.add(new Product(
                        item.findElement(By.xpath(".//span/font/../../a")).getText(),//второй вариант искать текст, в который входит название товара, но его надо предать
                        Float.parseFloat(item.findElement(By.xpath(".//a//span[contains(text(), '₽')]")).getText().replaceAll("[^\\d]",""))
                    ));
                }
            }
            if(checkMax && products.size() >= m) break;
            currItemNum++;
        }
        return this;
    }

    public SearchPage inputFilterText(String name, String ... filterText){
        WebElement inputField;
        boolean wasFound;
        for(String str : filterText) {
            wasFound = false;
            MyLogger.info("обработка фильтра '" + name + "' по значению '" + str +"'");
            for (WebElement elem : filters) { //div[contains(@class, 'filter')]
                if (isElementOnPage(elem, "./div[contains(text(),'" + name + "')]")) {
                    if(isElementOnPage(elem, ".//span[contains(text(),'Посмотреть все')]"))
                        elem.findElement(By.xpath(".//span[contains(text(),'Посмотреть все')]")).click();
                    inputField = elem.findElement(By.xpath(".//input[@type = '']"));
                    elementToBeClickable(inputField);
                    inputField.click();
                    inputField.sendKeys(str + Keys.ENTER);
                    wait.until(ExpectedConditions.elementToBeClickable(elem.findElement(By.xpath("./..//span[contains" + "(text(),'" + str + "')]"))));
                    elem.findElement(By.xpath("./..//span[contains(text(),'" + str + "')]")).click();
                    try {
                        wait.until(webDriver -> doFiltersHave(str));
                    } catch (org.openqa.selenium.TimeoutException ignore) {
                        Assertions.fail("Фильтр '" + str + "' не пременился");
                    }
                    wasFound = true;
                    break;
                }
            }
            if(!wasFound)Assertions.fail("Фильтр не найдн: " + name);
        }
        return this;
    }

    private void addToBasket(WebElement item){
        WebElement button;
        int num = Integer.parseInt(numInBasket.getText());
        button = item.findElement(By.xpath(".//button//div[contains(text(), 'В корзину')]/../.."));
        scrollToElementOffsetJs(button, -400);
        elementToBeClickable(button);
        button.click();
//        try{
//            wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.xpath(".//span[contains(text(), 'шт')]"))));
////            wait.until((ExpectedCondition<Boolean>) d -> !isElementOnPage(item, ".//button//div[contains(text(), 'В корзину')]"));
//            //*[contains(text(), '"+ name +"')]
//        }catch(org.openqa.selenium.TimeoutException|NoSuchElementException ignore){
//            Assertions.fail("Не удалось нажать на кнопку 'В корзину'");
//        }
        try{
            wait.until(ExpectedConditions.textToBePresentInElement(numInBasket, String.valueOf(num+1)));
        }catch(org.openqa.selenium.TimeoutException ignore){
            Assertions.fail("Ошибка отображения количества товаров в виджете корзины");
        }
    }

    private boolean doFiltersHave(String name){
        for(WebElement filter : filtersBar.findElements(By.xpath(".//span"))){
            if (filter.getText().replaceAll("\\p{Zs}+","").toLowerCase()
                    .contains(name.replaceAll("\\p{Zs}+","").toLowerCase())) return true;
        }
        return false;
    }
}
