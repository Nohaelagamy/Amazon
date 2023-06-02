package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class ProductPage
{
    WebDriver driver;
    private final By addToCartButton = By.id("add-to-cart-button");

    public ProductPage(WebDriver driver)
    {
        this.driver = driver;
    }


    public void addAllRequiredProductsToCart()
    {
        String currentTab = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        int index = tabs.indexOf(currentTab);

        for(int i = 0 ; i < tabs.size() ; i++)
        {
            if( i != index)
            {
                driver.switchTo().window(tabs.get(i));
                driver.findElement(addToCartButton).click();
                System.out.println("Item is ADDED");
            }
        }
    }
}
