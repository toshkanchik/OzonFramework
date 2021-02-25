package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.appline.ozoneFramework.managers.PageMngr;
import ru.appline.ozoneFramework.uils.Product;

import java.util.LinkedList;
import java.util.List;

import static ru.appline.ozoneFramework.uils.Attachments.allureAddReport;


public class Steps {
    private PageMngr app = PageMngr.getPageMngr();
    private List<Product> itemsList = new LinkedList<>();

    @Когда("^Загружена стартовая страница$")
    public void getInitialPage(){app.getStartPage();}

    @Когда("^Поиск продукта '(.*)'$")
    public void searchForProduct(String name){app.getStartPage().searchForProduct(name);}

    @Когда("^Выставить фильтр максимальной цены '(.*)'$")
    public void setMaxPrice(String value){app.getSearchPage().setMaxPrice(value);}

    @Когда("^Отметить фильтр '(.*)'$")
    public void clickFilter(String name){app.getSearchPage().clickFilter(name);}

    @Когда("^Добавить каждый '(.*)' продукт до максимума в '(.*)' продуктов$")
    public void pickEveryNItemUpToTotalM(int n, int m){
        app.getSearchPage().pickEveryNItemUpToTotalM(itemsList, n, m);
    }

    @Когда("^Добавить каждый '(.*)' продукт$")
    public void pickEveryNItemUp(int n){
        app.getSearchPage().pickEveryNItemUpToTotalM(itemsList, n);
    }

    @Когда("^Перейти в корзину$")
    public void goToBasket(){app.getSearchPage().goToBasket();}

    @Тогда("^Проверить, что все товары находятся в корзине$")
    public void checkAllItemsInBasket(){app.getBasketPage().checkAllItemsInBasket(itemsList);}

    @Тогда("^Проверить количество товаров в корзине$")
    public void checkItemsInBasketNum(){app.getBasketPage().checkItemsInBasketNum(itemsList);}

    @Когда("^Удалить все товары из корзины$")
    public void removeAll(){app.getBasketPage().removeAll();}

    @Тогда("^Прикрепить отчёт$")
    public void addFileReport(){allureAddReport(itemsList);}

    @Когда("^Добавить в фильтр '(.*)' значения '(.*)' , '(.*)' , '(.*)'$")
    public void inputFilterText(String name, String a, String b, String c){app.getSearchPage().inputFilterText(name, a, b, c);}
}
