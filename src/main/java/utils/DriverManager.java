package utils;

import config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;


public class DriverManager {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setDriver(String browserName) {
        WebDriver driver = createDriver(browserName.toLowerCase());
        driverThreadLocal.set(driver);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.getImplicitWait()));
        
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("WebDriver not found.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    private static WebDriver createDriver(String browserName) {
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--remote-allow-origins=*");

            if (TestConfig.isHeadless()) {
                chromeOptions.addArguments("--headless");
            }

            return new ChromeDriver(chromeOptions);
        }
        throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }
} 