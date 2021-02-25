package ru.appline.ozone.baseTestClasses;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.appline.ozoneFramework.managers.*;
import ru.appline.ozoneFramework.uils.MyListener;

@ExtendWith(MyListener.class)
public class BaseTestClass {
        protected PageMngr pages = PageMngr.getPageMngr();

        @BeforeEach
        void beforeEach() {
            InitMngr.initAll();
            DriverMngr.getDriver().get(PropMngr.GetPropMngr().getProperty("app.url"));
        }

        @AfterEach
        void after() {
            InitMngr.closeAll();
        }
}
