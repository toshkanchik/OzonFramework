package ru.appline.ozoneFramework.uils;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static ru.appline.ozoneFramework.managers.DriverMngr.getDriver;

public class MyListener implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if(context.getExecutionException().isPresent()){
            addScreenshot();
        }
    }

    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    public byte[] addScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
