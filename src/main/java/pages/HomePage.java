package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage
{
    private WebDriver driver;

    private final By navCartCountLocator = By.id("nav-cart-count");
    private final By signInToolTipButtonLocator = By.cssSelector("div[id=\"nav-signin-tooltip\"] span");
    private final By navBarHamburgerMenuLocator = By.id("nav-hamburger-menu");
    private final By hamburgerMenuSeeAllLocator = By.cssSelector("a[class=\"hmenu-item hmenu-compressed-btn\"]");
    private final By hamburgerMenuVideoGamesLocator = By.cssSelector("a[data-menu-id=\"16\"]");
    private final By hamburgerMenuAllVideoGamesLocator = By.cssSelector("ul[data-menu-id=\"16\"] :nth-child(3)");

    public HomePage(WebDriver driver)
    {
        this.driver = driver;
    }


    public LoginPage clickLoginToolTipButton()
    {
        driver.findElement(signInToolTipButtonLocator).click();

        return new LoginPage(driver);
    }

    public void clickNavBarHamburgerMenu()
    {
        driver.findElement(navBarHamburgerMenuLocator).click();
    }

    public void clickHamburgerMenuSeeAll()
    {
        driver.findElement(hamburgerMenuSeeAllLocator).click();
    }

    public void clickHamburgerMenuVideoGames()
    {
        driver.findElement(hamburgerMenuVideoGamesLocator).click();
    }

    public AllVideoGamesPage clickHamburgerMenuAllVideoGames()
    {
        driver.findElement(hamburgerMenuAllVideoGamesLocator).click();
        return new AllVideoGamesPage(driver);
    }

    public int getNavCartCount()
    {
        return Integer.parseInt(driver.findElement(navCartCountLocator).getText());
    }
}
