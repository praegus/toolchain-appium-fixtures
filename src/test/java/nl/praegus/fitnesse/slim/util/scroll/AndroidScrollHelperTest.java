package nl.praegus.fitnesse.slim.util.scroll;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import nl.praegus.fitnesse.slim.util.AndroidHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Dimension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AndroidScrollHelperTest {

    @Mock
    private AndroidHelper helper;

    @InjectMocks
    private AndroidScrollHelper scrollHelper;

    @Mock
    private AndroidDriver driver;

    @Mock
    private AndroidElement element;

    @Test
    public void when_element_is_already_visible_true_is_returned() {
        String place = "place";
        when(helper.getElementToCheckVisibility(place)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        boolean result = scrollHelper.scrollTo(0.5, place, helper::getElementToCheckVisibility);

        assertThat(result).isTrue();
        verify(element, times(1)).isDisplayed();
        verify(helper, times(1)).getElementToCheckVisibility(place);
        verify(driver, times(0)).performTouchAction(any(TouchAction.class));
    }

    @Test
    public void when_element_becomes_visible_true_is_eventually_returned() {
        String place = "place";
        when(helper.getWindowSize()).thenReturn(new Dimension(100, 100));
        when(helper.getTouchAction()).thenReturn(new TouchAction(driver));
        when(helper.getElementToCheckVisibility(place)).thenReturn(null).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        boolean result = scrollHelper.scrollTo(0.5, place, helper::getElementToCheckVisibility);

        assertThat(result).isTrue();
        verify(helper, times(1)).getTouchAction();
        verify(element, times(1)).isDisplayed();
        verify(helper, times(2)).getElementToCheckVisibility(place);
        verify(driver, times(1)).performTouchAction(any(TouchAction.class));
    }

    @Test
    public void when_element_remains_invisible_false_is_returned() {
        String place = "place";
        when(helper.getWindowSize()).thenReturn(new Dimension(100, 100));
        when(helper.getTouchAction()).thenReturn(new TouchAction(driver));
        when(helper.getElementToCheckVisibility(place)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(false);

        boolean result = scrollHelper.scrollTo(0.5, place, helper::getElementToCheckVisibility);

        assertThat(result).isFalse();
        verify(helper, times(2)).getTouchAction();
        verify(element, times(3)).isDisplayed();
        verify(helper, times(3)).getElementToCheckVisibility(place);
        verify(driver, times(2)).performTouchAction(any(TouchAction.class));
    }

    @Test
    public void when_element_is_does_not_exist_false_is_returned() {
        String place = "place";
        when(helper.getWindowSize()).thenReturn(new Dimension(100, 100));
        when(helper.getTouchAction()).thenReturn(new TouchAction(driver));
        when(helper.getElementToCheckVisibility(place)).thenReturn(null);

        boolean result = scrollHelper.scrollTo(0.5, place, helper::getElementToCheckVisibility);

        assertThat(result).isFalse();
        verify(helper, times(2)).getTouchAction();
        verify(helper, times(3)).getElementToCheckVisibility(place);
        verify(driver, times(2)).performTouchAction(any(TouchAction.class));
    }
}