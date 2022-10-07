package pages;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static support.TestContext.getDriver;

public class UpsLanding extends Page {

    public UpsLanding() {
//        url = "https://www.ups.com/us/en/Home.page";
    }

    @FindBy(css = ".close_btn_thick")
    private WebElement closeImplicitCookiesPopup;

    @FindBy(xpath = "(//label[@class='radio-button'])[1]")
    private WebElement acceptExplicitCookiesPopupOption;
    @FindBy(id = "consent_prompt_submit")
    private WebElement confirmExplicitCookiesPopup;

    @FindBy(id = "tabs_0_tab_2")
    private WebElement shipMenu;

    public void closeCookiesPopup() {
        try {
            closeImplicitPopup();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            closeExplicitPopup();
        }
    }

    public void closeImplicitPopup() {
        closeImplicitCookiesPopup.click();
    }

    public void closeExplicitPopup() {
        acceptExplicitCookiesPopupOption.click();
        confirmExplicitCookiesPopup.click();
    }

    public void clickShipMenu() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitUntilClickable(shipMenu);
        shipMenu.click();
    }




}
