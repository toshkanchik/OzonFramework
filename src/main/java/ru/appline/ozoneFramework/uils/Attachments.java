package ru.appline.ozoneFramework.uils;

import io.qameta.allure.Attachment;

import java.util.List;

public class Attachments {
    @Attachment(value = "отчёт")
    public static String addReport(List<Product> products) {
        String out = "";
        float maxPrice = 0;
        String maxPriceName = "";
        for (Product product : products){
            out = out + product.getName() + "\nprice: " + product.getPrice() + "\n";
            if(maxPrice < product.getPrice()){
                maxPrice = product.getPrice();
                maxPriceName = product.getName();
            }
        }
        out = out + "The most expensive item:\n" + maxPriceName + "\nprice: " + maxPrice + "\n";
        return out;
    }
}
