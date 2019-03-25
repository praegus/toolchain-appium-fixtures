package nl.praegus.fitnesse.slim.fixtures;

import io.appium.java_client.windows.WindowsElement;
import nl.hsac.fitnesse.fixture.util.ReflectionHelper;
import nl.praegus.fitnesse.slim.util.AppiumHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    private WindowsAppTest appTest;

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

    @Test
    public void clear_find_element_first(){
        String place = "locator";
        when(appiumHelper.getElement(place)).thenReturn(element);

        boolean result = appTest.clear(place);

        assertThat(result).isTrue();
    }

    @Test
    public void when_element_is_not_found_it_is_not_cleared(){
        String place = "locator";
        when(appiumHelper.getElement(place)).thenReturn(null);

        boolean result = appTest.clear(place);

        assertThat(result).isFalse();
    }


    @Test
    public void clear_element(){
        boolean result = appTest.clear(element);

        assertThat(result).isTrue();
    }

    @Test
    public void clear_doesnt_work_because_element_is_null(){
        boolean result = appTest.clear((WebElement) null);

        assertThat(result).isFalse();
    }

    @Test
    public void find_and_click_element(){
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appTest.click(place);

        assertThat(result).isTrue();
    }

    @Test
    public void try_to_click_element_but_element_not_found(){
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(null);

        boolean result = appTest.click(place);

        assertThat(result).isFalse();
    }

    @Test
    public void try_to_click_element_but_element_not_interactable(){
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(false);

        boolean result = appTest.click(place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_element_is_visible_wait_for_visible_returns_true(){
        String place = "place";
        when(appiumHelper.findByTechnicalSelectorOr(ArgumentMatchers.eq(place), any(Supplier.class))).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        boolean result = appTest.waitForVisible(place);

        assertThat(result).isTrue();
    }

    @Test
    public void when_the_element_is_not_found_wait_for_visible_returns_false(){
        String place = "place";
        when(appiumHelper.findByTechnicalSelectorOr(ArgumentMatchers.eq(place), any(Supplier.class))).thenReturn(null);

        boolean result = appTest.waitForVisible(place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_element_is_not_visible_wait_for_visible_returns_false(){
        String place = "place";
        when(appiumHelper.findByTechnicalSelectorOr(ArgumentMatchers.eq(place), any(Supplier.class))).thenReturn(element);
        when(element.isDisplayed()).thenReturn(false);

        boolean result = appTest.waitForVisible(place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_no_select_element_is_to_be_found_false_is_returned(){
        boolean result = appTest.clickSelectOption(null, "value");

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_option_of_the_select_element_is_not_found_false_is_returned(){
        when(element.getTagName()).thenReturn("select");
        when(element.findElement(any())).thenReturn(null);

        boolean result = appTest.clickSelectOption(element, "value");

        assertThat(result).isFalse();
    }


    @Test
    public void when_the_select_element_is_found_and_value_is_selected_true_is_returned(){
        when(element.getTagName()).thenReturn("select");
        when(element.findElement(any())).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appTest.clickSelectOption(element, "value");

        assertThat(result).isTrue();
    }
}