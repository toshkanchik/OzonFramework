package ru.appline.ozoneFramework.managers;


import ru.appline.ozoneFramework.pages.BasketPage;
import ru.appline.ozoneFramework.pages.SearchPage;
import ru.appline.ozoneFramework.pages.StartPage;

public class PageMngr {
    private static PageMngr pageMngr;
    private static StartPage startPage;
    private static SearchPage searchPage;
    private static BasketPage basketPage;

    private PageMngr(){}

    public static PageMngr getPageMngr(){
        if(pageMngr == null){
            pageMngr = new PageMngr();
        }
        return pageMngr;
    }

    public static void clearAllPages(){
        startPage = null;
        searchPage = null;
        basketPage = null;
        //        pageMngr = null;
    }

    public BasketPage getBasketPage() {
        if(basketPage == null) basketPage = new BasketPage();
        return basketPage;
    }

    public StartPage getStartPage() {
        if(startPage == null) startPage = new StartPage();
        return startPage;
    }

    public SearchPage getSearchPage() {
        if(searchPage == null) searchPage = new SearchPage();
        return searchPage;
    }
}
