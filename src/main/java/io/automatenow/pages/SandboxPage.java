package io.automatenow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Marco A. Cruz
 */
public class SandboxPage extends BasePage {
    private By inputField = By.id("g399-inputfield");
    private By dropDown = By.id("dd");
    private By calendarYear = By.xpath("//span[@class='ui-datepicker-year']");
    private By calendarMonth = By.xpath("//span[@class='ui-datepicker-month']");
    private By calendarField = By.id("g399-date");
    private By calendarRightArrow = By.xpath("//a[@title='Next']");
    private By searchBox = By.id("wp-block-search__input-1");
    private By searchBtn = By.xpath("//button[text()='Search']");
    private By noSearchResults = By.xpath("//h1[text()='Nothing Found']");
    private By twitterBtn = By.xpath("//span[text()='Twitter']");
    private By map = By.tagName("canvas");
    private By alertBtn = By.id("alert");
    private By confirmBtn = By.id("confirm");
    private By confirmPopupResult = By.id("confirmResult");
    private By promptBtn = By.id("prompt");
    private By promptPopupResult = By.id("promptResult");
    private By modalBtn = By.id("myBtn");
    private By modalName = By.id("g399-name");
    private By modalEmail = By.id("g399-email");
    private By modalMsg = By.id("contact-form-comment-g399-message");
    private By modalSubmitBtn = By.cssSelector("div[class='pum-content popmake-content'] button[type='submit']");
    private By hover = By.id("mouse_over");

    public String getPageTitle() {
        return driver.getTitle();
    }

    public SandboxPage setInputFieldText(String text) {
        setText(inputField, text);
        return null;
    }

    public String getInputFieldText() {
        return getText(inputField);
    }

    /**
     * Selects a check box
     *
     * @param option Range is 1 to 3
     */
    public SandboxPage selectCheckbox(String option) {
        click(By.xpath("//input[@value='Option " + option + "']"));
        return this;
    }

    public boolean checkboxIsSelected(String option) {
        return driver.findElement(By.xpath("//input[@value='Option " + option + "']")).isSelected();
    }

    /**
     * Selects drop-down option
     *
     * @param option Displayed text
     */
    public SandboxPage selectFromDropdown(String option) {
        Select dDown = new Select(driver.findElement(dropDown));
        dDown.selectByVisibleText(option);
        return this;
    }

    /**
     * @return The option that is currently selected.
     */
    public String getDropdownText() {
        Select dDown = new Select(driver.findElement(dropDown));
        return dDown.getFirstSelectedOption().getText();
    }

    /**
     * Selects a radio button
     *
     * @param option Case sensitive value
     */
    public SandboxPage selectRadioButton(String option) {
        click(By.cssSelector("input[value='" + option + "']"));
        return this;
    }

    public boolean radioButtonIsSelected(String option) {
        return driver.findElement(By.cssSelector("input[value='" + option + "']")).isSelected();
    }

    public String getItemPrice(String item) {
        return driver.findElement(By.xpath("//td[text()='" + item + "']/following-sibling::td")).getText();
    }

    public SandboxPage setDate(String month, String day, String year) {
        click(calendarField);

        while (true) {
            String currentMonth = getText(calendarMonth);
            String currentYear = getText(calendarYear);
            if (currentMonth.equals(month) && currentYear.equals(year)) {
                break;
            }
            click(calendarRightArrow);
        }

        click(By.xpath("//table//a[text()='" + day + "']"));
        return this;
    }

    public String getDate() {
        return getText(calendarField);
    }

    public boolean search(String text) {
        setText(searchBox, text);
        click(searchBtn);

        if (driver.findElements(noSearchResults).size() > 0) {
            goBack();
            return false;
        }
        return true;
    }

    public void clickTwitterButton() {
        click(twitterBtn);
    }

    public void switchToNewWindow() {
        // Get current window handle
        String currentWindow = getWindowHandle();

        // Get all window handles
        Set<String> handles = getWindowHandles();

        // Switch to new window
        Iterator<String> iter = handles.iterator();
        String newWindow = null;
        while (iter.hasNext()) {
            newWindow = iter.next();
            if (!currentWindow.equals(newWindow)) {
                driver.switchTo().window(newWindow);
            }
        }
    }

    public void dragMap(int x, int y) {
        dragAndDropByOffset(map, x, y);
    }

    public void clickAlertPopup() {
        click(alertBtn);
    }

    public void clickConfirmPopup() {
        click(confirmBtn);
    }

    public String getConfirmPopupSelection() {
        return getText(confirmPopupResult);
    }

    public void clickPromptPopup() {
        click(promptBtn);
    }

    public void waitForPromptPopupResult(String expectedText) {
        waitForElementText(promptPopupResult, expectedText);
    }

    public SandboxPage openModal() {
        click(modalBtn);
        return this;
    }

    public SandboxPage modalSendMessage(String name, String email, String message) {
        setText(modalName, name);
        setText(modalEmail, email);
        setText(modalMsg, message);
        click(modalSubmitBtn);
        return this;
    }

    public SandboxPage doHover() {
        hoverOverElement(hover);
        return this;
    }

    public String getHoverText() {
        return getText(hover);
    }

    public SandboxPage scrollMapIntoView() {
        scrollElementIntoView(map);
        return this;
    }
}
