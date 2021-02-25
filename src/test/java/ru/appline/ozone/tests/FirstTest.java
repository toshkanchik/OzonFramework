package ru.appline.ozone.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.appline.ozone.baseTestClasses.BaseTestClass;
import ru.appline.ozoneFramework.uils.Product;

import java.util.LinkedList;
import java.util.List;

import static ru.appline.ozoneFramework.uils.Attachments.addReport;

public class FirstTest extends BaseTestClass {
    @Tag("Add Iphones")
    @Test
    public void testIphones(){
        List<Product> itemsList = new LinkedList<>();

        pages.getStartPage()
            .searchForProduct("iphone")
            .setMaxPrice("100000")
            .clickFilter("Высокий рейтинг")
            .clickFilter("NFC")
            .pickEveryNItemUpToTotalM(itemsList,2, 8)
            .goToBasket()
//            .checkPageLoaded()
            .checkAllItemsInBasket(itemsList)
            .checkItemsInBasketNum(itemsList)
            .removeAll()
        ;
        addReport(itemsList);
    }


    @Tag("Earpods")
    @Test
    public void textEarpods(){
        List<Product> itemsList = new LinkedList<>();
        pages.getStartPage()
                .searchForProduct("беспроводные наушники")
                .setMaxPrice("10000")
                .clickFilter("Высокий рейтинг")
                .inputFilterText("Бренды","Beats", "Samsung", "Xiaomi")
                .pickEveryNItemUpToTotalM(itemsList,2)
                .goToBasket()
//                .checkPageLoaded()
                .checkAllItemsInBasket(itemsList)
                .checkItemsInBasketNum(itemsList)
                .removeAll()
        ;
        addReport(itemsList);

    }
}
