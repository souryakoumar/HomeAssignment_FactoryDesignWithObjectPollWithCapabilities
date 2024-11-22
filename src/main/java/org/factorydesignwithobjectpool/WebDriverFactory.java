package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory implements WebDriverFactoryInterface{
    @Override
    public WebDriver createWebDriver(BrowserType browserType, Capabilities capabilities) {
        Browser browser;
        switch (browserType){
            case CHROME:
                return new ChromeBrowserDriver().launchBrowser(capabilities);
            case EDGE:
                return new EdgeBrowserDriver().launchBrowser(capabilities);
            default:
                throw new IllegalArgumentException("Invalid Browser Type");
        }
    }

    @Override
    public void setupDriver(String url, WebDriver driver) {
        driver.get(url);
    }
}