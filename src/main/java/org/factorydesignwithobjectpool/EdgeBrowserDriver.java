package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class EdgeBrowserDriver implements Browser{
    private static final Logger logger = Logger.getLogger(EdgeBrowserDriver.class.getName());
    EdgeOptions edgeOptions;
    @Override
    public WebDriver launchBrowser(Capabilities capabilities) {
        if (capabilities instanceof EdgeOptions){
            edgeOptions = (EdgeOptions) capabilities;
            logger.info("Capabilities Set Successfully for Edge.");
        }else{
            edgeOptions = new EdgeOptions();
            logger.info("Provide capabilities are not compatible for Edge, so set to default.");
        }
        return new EdgeDriver();
    }
}