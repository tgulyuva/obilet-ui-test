package hooks;

import config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.time.StopWatch;
import utils.DriverManager;


public class TestHooks {
    private StopWatch stopwatch;   

    @Before
    public void setUp(Scenario scenario) {
        String browserName = TestConfig.getBrowser();
        DriverManager.setDriver(browserName);
        
        System.out.println("Starting scenario: " + scenario.getName());
        stopwatch = new StopWatch();  
        stopwatch.start();
    }

    @After
    public void tearDown(Scenario scenario) {
        DriverManager.quitDriver();
        System.out.println("Completed scenario: " + scenario.getName());
        stopwatch.stop();
        System.out.println("Scenario duration: " + stopwatch.getTime() + "ms");

    }
} 