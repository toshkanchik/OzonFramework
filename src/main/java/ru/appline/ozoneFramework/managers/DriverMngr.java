package ru.appline.ozoneFramework.managers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverMngr {
    private static DriverMngr instance = null;
    private static WebDriver driver;

    private DriverMngr(){
        PropMngr props = PropMngr.GetPropMngr();
        switch (props.getProperty("type.browser")) {
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty("path.edge.driver.windows"));
                driver = new EdgeDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty("path.chrome.driver.windows"));
                driver = new ChromeDriver();
                break;
            default:
                Assertions.fail("Браузер '" + props.getProperty("type.browser") + "' не поддерживается");
        }
    }

    public static WebDriver getDriver() {
        if(instance == null)
            instance = new DriverMngr();
        return driver;
    }

    public static void closeDriver() {
        if(driver != null)
            driver.quit();
        driver = null;
        instance = null;
    }
}
