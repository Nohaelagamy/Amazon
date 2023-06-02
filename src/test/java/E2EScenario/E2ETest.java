package E2EScenario;

import base.BaseTests;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AllVideoGamesPage;
import pages.ProductPage;


public class E2ETest extends BaseTests
{
    @Test
    public void testTheSuccessfulPurchaseScenario()
    {
        SoftAssert softAssert = new SoftAssert();

        /*LoginPage loginPage = homePage.clickLoginToolTipButton();
        loginPage.setEmail("test@test.com");
        loginPage.setPassword("P@ssw0rd");
        loginPage.clickSignInButton();*/

        //softAssert.assertTrue(getWindowManager().getCurrentWindowTitle().contains("Your Souq is now Amazon.eg | Welcome to Amazon.eg in Egypt."));

        homePage.clickNavBarHamburgerMenu();
        homePage.clickHamburgerMenuSeeAll();
        homePage.clickHamburgerMenuVideoGames();
        AllVideoGamesPage allVideoGamesPage = homePage.clickHamburgerMenuAllVideoGames();

        softAssert.assertEquals(allVideoGamesPage.getPageHeader() , "Video Games");

        allVideoGamesPage.clickFreeShippingCheckBox();
        allVideoGamesPage.scrollToNewConditionFilter();
        allVideoGamesPage.clickNewConditionFilter();
        allVideoGamesPage.scrollToSortMenu();
        allVideoGamesPage.sortBy("Price: High to Low");

        try {Thread.sleep(5000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        ProductPage productPage = allVideoGamesPage.openInNewTabsProducts(AllVideoGamesPage.ComparisonOperator.lessThan , 15000);
        productPage.addAllRequiredProductsToCart();

        gotoHomepage();

        softAssert.assertEquals(homePage.getNavCartCount() , allVideoGamesPage.getNumOfRequiredProducts());
        softAssert.assertAll();
    }
}
