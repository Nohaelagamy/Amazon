package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;



public class AllVideoGamesPage
{
    WebDriver driver;

    private final By videoGamesHeaderTagLocator = By.cssSelector("h1 b");
    private final By freeShippingCheckBoxLocator = By.cssSelector("a[href*=\"free_shipping\"] div");
    private final By newConditionFilterLocator = By.cssSelector("a[href*=\"condition-type_1\"]");
    private final By selectSortMenuLocator = By.id("s-result-sort-select");

    public enum ComparisonOperator {lessThan , greaterThan}
    List<WebElement> requiredProductsLinksElements;


    public AllVideoGamesPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public String getPageHeader()
    {
        return driver.findElement(videoGamesHeaderTagLocator).getText();
    }

    public void clickFreeShippingCheckBox()
    {
        driver.findElement(freeShippingCheckBoxLocator).click();
    }

    public void scrollToNewConditionFilter()
    {
        Actions actions = new Actions(driver);
        actions.scrollToElement(driver.findElement(newConditionFilterLocator));
        actions.perform();
    }

    public void clickNewConditionFilter()
    {
        driver.findElement(newConditionFilterLocator).click();
    }

    public void scrollToSortMenu()
    {
        Actions actions = new Actions(driver);
        actions.scrollToElement(driver.findElement(selectSortMenuLocator));
        actions.perform();
    }

    public void sortBy(String option)
    {
        Select select = new Select(driver.findElement(selectSortMenuLocator));

        select.selectByVisibleText(option);
    }

    public ProductPage openInNewTabsProducts(ComparisonOperator comparisonOperator , int price)
    {
        String operatorSignInLocatorStr = "";
        String priceInLocatorStr = String.valueOf(price);

        switch(comparisonOperator)
        {
            case lessThan:
                operatorSignInLocatorStr = "<";
                break;
            case greaterThan:
                operatorSignInLocatorStr = ">";
                break;
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelectorAll('span[class=\\\"a-price-whole\\\"]').forEach(item => {item.innerText = item.innerText.replace(\",\",\"\").replace(\".\",\"\").replace(\"\\n\",\"\")})");

        String requiredProductsLinksXpathLocator = String.format("//div[@class=\"sg-row\"]//span[@class=\"a-price\"]//span[@class=\"a-price-whole\" and text() %s %s]//ancestor::div[@class=\"a-section a-spacing-small a-spacing-top-small\"]//child::div//h2" , operatorSignInLocatorStr,priceInLocatorStr);

        requiredProductsLinksElements = driver.findElements(By.xpath(requiredProductsLinksXpathLocator));

        Actions actions = new Actions(driver);
        for(WebElement product : requiredProductsLinksElements)
        {
            actions.moveToElement(product);
            actions.keyDown(Keys.CONTROL)
                    .click(product)
                    .build()
                    .perform();
            try {Thread.sleep(5000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        }

        return new ProductPage(driver);
    }

    public int getNumOfRequiredProducts()
    {
        return requiredProductsLinksElements.size();
    }
}
