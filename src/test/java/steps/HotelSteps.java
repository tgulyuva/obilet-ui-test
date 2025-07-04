package steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import utils.DriverManager;
import utils.ElementUtils;

import java.util.ArrayList;
import java.util.List;

public class HotelSteps {

    @Then("Hotel elements with selector {string} should be sorted from low to high")
    public void theElementsWithSelectorShouldBeSortedFromLowToHigh(String selectorKey) {
        List<WebElement> elements = ElementUtils.findElements(DriverManager.getDriver(), selectorKey);
        
        Assert.assertTrue("At least 2 elements",
                         elements.size() >= 2);
        
        System.out.println("Found " + elements.size() + " price elements");
        
        // to integer
        List<Integer> prices = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            String priceText = elements.get(i).getText().trim();
            Integer price = extractPriceFromText(priceText);
            prices.add(price);
        }
        
        // check sort
        for (int i = 0; i < prices.size() - 1; i++) {
            Integer currentPrice = prices.get(i);
            Integer nextPrice = prices.get(i + 1);
            
            Assert.assertTrue("Price at index " + i + " (" + currentPrice + " TL) should be less than or equal to price at index " + (i + 1) + " (" + nextPrice + " TL)", 
                             currentPrice <= nextPrice);
        }
        
        System.out.println("All prices are sorted from low to high correctly");
    }

    private Integer extractPriceFromText(String priceText) {
        try {
            String numericPart = priceText.replaceAll("[^0-9.,]", "");
            numericPart = numericPart.replace(".", "");
            numericPart = numericPart.replace(",", "");

            return Integer.parseInt(numericPart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract price from text: '" + priceText + "'", e);
        }
    }
} 