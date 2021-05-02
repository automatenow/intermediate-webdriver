package io.automatenow.pages;

import org.openqa.selenium.By;

/**
 * @author Marco A. Cruz
 */
public class TablesPage extends BasePage {

    public String getItemPrice(String item) {
        return driver.findElement(By.xpath("//td[text()='" + item + "']/following-sibling::td")).getText();
    }
}
