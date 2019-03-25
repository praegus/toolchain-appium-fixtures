package nl.praegus.fitnesse.slim.fixtures;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import nl.hsac.fitnesse.fixture.slim.SlimFixtureException;
import nl.hsac.fitnesse.fixture.slim.web.annotation.TimeoutPolicy;
import nl.hsac.fitnesse.fixture.slim.web.annotation.WaitUntil;
import nl.hsac.fitnesse.fixture.util.ReflectionHelper;
import nl.praegus.fitnesse.slim.util.AppiumHelper;
import nl.praegus.fitnesse.slim.util.WindowsHelper;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class WindowsAppTest extends AppiumTest<WindowsElement, WindowsDriver<WindowsElement>> {
    private int delayAfterClickInMillis = 100;
    private String focusedWindow = "";

    public WindowsAppTest() {
        super();
        focusedWindow = windowHandles().get(0);
    }

    public WindowsAppTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
    }

    public WindowsAppTest(AppiumHelper appiumHelper, ReflectionHelper reflectionHelper) {
        super(appiumHelper, reflectionHelper);
    }

    public void setMillisecondsDelayAfterClick(int millis) {
        delayAfterClickInMillis = millis;
    }

    @Override
    protected WindowsHelper getAppiumHelper() {
        return (WindowsHelper) super.getAppiumHelper();
    }

    public List<String> windowHandles() {
        return new ArrayList<>(getAppiumHelper().driver().getWindowHandles());
    }


    @Override
    protected boolean clickImp(String place, String container) {
        boolean result = super.clickImp(place, container);
        if (result) {
            waitMilliseconds(delayAfterClickInMillis);
        }
        return result;
    }

    @Override
    protected boolean doubleClick(WebElement element) {
        boolean result = super.doubleClick(element);
        if (result) {
            waitMilliseconds(delayAfterClickInMillis);
        }
        return result;
    }

    @WaitUntil(TimeoutPolicy.STOP_TEST)
    public boolean waitForSplashScreenToDisappear() {
        if (!windowHandles().get(0).equals(focusedWindow)) {
            switchToTopWindow();
            return true;
        }
        return false;
    }

    public void switchToTopWindow() {
        String appWindow = windowHandles().get(0);
        getDriver().switchTo().window(appWindow);
        focusedWindow = appWindow;
    }

    public void switchToNextWindow() {
        List<String> windows = windowHandles();
        if (windows.size() > 1) {
            int currentIndex = windowHandles().indexOf(focusedWindow);
            int newIndex = currentIndex == (windows.size() - 1) ? 0 : currentIndex + 1;
            getDriver().switchTo().window(windows.get(newIndex));
            focusedWindow = windows.get(newIndex);
        } else {
            throw new SlimFixtureException("There is only one window in WinAppDriver's scope. Cannot Switch to next window");
        }
    }

    @Override
    @WaitUntil
    public boolean enterAs(String value, String place) {
        if (click(place)) {
            return type(value);
        }
        return false;
    }

}
