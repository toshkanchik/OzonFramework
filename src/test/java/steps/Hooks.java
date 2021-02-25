package steps;

import io.cucumber.java.*;
import io.cucumber.plugin.event.Result;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.appline.ozoneFramework.managers.PageMngr;
import ru.appline.ozoneFramework.managers.PropMngr;

import static ru.appline.ozoneFramework.managers.DriverMngr.getDriver;
import static ru.appline.ozoneFramework.managers.InitMngr.initAll;
import static ru.appline.ozoneFramework.managers.InitMngr.closeAll;
import static ru.appline.ozoneFramework.managers.PageMngr.getPageMngr;

public class Hooks {
    protected PageMngr pages = getPageMngr();
    @Before
    public void beforeAll()
    {
        initAll();
        getDriver().get(PropMngr.GetPropMngr().getProperty("app.url"));
    }

    @After
    public void afterAll(Scenario scenario)
    {
        closeAll();
        if(scenario.isFailed()){
            Allure.getLifecycle().addAttachment("Screenshot", "image/png", ".png", addScreenshot());
        }
    }
    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    public static byte[] addScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
