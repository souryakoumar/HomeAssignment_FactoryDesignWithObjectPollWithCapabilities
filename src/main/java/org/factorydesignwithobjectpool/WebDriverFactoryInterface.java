package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public interface WebDriverFactoryInterface {
    WebDriver createWebDriver(BrowserType browserType, Capabilities capabilities);

    void setupDriver(String url, WebDriver driver);
}
