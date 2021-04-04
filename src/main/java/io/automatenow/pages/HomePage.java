package io.automatenow.pages;

import org.openqa.selenium.By;

/**
 * @author Marco A. Cruz
 */
public class HomePage extends BasePage {
    private By welcomeMsg = By.xpath("//p[text()='Welcome to AUTOMATENOW! ']");

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getWelcomeMessage() {
        return driver.findElement(welcomeMsg).getText();
    }
}
