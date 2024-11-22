package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;
import java.util.logging.Logger;

public class TestWebDriverPoolFactory {

    private static final Logger logger = Logger.getLogger(TestWebDriverPoolFactory.class.getName());

    public static void main(String[] args) {
        WebDriverFactoryInterface webDriverFactoryInterface = new WebDriverFactory();
        WebDriverPoolFactory webDriverPoolFactory = new WebDriverPoolFactory(webDriverFactoryInterface);

        String url1 = "https://www.google.com/";
        String url2 = "https://www.booking.com/";

        ChromeOptions chromeOptions1 = new ChromeOptions();
        chromeOptions1.addArguments("start-maximized");
        chromeOptions1.addArguments("--incognito");

        ChromeOptions chromeOptions2 = new ChromeOptions();
        chromeOptions2.addArguments("--incognito");
        chromeOptions2.addArguments("start-maximized");

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--headless");

        logger.info("Test 1 Started");
        test(webDriverPoolFactory, BrowserType.CHROME, url1, chromeOptions1);
        logger.info("Test 1 Completed");
        logger.info("Test 2 Started");
        test(webDriverPoolFactory, BrowserType.EDGE, url1, edgeOptions);
        logger.info("Test 2 Completed");
        logger.info("Test 3 Started");
        test(webDriverPoolFactory, BrowserType.CHROME, url2, chromeOptions2);
        logger.info("Test 3 Completed");
        logger.info("Test 4 Started");
        test(webDriverPoolFactory, BrowserType.EDGE, url2, edgeOptions);
        logger.info("Test 4 Completed");
        webDriverPoolFactory.closeAllBrowsers();
    }

    private static void test(WebDriverPoolFactory webDriverPoolFactory, BrowserType browserType, String url, Capabilities capabilities){
        WebDriver driver = webDriverPoolFactory.getWebDriverCapabilities(browserType, capabilities, url);
        logger.info("Page title : " + driver.getTitle());
        webDriverPoolFactory.releaseDriver(driver);
    }
}
