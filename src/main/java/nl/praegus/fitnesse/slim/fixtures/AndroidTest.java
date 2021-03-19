package nl.praegus.fitnesse.slim.fixtures;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import nl.hsac.fitnesse.fixture.slim.web.annotation.WaitUntil;
import nl.praegus.fitnesse.slim.util.AndroidHelper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Specialized class to test Android applications using Appium.
 */
public class AndroidTest extends AppiumTest<AndroidElement, AndroidDriver<AndroidElement>> {
    public AndroidTest() {
        super();
        setRepeatIntervalToMilliseconds(500);
        repeatAtMostTimes(20);
    }

    public AndroidTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
        setRepeatIntervalToMilliseconds(500);
        repeatAtMostTimes(20);
    }

    @Override
    public boolean pressEnter() {
        getAppiumHelper().driver().pressKey(new KeyEvent(AndroidKey.NUMPAD_ENTER));
        return true;
    }

    @Override
    protected AndroidHelper getAppiumHelper() {
        return (AndroidHelper) super.getAppiumHelper();
    }

    public boolean resetApp() {
        getDriver().resetApp();
        return true;
    }

    @Override
    protected boolean enter(WebElement element, String value, boolean shouldClear) {
        boolean result = super.enter(element, value, shouldClear);
        if (getDriver().isKeyboardShown()) {
            getDriver().hideKeyboard();
        }
        return result;
    }



    @WaitUntil
    public boolean dragTo(String dragPlace, String targetPlace ) {
        return dragTo(dragPlace, targetPlace, new AndroidTouchAction(getDriver()));
    }

}
