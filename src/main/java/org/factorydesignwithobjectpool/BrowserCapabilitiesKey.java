package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BrowserCapabilitiesKey {
    private List<String> listCapabilities;
    private BrowserType browserType;

    public BrowserCapabilitiesKey(BrowserType browserType, Capabilities capabilities) {
        this.browserType = browserType;
        this.listCapabilities = sortCapabilities(browserType, capabilities);
    }

    public List<String> getListCapabilities() {
        return listCapabilities;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    @SuppressWarnings("unchecked")
    public List<String> sortCapabilities(BrowserType browserType, Capabilities capabilities){
        Map<String, Object> browserOptionsMap;
        switch (browserType){
            case CHROME:
                browserOptionsMap = (Map<String, Object>)capabilities.getCapability(ChromeOptions.CAPABILITY);
                break;
            case EDGE:
                browserOptionsMap = (Map<String, Object>)capabilities.getCapability(EdgeOptions.CAPABILITY);
                break;
            default:
                throw new IllegalArgumentException("Illegal Browser Type");
        }

        List<String> args = (List<String>)browserOptionsMap.getOrDefault("args", Collections.emptyList());
        List<String> sortedArgs = new ArrayList<>(args);
        Collections.sort(sortedArgs);
        return sortedArgs;
    }
}
