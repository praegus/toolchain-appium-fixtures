package nl.praegus.fitnesse.slim.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AndroidHelperTest {

    @Mock
    private AppiumDriver webDriver;

    private AndroidHelper androidHelper = new AndroidHelper();

    @Before
    public void setMocks() {
        androidHelper.setWebDriver(webDriver, 10);
    }

    @Test
    public void when_element_is_checked_for_visibility_several_attempts_are_made_to_find_the_element() {
        androidHelper.getElementToCheckVisibility("banaan");

        verify(webDriver, times(1)).findElements(eq(By.xpath(".//*[@enabled='true' and (contains(@text,'banaan') or contains(@name,'banaan') or contains(@content-desc,'banaan') or contains(@resource-id,'banaan'))]")));
    }
}