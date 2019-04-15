package nl.praegus.fitnesse.slim.fixtures;

import io.appium.java_client.windows.WindowsElement;
import nl.hsac.fitnesse.fixture.util.ReflectionHelper;
import nl.praegus.fitnesse.slim.util.WindowsHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppiumTestTest {

    @Mock
    private WindowsHelper appiumHelper;
    @Mock
    private ReflectionHelper reflectionHelper;
    @Mock
    private WindowsElement element;

    @InjectMocks
    private WindowsAppTest appiumTest;

    @Test
    public void when_element_can_be_found_and_is_interactable_and_can_be_clicked_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.getActiveElement()).thenReturn(element);

        boolean result = appiumTest.enterAs("inputtekst", place);

        assertThat(result).isTrue();
    }

    @Test
    public void when_element_cannot_be_found_no_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(null);

        boolean result = appiumTest.enterAs("inputtekst", place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_element_can_be_found_but_is_not_interactable_no_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(false);

        boolean result = appiumTest.enterAs("inputtekst", place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_element_no_element_is_active_no_text_is_sent() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.getActiveElement()).thenReturn(null);

        boolean result = appiumTest.enterAs("inputtekst", place);

        assertThat(result).isFalse();
    }

    @Test
    public void clear_find_element_first() {
        String place = "locator";
        when(appiumHelper.getElement(place)).thenReturn(element);

        boolean result = appiumTest.clear(place);

        assertThat(result).isTrue();
    }

    @Test
    public void when_element_is_not_found_it_is_not_cleared() {
        String place = "locator";
        when(appiumHelper.getElement(place)).thenReturn(null);

        boolean result = appiumTest.clear(place);

        assertThat(result).isFalse();
    }

    @Test
    public void clear_element() {
        boolean result = appiumTest.clear(element);

        assertThat(result).isTrue();
    }

    @Test
    public void clear_doesnt_work_because_element_is_null() {
        boolean result = appiumTest.clear((WebElement) null);

        assertThat(result).isFalse();
    }

    @Test
    public void find_and_click_element() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.click(place);

        assertThat(result).isTrue();
    }

    @Test
    public void find_and_click_element_when_available() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.clickIfAvailable(place);

        assertThat(result).isTrue();
    }

    @Test
    public void click_element_then_throw_webdriver_exception_with_message() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenThrow(new WebDriverException("Exeption"));

        assertThatThrownBy(() -> appiumTest.clickIfAvailable(place))
                .isInstanceOf(WebDriverException.class)
                .hasMessageStartingWith("Exeption");
    }

    @Test
    public void find_and_click_element_in_container() {
        String container = "container";
        String place = "locator";
        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.doInContext(any(), any())).thenReturn(element);

        boolean result = appiumTest.clickIn(place, container);

        assertThat(result).isTrue();
        verify(element, times(1)).click();
    }

    @Test
    public void find_and_double_click_with_string() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.doubleClick(place);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).doubleClick(element);
    }

    @Test
    public void find_and_double_click_in_container() {
        String container = "container";
        String place = "locator";
        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.doInContext(any(), any())).thenReturn(element);

        boolean result = appiumTest.doubleClickIn(place, container);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).doubleClick(element);
    }

    @Test
    public void try_to_click_element_but_element_not_found() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(null);

        boolean result = appiumTest.click(place);

        assertThat(result).isFalse();
    }

    @Test
    public void try_to_click_element_but_element_not_interactable() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(false);

        boolean result = appiumTest.click(place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_element_is_visible_wait_for_visible_returns_true() {
        String place = "place";
        when(appiumHelper.findByTechnicalSelectorOr(eq(place), any(Supplier.class))).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        boolean result = appiumTest.waitForVisible(place);

        assertThat(result).isTrue();
    }

    @Test
    public void when_the_element_is_not_found_wait_for_visible_returns_false() {
        String place = "place";
        when(appiumHelper.findByTechnicalSelectorOr(eq(place), any(Supplier.class))).thenReturn(null);

        boolean result = appiumTest.waitForVisible(place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_element_is_not_visible_wait_for_visible_returns_false() {
        String place = "place";
        when(appiumHelper.findByTechnicalSelectorOr(eq(place), any(Supplier.class))).thenReturn(element);
        when(element.isDisplayed()).thenReturn(false);

        boolean result = appiumTest.waitForVisible(place);

        assertThat(result).isFalse();
    }

    @Test
    public void when_no_select_element_is_to_be_found_false_is_returned() {
        boolean result = appiumTest.clickSelectOption(null, "value");

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_option_of_the_select_element_is_not_found_false_is_returned() {
        when(element.getTagName()).thenReturn("select");
        when(element.findElement(any())).thenReturn(null);

        boolean result = appiumTest.clickSelectOption(element, "value");

        assertThat(result).isFalse();
    }

    @Test
    public void when_the_select_element_is_found_and_value_is_selected_true_is_returned() {
        when(element.getTagName()).thenReturn("select");
        when(element.findElement(any())).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.clickSelectOption(element, "value");

        assertThat(result).isTrue();
    }

    @Test
    public void find_and_right_click_element_then_true_is_returned() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.rightClick(place);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).rightClick(element);
    }

    @Test
    public void find_and_right_click_element_in_container_then_true_is_returned() {
        String container = "container";
        String place = "locator";
        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.doInContext(any(), any())).thenReturn(element);

        boolean result = appiumTest.rightClickIn(place, container);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).rightClick(element);
    }

    @Test
    public void find_and_shift_click_element_then_true_is_returned() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.shiftClick(place);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).clickWithKeyDown(element, Keys.SHIFT);
    }

    @Test
    public void find_and_shift_click_element_in_container_then_true_is_returned() {
        String container = "container";
        String place = "locator";
        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.doInContext(any(), any())).thenReturn(element);

        boolean result = appiumTest.shiftClickIn(place, container);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).clickWithKeyDown(element, Keys.SHIFT);
    }

    @Test
    public void find_and_control_click_element_then_true_is_returned() {
        String place = "locator";
        when(appiumHelper.getElementToClick(place)).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);

        boolean result = appiumTest.controlClick(place);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).clickWithKeyDown(element, Keys.CONTROL);
    }

    @Test
    public void find_and_control_click_element_in_container_then_true_is_returned() {
        String container = "container";
        String place = "locator";
        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.isInteractable(element)).thenReturn(true);
        when(appiumHelper.doInContext(any(), any())).thenReturn(element);

        boolean result = appiumTest.controlClickIn(place, container);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).clickWithKeyDown(element, Keys.CONTROL);
    }

    @Test
    public void enter_value_in_hidden_field_then_true_is_returned() {
        String idOrName = "idorname";
        String value = "value";
        when(appiumHelper.setHiddenInputValue(any(), any())).thenReturn(true);

        boolean result = appiumTest.enterForHidden(idOrName, value);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).setHiddenInputValue(any(), any());
    }

    @Test
    public void select_option_for_element_then_true_is_returned() {
        WindowsElement option = mock(WindowsElement.class);

        String place = "place";
        when(appiumHelper.getElement(place)).thenReturn(element);
        when(element.getTagName()).thenReturn("select");
        when(element.findElement(any())).thenReturn(option);
        when(appiumHelper.isInteractable(option)).thenReturn(true);

        boolean result = appiumTest.selectFor("value", place);

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).getElement(place);
        verify(option, times(1)).click();
    }

    @Test
    public void select_option_for_element_in_container_then_true_is_returned() {
        String container = "container";
        String place = "place";

        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.doInContext(any(), any())).thenReturn(true);

        boolean result = appiumTest.selectForIn("value", place, container);

        assertThat(result).isTrue();
        //TODO: verify implementeren zonder any()
        verify(appiumHelper, times(1)).doInContext(any(), any());
    }

    @Test
    public void select_option_as_element_then_true_is_returned() {
        WindowsElement option = mock(WindowsElement.class);

        String place = "place";
        when(appiumHelper.getElement(place)).thenReturn(element);
        when(element.getTagName()).thenReturn("select");
        when(element.findElement(any())).thenReturn(option);
        when(appiumHelper.isInteractable(option)).thenReturn(true);
        when(element.getAttribute("multiple")).thenReturn("multiple");

        boolean result = appiumTest.selectAs("value", place);

        assertThat(result).isTrue();
        verify(element, times(1)).findElements(By.tagName("option"));
    }

    @Test
    public void select_option_as_element_in_container_then_true_is_returned() {
        String container = "container";
        String place = "place";

        when(appiumHelper.findByTechnicalSelectorOr(eq(container), any(Supplier.class))).thenReturn(element);
        when(appiumHelper.doInContext(any(), any())).thenReturn(true);

        boolean result = appiumTest.selectAsIn("value", place, container);

        assertThat(result).isTrue();
        //TODO: verify implementeren zonder any()
        verify(appiumHelper, times(1)).doInContext(any(), any());
    }

    @Test
    public void send_value_to_web_element_then_true_is_returned() {
        appiumTest.sendValue(element, "value");

        verify(element, times(1)).sendKeys("value");
    }

    @Test
    public void press_single_key_then_true_is_returned() {
        when(appiumHelper.getActiveElement()).thenReturn(element);

        boolean result = appiumTest.press("control");

        assertThat(result).isTrue();
        ArgumentCaptor<CharSequence> captor = ArgumentCaptor.forClass(CharSequence.class);
        verify(element, times(1)).sendKeys(captor.capture());
        assertThat(String.valueOf(captor.getValue())).isEqualTo("\ue009");
    }

    @Test
    public void press_double_key_then_true_is_returned() {
        when(appiumHelper.getActiveElement()).thenReturn(element);

        boolean result = appiumTest.press("control + v");

        assertThat(result).isTrue();
        ArgumentCaptor<CharSequence> captor = ArgumentCaptor.forClass(CharSequence.class);
        verify(element, times(1)).sendKeys(captor.capture());
    }

    @Test
    public void press_command_then_true_is_returned() {
        when(appiumHelper.getActiveElement()).thenReturn(element);

        appiumTest.setSendCommandForControlOnMacTo(true);
        boolean result = appiumTest.press("control");

        assertThat(result).isTrue();
        verify(appiumHelper, times(1)).getControlOrCommand();
    }

}