package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static support.TestContext.getConfig;
import static support.TestContext.getDriver;

public class Page {

    protected String url;
    protected String title;

    public Page() {
        PageFactory.initElements(getDriver(), this);
    }

    public void open() {
        getDriver().get(url);
    }

    public String getTitle() {
        return title;
    }

    public WebDriverWait getWait() {
        return getWait(getConfig().getExplicitTimeout());
    }

    public WebDriverWait getWait(int secondsTimeout) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(secondsTimeout));
    }

    public Actions getActions() {
        return new Actions(getDriver());
    }
    public void mouseOver(WebElement element) {
        getActions().moveToElement(element).perform();
    }

    public void waitUntilClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    public void waitUntilVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }
    public void waitUntilInvisible(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }
    public void waitUntilTextNotEmpty(WebElement element) {
        getWait().until(driver -> element.getText().length() > 0);
    }

    public void clickWithJs(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    public void scrollBy(WebElement element, int xOffset, int yOffset) {
        new Actions(getDriver()).moveToElement(element).perform();
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(" + xOffset + ", " + yOffset + ");");
    }

    public void refresh() {
        getDriver().navigate().refresh();
    }

    public boolean areJsErrorsPresent() {
        LogEntries logs = getDriver().manage().logs().get(LogType.BROWSER);
        boolean flag = false;
        for (LogEntry log : logs) {
            if (log.getLevel().getName().equals("SEVERE")) {
                System.err.println(log);
                flag = true;
            }
        }
        return flag;
    }

}
