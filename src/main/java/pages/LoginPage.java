package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage
{
    private WebDriver driver;

    private final By emailOrMobNumFieldLocator = By.id("ap_email");
    private final By passwordFieldLocator = By.id("ap_password");
    private final By signInButton = By.id("signInSubmit");


    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public void setEmail(String email)
    {
        driver.findElement(emailOrMobNumFieldLocator).sendKeys(email);
    }

    public void setPassword(String password)
    {
        driver.findElement(passwordFieldLocator).sendKeys(password);
    }

    public void clickSignInButton()
    {
        driver.findElement(signInButton).click();
    }

}
