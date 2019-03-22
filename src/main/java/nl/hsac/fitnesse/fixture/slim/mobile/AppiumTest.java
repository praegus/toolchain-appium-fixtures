package nl.hsac.fitnesse.fixture.slim.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import nl.hsac.fitnesse.fixture.slim.StopTestException;
import nl.hsac.fitnesse.fixture.slim.web.BrowserTest;
import nl.hsac.fitnesse.fixture.slim.web.annotation.TimeoutPolicy;
import nl.hsac.fitnesse.fixture.slim.web.annotation.WaitUntil;
import nl.hsac.fitnesse.fixture.util.mobile.AppiumHelper;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

/**
 * Specialized class to test mobile applications using Appium.
 */
public abstract class AppiumTest<T extends MobileElement, D extends AppiumDriver<T>> extends BrowserTest<T> {

    private boolean abortOnException;

    public AppiumTest() {
        super();
        setImplicitFindInFramesTo(false);
    }

    public AppiumTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
        setImplicitFindInFramesTo(false);
    }

    public boolean launchApp() {
        driver().launchApp();
        return true;
    }

    public boolean resetApp() {
        driver().resetApp();
        return true;
    }

    public boolean closeApp() {
        driver().closeApp();
        return true;
    }

    public void abortOnException(boolean abort) {
        abortOnException = abort;
    }

    @Override
    protected Throwable handleException(Method method, Object[] arguments, Throwable t) {
        Throwable result = super.handleException(method, arguments, t);
        if(abortOnException) {
            String msg = result.getMessage();
            if (msg.startsWith("message:<<") && msg.endsWith(">>")) {
                msg = msg.substring(10, msg.length() - 2);
            }
            result = new StopTestException(false, msg);
        }
        return result;
    }

    @Override
    public boolean ensureActiveTabIsNotClosed() {
        return true;
    }

    @Override
    protected T getElementToCheckVisibility(String place) {
        T result = getMobileHelper().getElementToCheckVisibility(place);
        return result;
    }

    @Override
    public String savePageSource() {
        String fileName = "xmlView_" + System.currentTimeMillis();
        return savePageSource(fileName, fileName + ".xml");
    }

    @Override
    public boolean scrollTo(String place) {
        return getMobileHelper().scrollTo(place);
    }

    @Override
    public boolean scrollToIn(String place, String container) {
        return doInContainer(container, () -> scrollTo(place));
    }

    @Override
    protected T getElement(String place) {
        return super.getElement(place);
    }

    @Override
    protected T getContainerImpl(String container) {
        return getMobileHelper().getContainer(container);
    }

    protected D driver() {
        return getMobileHelper().driver();
    }

    protected AppiumHelper<T, D> getMobileHelper() {
        return (AppiumHelper<T, D>) super.getSeleniumHelper();
    }

    @Override
    protected boolean clear(WebElement element) {
        boolean result = false;
        if (null != element) {
            element.clear();
            result = true;
        }
        return result;
    }

    @WaitUntil(TimeoutPolicy.STOP_TEST)
    public boolean waitForToContain(String place, String text) {
        T element = this.getElement(place, null);
        return null != element && element.getText().contains(text);
    }
}
