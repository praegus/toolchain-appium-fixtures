package nl.praegus.fitnesse.slim.fixtures;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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
    }

    public AndroidTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
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

    @Override
    public void scrollIntoView(String text) {
        getDriver().findElement(
                MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().textContains(\""+text+"\").instance(0))"));
    }

    public void swipeScreen(String direction) {
        System.out.println("swipeScreen(): dir: '" + direction + "'"); // always log your actions

        final int ANIMATION_TIME = 200; // ms

        final int PRESS_TIME = 200; // ms

        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Dimension dims = getDriver().manage().window().getSize();

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        Direction dir = Direction.valueOf(direction);
        switch (dir) {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            new TouchAction(getDriver())
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
}
