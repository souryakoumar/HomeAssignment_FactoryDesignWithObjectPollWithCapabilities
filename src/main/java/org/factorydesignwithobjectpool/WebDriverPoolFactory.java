package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class WebDriverPoolFactory {
    private static final Logger logger = Logger.getLogger(WebDriverPoolFactory.class.getName());
    private final WebDriverFactoryInterface driverFactory;
    private final ConcurrentMap<BrowserCapabilitiesKey, BlockingQueue<WebDriver>> driverPool;
    private final ConcurrentMap<WebDriver, BrowserCapabilitiesKey> driverToBrowserKey;

    public WebDriverPoolFactory(WebDriverFactoryInterface driverFactory) {
        this.driverFactory = driverFactory;
        driverPool = new ConcurrentHashMap<BrowserCapabilitiesKey, BlockingQueue<WebDriver>>();
        driverToBrowserKey = new ConcurrentHashMap<WebDriver, BrowserCapabilitiesKey>();

        //for every browser type, create new Blocking Queue
        /*for(BrowserType browserType: BrowserType.values()){
            driverPool.put(browserType, new LinkedBlockingDeque<>());
        }*/
    }

    public WebDriver getWebDriverCapabilities(BrowserType browserType, Capabilities capabilities, String url){
        WebDriver driver = null;
        if (driverPool.isEmpty()){
            driver = driverFactory.createWebDriver(browserType, capabilities);
            BrowserCapabilitiesKey browserCapabilitiesKey = new BrowserCapabilitiesKey(browserType, capabilities);
            driverPool.put(browserCapabilitiesKey, new LinkedBlockingDeque<>());
            driverToBrowserKey.put(driver, browserCapabilitiesKey);
            logger.info("Created new driver : " + driver.hashCode());
        }else{
            BrowserCapabilitiesKey browserCapabilitiesKey = new BrowserCapabilitiesKey(browserType, capabilities);
            List<String> browserCapabilitiesRequested = browserCapabilitiesKey.getListCapabilities();
            for (BrowserCapabilitiesKey browserCapabilitiesKey1: driverPool.keySet()){
                List<String> browserCapabilitiesInQueue = browserCapabilitiesKey1.getListCapabilities();
                if ((browserCapabilitiesKey1.getBrowserType().equals(browserCapabilitiesKey.getBrowserType())) && (browserCapabilitiesInQueue.containsAll(browserCapabilitiesRequested))){
                    Queue<WebDriver> queue = driverPool.get(browserCapabilitiesKey1);
                    driver = queue.poll();
                    logger.info("Reusing existing driver : " + driver.hashCode());
                    break;
                }
            }
            if (driver == null){
                driver = driverFactory.createWebDriver(browserType, capabilities);
                BrowserCapabilitiesKey browserCapabilitiesKey1 = new BrowserCapabilitiesKey(browserType, capabilities);
                driverPool.put(browserCapabilitiesKey1, new LinkedBlockingDeque<>());
                driverToBrowserKey.put(driver, browserCapabilitiesKey1);
                logger.info("Created new driver : " + driver.hashCode());
            }
        }

        driverFactory.setupDriver(url, driver);
        return driver;
    }

    public void releaseDriver(WebDriver driver){
        BrowserCapabilitiesKey browserCapabilitiesKey = driverToBrowserKey.get(driver);
        if(browserCapabilitiesKey != null){
            BlockingQueue<WebDriver> queue = driverPool.get(browserCapabilitiesKey);
            queue.offer(driver);
            logger.info("Driver released and added to the queue again.");
        }else {
            driver.quit();
            logger.info("Invalid Browser Type : "+driver.hashCode()+",hence quit the browser.");
        }
    }

    public void closeAllBrowsers(){
        for (BrowserCapabilitiesKey browserCapabilitiesKey1: driverPool.keySet()){
            BlockingQueue<WebDriver> queue = driverPool.get(browserCapabilitiesKey1);
            while(!queue.isEmpty()){
                WebDriver driver = queue.poll();
                driver.quit();
            }
        }
    }
}
