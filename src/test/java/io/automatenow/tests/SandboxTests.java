package io.automatenow.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Marco A. Cruz
 */
public class SandboxTests extends BaseTest {

    @BeforeClass
    public void testNavigateToSandboxPage() {
        navBar.selectSandbox();
    }

    @Test(description = "Verify the page title")
    public void testPageTitle() {
        String title = sandboxPage.getPageTitle();
        assertEquals(title, "Sandbox – AUTOMATENOW", "Page title did not match");
    }

    @Test(description = "Enters text in an input field")
    public void testEnterText() {
        String myText = "hello";
        sandboxPage.setInputFieldText(myText);
        String displayedText = sandboxPage.getInputFieldText();
        assertEquals(displayedText, myText, "Unable to verify entered text");
    }

    @Test(description = "Checks a checkbox")
    public void testCheckbox() {
        sandboxPage.selectCheckbox("1");
        assertTrue(sandboxPage.checkboxIsSelected("1"), "Checkbox is not selected");
        assertFalse(sandboxPage.checkboxIsSelected("2"), "Checkbox is selected");
    }

    @Test(description = "Selects from a drop-down")
    public void testSelectFromDropdown() {
        String myOption = "Binary";
        sandboxPage.selectFromDropdown(myOption);
        assertEquals(sandboxPage.getDropdownText(), myOption, "Dropdown option not selected");
    }

    @Test(description = "Selects radio buttons")
    public void testSelectRadioButton() {
        String radio = "White";
        String radio2 = "Blue";

        sandboxPage.selectRadioButton(radio);
        assertTrue(sandboxPage.radioButtonIsSelected(radio), "White option was not selected");

        sandboxPage.selectRadioButton(radio2);
        assertTrue(sandboxPage.radioButtonIsSelected(radio2), "Blue option was not selected");
        assertFalse(sandboxPage.radioButtonIsSelected(radio), "White option was selected");
    }

    @Test(description = "Finds the price of an item in a table")
    public void testItemPrice() {
        String itemPrice = sandboxPage.getItemPrice("Laptop");
        assertEquals(itemPrice, "$1200.00", "Laptop price was incorrect");

        itemPrice = sandboxPage.getItemPrice("Oranges");
        assertEquals(itemPrice, "$3.99", "Oranges price was incorrect");
    }

    @Test(description = "Selects a date from a date picker")
    public void testSelectFromDatePicker() {
        sandboxPage.setDate("July", "4", "2021");
        String date = sandboxPage.getDate();
        assertEquals(date, "July 4, 2021", "The date was not properly set");
    }

    @Test(description = "Searches for a blog article")
    public void testBlogSearch() {
        boolean search;
        search = sandboxPage.search("aaaa");
        assertFalse(search, "Did not expect to find a search result");
        search = sandboxPage.search("jmeter");
        assertTrue(search, "Expected to find a search result");
    }

    @Test(description = "Working with multiple open windows")
    public void testMultipleOpenWindows() {
        sandboxPage.clickTwitterButton();
        sandboxPage.switchToNewWindow();
        String pageTitle = sandboxPage.getPageTitle();
        assertTrue(pageTitle.contains("Twitter"), "The new window's tile does not match");
    }

    @Test(description = "Closes a second open window")
    public void testCloseSecondWindow() {
        sandboxPage.clickTwitterButton();
        sandboxPage.switchToNewWindow();
        closeWindow();
        int numberOfOpenWindows = getNumberOfOpenWindows();
        assertEquals(numberOfOpenWindows, 1, "Found more than one open window");
    }

    @Test(description = "Working with multiple tabs")
    public void testMultipleTabs() {
        openNewTab();
        sandboxPage.switchToNewWindow();
        goToUrl("https://www.nasa.gov");
        String pageTitle = getPageTitle();
        assertEquals(pageTitle, "NASA", "The page title for the new window did not match");
        closeWindow();
        int numberOfOpenWindows = getNumberOfOpenWindows();
        assertEquals(numberOfOpenWindows, 1, "Found more than one open window");
    }

    @Test(description = "Test click and drag operation on a map")
    public void testClickAndDrag() {
        int x_coordinate = -300;
        int y_coordinate = 100;

        sandboxPage.dragMap(x_coordinate, y_coordinate);
    }

    @Test(description = "Tests a JavaScript alert and a confirmation box")
    public void testPopups() {
        sandboxPage.clickAlertPopup();
        dismissPopup();

        sandboxPage.clickConfirmPopup();
        acceptPopup();
        String selectionResult = sandboxPage.getConfirmPopupSelection();
        assertEquals(selectionResult, "OK it is!", "The popup selection result does not match");
    }

    @Test(description = "Tests a JavaScript prompt box")
    public void testPromptPopup() {
        String name = "Marco";
        sandboxPage.clickPromptPopup();
        setAlertText(name);
        acceptPopup();
        sandboxPage.waitForPromptPopupResult(String.format("Nice to meet you %s!", name));
    }

    @Test(description = "Tests a JavaScript modal")
    public void testModal() {
        String name = "Marco";
        String email = "info@automatenow.io";
        String message = "Test Message";

        sandboxPage.openModal()
                .modalSendMessage(name, email, message);
    }

    @Test(description = "Test mouse over")
    public void testHovering() {
        sandboxPage.doHover();
        String hoverText = sandboxPage.getHoverText();
        assertEquals(hoverText, "You did it!", "Hover text did not match expected value");
    }

    @Test(description = "Tests scrolling an element into view")
    public void testScrollElementIntoView() {
        sandboxPage.scrollMapIntoView();
    }

    @Test(description = "Tests scrolling a webpage")
    public void testScrollPage() {
        // Scroll down
        scrollPage(0, 500);
        // Scroll up
        scrollPage(0,-500);
    }
}
