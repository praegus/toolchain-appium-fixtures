package nl.hsac.fitnesse.fixture.slim.mobile;


import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import nl.hsac.fitnesse.fixture.util.mobile.WindowsHelper;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class WindowsAppTest extends MobileTest<WindowsElement, WindowsDriver<WindowsElement>> {
    private int delayAfterClickInMillis = 300;

    public WindowsAppTest() {
        super();
    }

    public WindowsAppTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
    }

    public void setMillisecondsDelayAfterClick(int millis) {
        delayAfterClickInMillis = millis;
    }

    @Override
    protected WindowsHelper getMobileHelper() {
        return (WindowsHelper) super.getMobileHelper();
    }

    public List<String> windowHandles() {
        return new ArrayList<>(getSeleniumHelper().driver().getWindowHandles());
    }


    @Override
    protected boolean clickImp(String place, String container) {
        boolean result = super.clickImp(place, container);
        if(result) {
            waitMilliseconds(delayAfterClickInMillis);
        }
        return result;
    }

    @Override
    protected boolean doubleClick(WebElement element) {
        boolean result = super.doubleClick(element);
        if(result) {
            waitMilliseconds(delayAfterClickInMillis);
        }
        return result;
    }
}
