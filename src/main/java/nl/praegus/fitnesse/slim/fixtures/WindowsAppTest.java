package nl.praegus.fitnesse.slim.fixtures;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import nl.hsac.fitnesse.fixture.slim.SlimFixtureException;
import nl.hsac.fitnesse.fixture.slim.web.annotation.TimeoutPolicy;
import nl.hsac.fitnesse.fixture.slim.web.annotation.WaitUntil;
import nl.hsac.fitnesse.slim.interaction.ReflectionHelper;
import nl.praegus.fitnesse.slim.util.WindowsHelper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static nl.praegus.fitnesse.slim.util.KeyMapping.getKey;

@SuppressWarnings("WeakerAccess")
public class WindowsAppTest extends AppiumTest<WindowsElement, WindowsDriver<WindowsElement>> {

    private final static Set<String> METHODS_NO_WAIT;

    @Override
    public void scrollIntoView(String text) {
        throw new NotImplementedException();
    }

    static {
        METHODS_NO_WAIT = ReflectionHelper.validateMethodNames(WindowsAppTest.class,
                "getFocusedWindow",
                "takeScreenshot",
                "secondsBeforeTimeout",
                "waitForVisible",
                "waitSeconds",
                "waitMilliseconds",
                "waitMilliSecondAfterScroll",
                "screenshotBaseDirectory",
                "screenshotShowHeight",
                "setGlobalValueTo",
                "setSendCommandForControlOnMacTo",
                "sendCommandForControlOnMac",
                "globalValue",
                "clearSearchContext",
                "secondsBeforeTimeout",
                "secondsBeforePageLoadTimeout",
                "trimOnNormalize",
                "setImplicitFindInFramesTo",
                "setTrimOnNormalize",
                "setRepeatIntervalToMilliseconds",
                "repeatAtMostTimes",
                "repeatAtMostTimes",
                "timeSpentRepeating");
    }

    private int delayAfterClickInMillis = 100;
    private String focusedWindow = "";
    private Robot robot;
    private Clipboard clipboard;
    private int maxRetriesToFindElement = 5;

    public WindowsAppTest() {
        super();
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        focusedWindow = windowHandles().get(0);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new SlimFixtureException("the platform configuration does not allow low-level input control");
        }
    }

    public WindowsAppTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
    }

    public WindowsAppTest(WindowsHelper windowsHelper, nl.hsac.fitnesse.fixture.util.ReflectionHelper reflectionHelper, Robot robot, Clipboard clipboard) {
        super(windowsHelper, reflectionHelper);
        this.robot = robot;
        this.clipboard = clipboard;
    }

    /**
     * Override beforeInvoke to delay execution
     */
    @Override
    protected void beforeInvoke(Method method, Object[] arguments) {
        super.beforeInvoke(method, arguments);
        if (!METHODS_NO_WAIT.contains(method.getName())) {
            waitMilliseconds(delayAfterClickInMillis);
        }
    }

    public String getFocusedWindow() {
        return focusedWindow;
    }

    public int maxRetriesToFindElement() {
        return maxRetriesToFindElement;
    }

    public void maxRetriesToFindElement(int maxRetriesToFindElement) {
        this.maxRetriesToFindElement = maxRetriesToFindElement;
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
    protected boolean click(String place, String container) {
        int retryCount = 0;
        try {
            WebElement element = getElementToClick(cleanupValue(place), container);
            return clickElement(element);
        } catch (Exception e) {
            if (e.getMessage().contains("element//displayed") && retryCount < maxRetriesToFindElement) {
                System.out.println("Retry click on " + place + " in " + container);
                retryCount++;
                return click(place, container);
            } else {
                throw e;
            }
        }
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

    public boolean pasteText(String text) {
        StringSelection stringSelection = new StringSelection(text);
        clipboard.setContents(stringSelection, stringSelection);

        return pressAnd("control", "v");
    }

    public boolean pressAnd(String key1, String key2) {
        robot.keyPress(getKey(key1));
        robot.keyPress(getKey(key2));
        robot.keyRelease(getKey(key1));
        robot.keyRelease(getKey(key2));
        return true;
    }

    public boolean pressKey(String key) {
        robot.keyPress(getKey(key));
        robot.keyRelease(getKey(key));
        return true;
    }

    public int currentWindowWidth() {
        return getWindowSize().getWidth();
    }

    public int currentWindowHeight() {
        return getWindowSize().getHeight();
    }

    public void setWindowWidth(int newWidth) {
        int currentHeight = currentWindowHeight();
        setWindowSizeToBy(newWidth, currentHeight);
    }

    public void setWindowHeight(int newHeight) {
        int currentWidth = currentWindowWidth();
        setWindowSizeToBy(currentWidth, newHeight);
    }

    public void setWindowSizeToBy(int newWidth, int newHeight) {
        appiumHelper.setWindowSize(newWidth, newHeight);
        org.openqa.selenium.Dimension actualSize = getWindowSize();
        if (actualSize.getHeight() != newHeight || actualSize.getWidth() != newWidth) {
            String message = String.format("Unable to change size to: %s x %s; size is: %s x %s",
                    newWidth, newHeight, actualSize.getWidth(), actualSize.getHeight());
            throw new SlimFixtureException(false, message);
        }
    }

    protected Dimension getWindowSize() {
        return appiumHelper.getWindowSize();
    }

    public void setWindowSizeToMaximum() {
        appiumHelper.setWindowSizeToMaximum();
    }


    /**
     * Scrolls window so top of element becomes visible.
     *
     * @param element element to scroll to.
     */
    @Override
    protected void scrollTo(WebElement element) {
        getAppiumHelper().scrollTo(element, false);
        waitAfterScroll(waitAfterScroll);
    }


    @WaitUntil
    public boolean rightClick(String place) {
        return rightClickIn(place, null);
    }

    @WaitUntil
    public boolean rightClickIn(String place, String container) {
        WebElement element = getElementToClick(cleanupValue(place), container);
        return rightClick(element);
    }

    protected boolean rightClick(WebElement element) {
        return doIfInteractable(element, () -> appiumHelper.rightClick(element));
    }

    @WaitUntil
    public boolean shiftClick(String place) {
        return shiftClickIn(place, null);
    }

    @WaitUntil
    public boolean shiftClickIn(String place, String container) {
        WebElement element = getElementToClick(cleanupValue(place), container);
        return shiftClick(element);
    }

    protected boolean shiftClick(WebElement element) {
        return doIfInteractable(element, () -> appiumHelper.clickWithKeyDown(element, Keys.SHIFT));
    }

    @WaitUntil
    public boolean controlClick(String place) {
        return controlClickIn(place, null);
    }

    @WaitUntil
    public boolean controlClickIn(String place, String container) {
        WebElement element = getElementToClick(cleanupValue(place), container);
        return controlClick(element);
    }

    protected boolean controlClick(WebElement element) {
        return doIfInteractable(element, () -> appiumHelper.clickWithKeyDown(element, controlKey()));
    }

    @Override
    protected boolean doIfInteractable(WebElement element, Runnable action) {
        boolean result = false;
        if (element != null && getAppiumHelper().isInteractable(element)) {
                action.run();
                result = true;
            }
        return result;
    }
}
