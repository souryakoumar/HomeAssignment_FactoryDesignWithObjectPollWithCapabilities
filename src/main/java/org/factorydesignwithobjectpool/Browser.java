package org.factorydesignwithobjectpool;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public interface Browser {
    WebDriver launchBrowser(Capabilities capabilities);
}