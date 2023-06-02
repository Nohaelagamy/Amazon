package base;

import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;


public class BaseTests
{
    private WebDriver driver;

    protected HomePage homePage;
    String browser = System.getProperty("browser");

    @BeforeClass
    @Parameters({"browser" , "isRunOnLocal"})
    public void setUp(String browserName , boolean isRunOnLocal) throws MalformedURLException
    {
        switch(browserName.toLowerCase())
        {
            case "chrome" :
                if(isRunOnLocal)
                {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(getChromeOptions());
                }
                else  // Running tests using selenium grid with docker (must have required selenium grid docker images first)
                {
                    //In Terminal -> docker-compose -f docker-compose.yml up  // or docker-compose up -d
                    // To scale up -> docker-compose -d --scale chrome=10
                    // -> docker-compose down

                    DesiredCapabilities dc = new DesiredCapabilities();
                    dc.setBrowserName("chrome");
                    driver = new RemoteWebDriver(new URL("http://localhost:4444") , dc);
                    System.out.println("A chrome browser is opened remotely");
                }
                break;

            case "firefox":
                if(isRunOnLocal)
                {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                else
                {
                    DesiredCapabilities dc = new DesiredCapabilities();
                    dc.setBrowserName("firefox");
                    driver = new RemoteWebDriver(new URL("http://localhost:4444") , dc);
                    System.out.println("A firefox browser is opened remotely");
                }
                break;

            case "edge":
                if(isRunOnLocal)
                {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                }
                else
                {
                    DesiredCapabilities dc = new DesiredCapabilities();
                    dc.setBrowserName("edge");
                    driver = new RemoteWebDriver(new URL("http://localhost:4444") , dc);
                    System.out.println("An edge browser is opened remotely");
                }
                break;

        }

        homePage = new HomePage(driver);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));  //implicit wait

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5)); //page load time

        gotoHomepage();

    }

    @BeforeMethod
    public void gotoHomepage()
    {
        driver.get("https://www.amazon.eg/?language=en_AE");
    }


    @AfterMethod
    public void recordFailure(ITestResult result)
    {
        if(ITestResult.FAILURE == result.getStatus())
        {
            TakesScreenshot camera = (TakesScreenshot) driver;
            File screenShot = camera.getScreenshotAs(OutputType.FILE);

            try {
                Files.move(screenShot ,
                        new File(".\\src\\main\\resources\\screenShots\\onFailure\\" + result.getName() +  ".png"));
            } catch (IOException e) {throw new RuntimeException(e);}
        }

    }


    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }


    public ChromeOptions getChromeOptions()
    {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));  //To disable info bar
        //options.addArguments("--headless=new");  // Run Tests in Headless mode (No Browser opened)
        return options;
    }



}
