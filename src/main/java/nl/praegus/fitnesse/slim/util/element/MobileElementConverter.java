package nl.praegus.fitnesse.slim.util.element;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.internal.JsonToMobileElementConverter;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import nl.hsac.fitnesse.fixture.util.selenium.caching.CachingRemoteWebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public class MobileElementConverter extends JsonToMobileElementConverter {

    public MobileElementConverter(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected RemoteWebElement newRemoteWebElement() {
        RemoteWebElement result;
        // standard newMobileElement will get the session details from server multiple time for each element
        // to get the correct class to instantiate.
        // Lets not do that for iOS and Android
        if (driver instanceof IOSDriver) {
            result = createIOSElement();
        } else if (driver instanceof AndroidDriver) {
            result = createAndroidElement();
        } else if (driver instanceof WindowsDriver) {
            result = createWindowsElement();
        }else {
            result = createOtherNewElement();
        }

        result.setParent(driver);
        result.setFileDetector(driver.getFileDetector());

        return result;
    }

    protected RemoteWebElement createIOSElement() {
        return new HsacIOSElement();
    }

    protected RemoteWebElement createAndroidElement() {
        return new HsacAndroidElement();
    }

    protected RemoteWebElement createWindowsElement() { return new HsacWindowsElement(); }

    protected RemoteWebElement createOtherNewElement() {
        RemoteWebElement newMobileElement = super.newRemoteWebElement();
        if (!(newMobileElement instanceof MobileElement)) {
            newMobileElement = handleNonMobileElement(newMobileElement);
        }
        return newMobileElement;
    }

    protected RemoteWebElement handleNonMobileElement(RemoteWebElement element) {
        return element instanceof CachingRemoteWebElement ? element : new CachingRemoteWebElement(element);
    }
}
