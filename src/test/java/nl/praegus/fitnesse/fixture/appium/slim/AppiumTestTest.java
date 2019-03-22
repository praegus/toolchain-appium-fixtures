package nl.praegus.fitnesse.fixture.appium.slim;

import io.appium.java_client.windows.WindowsElement;
import nl.hsac.fitnesse.fixture.util.ReflectionHelper;
import nl.praegus.fitnesse.fixture.appium.util.AppiumHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppiumTestTest {

    @Mock
    private AppiumHelper appiumHelper;
    @Mock
    private ReflectionHelper reflectionHelper;
    @Mock
    private WindowsElement element;

    @InjectMocks
    WindowsAppTest appTest;

    @Test
    public void when_element_can_be_found_and_is_interactable_and_can_be_clicked_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.getActiveElement()).thenReturn(element);

        boolean result = appTest.enterAs("inputtekst", place);

        assertThat(result).isTrue();
    }

    @Test
    public void when_element_cannot_be_found_no_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(null);

        boolean result = appTest.enterAs("inputtekst", place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_element_can_be_found_but_is_not_interactable_no_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(false);

        boolean result = appTest.enterAs("inputtekst", place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_element_no_element_is_active_no_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.getActiveElement()).thenReturn(null);

        boolean result = appTest.enterAs("inputtekst", place);

        assertThat(result).isFalse();
    }


}