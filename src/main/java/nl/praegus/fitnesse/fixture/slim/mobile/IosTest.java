package nl.praegus.fitnesse.fixture.slim.mobile;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import nl.praegus.fitnesse.fixture.util.mobile.IosHelper;

/**
 * Specialized class to test iOS applications using Appium.
 */
public class IosTest extends AppiumTest<IOSElement, IOSDriver<IOSElement>> {
    public IosTest() {
        super();
    }

    public IosTest(int secondsBeforeTimeout) {
        super(secondsBeforeTimeout);
    }

    @Override
    protected IosHelper getAppiumHelper() {
        return (IosHelper) super.getAppiumHelper();
    }
}
