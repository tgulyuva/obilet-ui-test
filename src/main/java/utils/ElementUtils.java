package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ElementUtils {
    private static Properties selectors;
    private static final String SELECTORS_FILE = "selectors.properties";
    private static final int DEFAULT_TIMEOUT = 10;
    
    static {
        loadSelectors();
    }
    
    private static void loadSelectors() {
        selectors = new Properties();
        try (InputStream inputStream = ElementUtils.class.getClassLoader().getResourceAsStream(SELECTORS_FILE)) {
            if (inputStream != null) {
                selectors.load(inputStream);
            } else {
                throw new RuntimeException("Selectors file not found: " + SELECTORS_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load selectors file: " + SELECTORS_FILE, e);
        }
    }
    
    public static String getSelector(String key) {
        String selector = selectors.getProperty(key);
        if (selector == null) {
            throw new RuntimeException("Selector not found for key: " + key);
        }
        return selector;
    }
    
    public static WebElement findElement(WebDriver driver, String selectorKey) {
        String selector = getSelector(selectorKey);
        return driver.findElement(By.cssSelector(selector));
    }
    
    public static List<WebElement> findElements(WebDriver driver, String selectorKey) {
        String selector = getSelector(selectorKey);
        return driver.findElements(By.cssSelector(selector));
    }

    public static WebElement waitAndFindElement(WebDriver driver, String selectorKey) {
        return waitAndFindElement(driver, selectorKey, DEFAULT_TIMEOUT);
    }
    
    public static WebElement waitAndFindElement(WebDriver driver, String selectorKey, int timeoutSeconds) {
        String selector = getSelector(selectorKey);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    public static WebElement waitForClickableElement(WebDriver driver, String selectorKey) {
        return waitForClickableElement(driver, selectorKey, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForClickableElement(WebDriver driver, String selectorKey, int timeoutSeconds) {
        String selector = getSelector(selectorKey);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
    }
    
    public static void waitForElementClickable(WebDriver driver, WebElement element) {
        waitForElementClickable(driver, element, DEFAULT_TIMEOUT);
    }

    public static void waitForElementClickable(WebDriver driver, WebElement element, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public static boolean isElementClickable(WebDriver driver, String selectorKey) {
        try {
            WebElement element = findElement(driver, selectorKey);
            return element.isEnabled() && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void safeClick(WebDriver driver, String selectorKey) {
        WebElement element = waitForClickableElement(driver, selectorKey);
        element.click();
    }

    public static void safeType(WebDriver driver, String selectorKey, String text) {
        WebElement element = waitAndFindElement(driver, selectorKey);
        element.clear();
        element.sendKeys(text);
    }
    
    public static String getText(WebDriver driver, String selectorKey) {
        WebElement element = waitAndFindElement(driver, selectorKey);
        return element.getText();
    }
    
    public static String getAttribute(WebDriver driver, String selectorKey, String attributeName) {
        WebElement element = waitAndFindElement(driver, selectorKey);
        return element.getAttribute(attributeName);
    }
} 