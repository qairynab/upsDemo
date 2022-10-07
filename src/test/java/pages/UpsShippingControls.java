package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static support.TestContext.getDriver;

public class UpsShippingControls extends Page {

    @FindBy(id = "nbsBackForwardNavigationContinueButton")
    private WebElement continueButton;
    @FindBy(css = ".ups-lever_switch_no")
    private WebElement residentialSwitchNo;
    @FindBy(css = ".ups-lever_switch_yes")
    private WebElement residentialSwitchYes;
    @FindBy(id = "nbsAddressClassificationContinue")
    private WebElement classificationContinue;
    @FindBy(id = "vm.residentialAddressControlId")
    private WebElement internalResidentialCheckbox;

    @FindBy(id = "total-charges-spinner")
    private WebElement totalCharges;

    public boolean areTotalChargesAppear() {
        try {
            waitUntilVisible(totalCharges);
        } catch (TimeoutException e) {
            return false;
        }
        return !totalCharges.getText().isEmpty();
    }


    public void clickSubmit() {
        // workaround for bug #...
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scrollBy(continueButton, 0, 200);
//        clickWithJs(continueButton);
        continueButton.click();
    }

    public void clickConfirmAddress() {
        try {
            if (classificationContinue.isDisplayed()) {
                classificationContinue.click();
            }
        } catch (NoSuchElementException e) {
            // that means element not in DOM, just continue
        }
    }

    public void enableResidential() {
        if (residentialSwitchNo.isDisplayed()) {
            residentialSwitchNo.click();
        }
    }
    public void disableResidential() {
        if (residentialSwitchYes.isDisplayed()) {
            residentialSwitchYes.click();
        }
    }
}