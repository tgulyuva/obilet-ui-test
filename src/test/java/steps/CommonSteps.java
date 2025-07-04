package steps;

import config.TestConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.DriverManager;
import utils.ElementUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommonSteps {
    
    private static Map<String, String> storedTexts = new HashMap<>();

    @Given("I am on the homepage")
    public void iAmOnTheHomePage() {
        String fullUrl = TestConfig.getBaseUrl();
        DriverManager.getDriver().get(fullUrl);
    }


    @Given("I am on the {string} page")
    public void iAmOnThePage(String path) {
        String fullUrl = TestConfig.getBaseUrl() + "/" + path;
        DriverManager.getDriver().get(fullUrl);
    }


    @When("I click on the element with selector {string}")
    public void iClickOnTheElementWithSelector(String selectorKey) {
        ElementUtils.safeClick(DriverManager.getDriver(), selectorKey);
    }
    

    @When("I type {string} into the element with selector {string}")
    public void iTypeIntoTheElementWithSelector(String text, String selectorKey) {
        ElementUtils.safeType(DriverManager.getDriver(), selectorKey, text);
    }
    

    @When("I type {string} into the element with selector {string} and press {string}")
    public void iTypeIntoElementAndPressKey(String text, String selectorKey, String keyName) {
        WebElement element = ElementUtils.waitAndFindElement(DriverManager.getDriver(), selectorKey);
        element.clear();
        element.sendKeys(text);
        iWaitForMilliseconds(1000);
        Keys key = getKeyFromString(keyName);
        element.sendKeys(key);
    }
    

    @When("I store text from element with selector {string} as {string}")
    public void iStoreTextFromElementAsVariable(String selectorKey, String variableName) {
        String elementText = ElementUtils.getText(DriverManager.getDriver(), selectorKey);
        storedTexts.put(variableName, elementText);
        System.out.println("Stored text '" + elementText + "' as variable '" + variableName + "'");
    }
    

    @When("I store text from element at index {int} with selector {string} as {string}")
    public void iStoreTextFromElementAtIndexAsVariable(int index, String selectorKey, String variableName) {
        List<WebElement> elements = ElementUtils.findElements(DriverManager.getDriver(), selectorKey);
        
        Assert.assertTrue("Elements should be found with selector: " + selectorKey, 
                         !elements.isEmpty());
        Assert.assertTrue("Index " + index + " should be valid for " + elements.size() + " elements", 
                         index >= 0 && index < elements.size());
        
        WebElement targetElement = elements.get(index);
        String elementText = targetElement.getText();
        storedTexts.put(variableName, elementText);
        
        System.out.println("Stored text '" + elementText + "' from element at index " + index + " as variable '" + variableName + "'");
    }
    

    @Then("The stored variable {string} should equal stored variable {string}")
    public void theStoredVariableShouldEqualStoredVariable(String variableName1, String variableName2) {
        String storedText1 = storedTexts.get(variableName1);
        String storedText2 = storedTexts.get(variableName2);
        Assert.assertNotNull("Variable '" + variableName1 + "' should be stored", storedText1);
        Assert.assertNotNull("Variable '" + variableName2 + "' should be stored", storedText2);
        Assert.assertEquals("Variable '" + variableName1 + "' should equal variable '" + variableName2 + "'", 
                           storedText1, storedText2);
    }
    

    @Then("The stored variable {string} should contain stored variable {string}")
    public void theStoredVariableShouldContainStoredVariable(String containerVariableName, String containedVariableName) {
        String containerText = storedTexts.get(containerVariableName);
        String containedText = storedTexts.get(containedVariableName);
        Assert.assertNotNull("Variable '" + containerVariableName + "' should be stored", containerText);
        Assert.assertNotNull("Variable '" + containedVariableName + "' should be stored", containedText);
        Assert.assertTrue("Variable '" + containerVariableName + "' ('" + containerText + "') should contain variable '" + 
                         containedVariableName + "' ('" + containedText + "')", 
                         containerText.contains(containedText));
    }
    

    @When("I store attribute {string} from element with selector {string} as {string}")
    public void iStoreAttributeFromElementAsVariable(String attributeName, String selectorKey, String variableName) {
        String attributeValue = ElementUtils.getAttribute(DriverManager.getDriver(), selectorKey, attributeName);
        storedTexts.put(variableName, attributeValue);
        System.out.println("Stored attribute '" + attributeName + "' value '" + attributeValue + "' as variable '" + variableName + "'");
    }


    private Keys getKeyFromString(String keyName) {
        switch (keyName.toUpperCase()) {
            case "ENTER":
                return Keys.ENTER;
            default:
                throw new IllegalArgumentException("Unsupported key: " + keyName + 
                    ". Supported keys: ENTER, ESC, TAB, SPACE, ARROW_UP, ARROW_DOWN, ARROW_LEFT, ARROW_RIGHT, " +
                    "BACKSPACE, DELETE, HOME, END, PAGE_UP, PAGE_DOWN, CTRL, ALT, SHIFT, F1-F12");
        }
    }
    

    @Then("The element with selector {string} should have text {string}")
    public void theElementWithSelectorShouldHaveText(String selectorKey, String expectedText) {
        String actualText = ElementUtils.getText(DriverManager.getDriver(), selectorKey);
        Assert.assertEquals("Element text should exactly match. Expected: '" + expectedText + "', Actual: '" + actualText + "'", 
                           expectedText, actualText);
    }


    @When("I click on element at index {int} with selector {string}")
    public void iClickOnElementAtIndexWithSelector(int index, String selectorKey) {
        List<WebElement> elements = ElementUtils.findElements(DriverManager.getDriver(), selectorKey);
        
        Assert.assertTrue("Elements should be found with selector: " + selectorKey, 
                         !elements.isEmpty());
        Assert.assertTrue("Index " + index + " should be valid for " + elements.size() + " elements", 
                         index >= 0 && index < elements.size());
        
        WebElement targetElement = elements.get(index);
        
        ElementUtils.waitForElementClickable(DriverManager.getDriver(), targetElement);
        targetElement.click();
        
        System.out.println("Found " + elements.size() + " elements with selector '" + selectorKey + "'");
        System.out.println("Clicked on element at index " + index);
    }


    @Then("The element with selector {string} should be clickable")
    public void theElementWithSelectorShouldBeClickable(String selectorKey) {
        Assert.assertTrue("Element should be clickable: " + selectorKey, 
                         ElementUtils.isElementClickable(DriverManager.getDriver(), selectorKey));
    }


    @When("I wait for {int} milliseconds")
    public void iWaitForMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    
    @Then("The page should be fully loaded")
    public void thePageShouldBeFullyLoaded() {
        String readyState = (String) ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("return document.readyState");
        Assert.assertEquals("Page should be fully loaded", "complete", readyState);
    }
} 