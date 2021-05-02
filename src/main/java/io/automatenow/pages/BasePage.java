package io.automatenow.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

/**
 * @author Marco A. Cruz
 */
public class BasePage {
    protected static WebDriver driver;

    public String browser;
    public String baseUrl;
    public Properties properties;

    private void loadProperties() {
        FileInputStream fis = null;

        try {
            properties = new Properties();
            fis = new FileInputStream("src\\main\\java\\io\\automatenow\\config\\config.properties");
            properties.load(fis);

            browser = properties.getProperty("browser");
            baseUrl = properties.getProperty("baseUrl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openBrowser() {
            if (browser.equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().window().maximize();
    }

    public void closeBrowser() {
        driver.quit();
    }

    public void closeWindow() {
        driver.close();
    }

    public Boolean goToHomepage() {
        try {
            loadProperties();
            openBrowser();
            driver.get(baseUrl);
        } catch (Exception e) {
            System.out.println("Unable to navigate to the homepage");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setText(By locator, String text) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        tab(locator);
    }

    public void tab(By locator) {
        driver.findElement(locator).sendKeys(Keys.TAB);
    }

    public String getText(By locator) {
        String displayedText = driver.findElement(locator).getText();
        if (displayedText.isEmpty()) {
            return driver.findElement(locator).getAttribute("value");
        } else {
            return displayedText;
        }
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void goBack() {
        driver.navigate().back();
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public int getNumberOfOpenWindows() {
        return driver.getWindowHandles().size();
    }

    public void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean waitForPageTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Performs a drag-n-drop operation on a given element by a given x,y offset.
     *
     * @param locator The element to be interacted with
     * @param x x-coordinate
     * @param y Y-coordinate
     */
    public void dragAndDropByOffset(By locator, int x, int y) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(locator);
        actions.dragAndDropBy(element, x, y).perform();
    }

    public void dismissPopup() {
        driver.switchTo().alert().dismiss();
    }

    public void acceptPopup() {
        driver.switchTo().alert().accept();
    }

    public void setAlertText(String text) {
        driver.switchTo().alert().sendKeys(text);
    }

    public void waitForElementText(By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.textToBe(locator, text));
    }

    public void hoverOverElement(By locator) {
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void scrollElementIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * Scrolls the document by the specified number of pixels.
     *
     * @param x How many pixels to scroll by, along the x-axis (horizontal).
     * @param y How many pixels to scroll by, along the y-axis (vertical).
     */
    public void scrollPage(int x, int y) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("window.scrollBy(" + x + "," + y + ");");
    }
}
