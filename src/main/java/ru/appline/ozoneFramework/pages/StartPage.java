package ru.appline.ozoneFramework.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends BasePage{
    @FindBy(xpath = "//input[@placeholder = 'Искать на Ozon']")
    private WebElement searchField;

    public SearchPage searchForProduct(String name){
        inputTextField(searchField, name + Keys.ENTER);
        return pages.getSearchPage().waitToLoad(name);
    }
}
