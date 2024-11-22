package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ChromeBrowserDriver implements Browser{
    private static final Logger logger = Logger.getLogger(ChromeBrowserDriver.class.getName());
    ChromeOptions chromeOptions;
    @Override
    public WebDriver launchBrowser(Capabilities capabilities) {
        if(capabilities instanceof ChromeOptions){
            chromeOptions = (ChromeOptions) capabilities;
            logger.info("Capabilities Set Successfully for Chrome.");
        }else{
            chromeOptions = new ChromeOptions();
            logger.info("Provide capabilities are not compatible for chrome, so set to default.");
        };
        return new ChromeDriver(chromeOptions);
    }
}