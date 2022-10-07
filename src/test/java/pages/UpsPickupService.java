package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static support.TestContext.getDriver;

public class UpsPickupService extends Page {


    @FindBy(xpath = "//*[@id='nbsServiceGridControl']//p[contains(@id,'nbsServiceTileTotalCharge')]")
    private List<WebElement> priceElements;

    private WebElement getElementByPrice(double price) {
        return getDriver().findElement(By.xpath("//*[@id='nbsServiceGridControl']//p[contains(@id,'nbsServiceTileTotalCharge')][contains(text(), '" + price + "')]"));
    }


    public void selectCheapestPrice() {
        double cheapestPrice = Double.MAX_VALUE;
        WebElement cheapestPriceElement = null;
        for (WebElement priceElement : priceElements) {
            waitUntilTextNotEmpty(priceElement);
            String textPrice = priceElement.getText();
            double price = Double.parseDouble(textPrice.replaceAll("[^0-9.]", ""));
            System.out.println(price);
            if (cheapestPrice > price) {
                cheapestPrice = price;
                cheapestPriceElement = priceElement;
            }
        }
        clickWithJs(cheapestPriceElement);

    }

    public void clickOptionByPrice(double price) {
        getElementByPrice(price).click();

    }

    @FindBy(id = "nbsShipmentDescription")
    private WebElement shipmentDescription;

    public  void  fillShipmentDescription(String value){
        shipmentDescription.sendKeys(value);
    }

    @FindBy(xpath = "//label[@for='nbsDirectDeliveryOnlyOptionBaseOptionSwitch']")
    private WebElement deliveryOnlyReciverAddress;

    public void checkDeliveryOnlyReciverAddress(){
        scrollBy(deliveryOnlyReciverAddress, 0, 200);
        deliveryOnlyReciverAddress.click();
    }

    @FindBy(id = "total-charges-spinner")
    private WebElement totalChargesChanged;

    public boolean areTotalChargesChangedAppear() {
        try {
            waitUntilVisible(totalChargesChanged);
            Thread.sleep(3000);
        } catch (TimeoutException e) {
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return !totalChargesChanged.getText().isEmpty();
    }

    @FindBy(xpath = "//span[contains(text(),'PayPal')]")
    private WebElement payPall;

    public void checkPayPall(){
        payPall.click();
    }

    @FindBy(id = "nbsBackForwardNavigationReviewPrimaryButton")
    private WebElement reviewButton;

    public void clickReviewButton(){
        scrollBy(reviewButton, 0, 200);
        reviewButton.click();
    }

    @FindBy(id = "nbsSpaOrigin")
    private WebElement shipFrom;
    public void getShipFrom(){
        shipFrom.isDisplayed();
    }

    @FindBy(id = "nbsSpaDestination")
    private WebElement shipTo;
    public void getShipTo(){
        shipTo.isDisplayed();
    }

    @FindBy(id = "nbsSpaShipmentPackages")
    private WebElement packageInfo;
    public void getPackageInfo(){
        packageInfo.isDisplayed();
    }

    @FindBy(id = "nbsShipmentServicesDropoffPickupEdit")
    private WebElement shippingService;
    public void getShippingService(){
        shippingService.isDisplayed();
    }

    @FindBy(id = "nbsOptionsDrawerShipmentOptions")
    private WebElement additionalOptions;
    public void getAdditionalOptions(){
        additionalOptions.isDisplayed();
    }

    @FindBy(id = "nbsPaymentSummaryBillShippingCharges")
    private WebElement payment;
    public void getPayment(){
        payment.isDisplayed();
    }

    @FindBy(id = "nbsBackForwardNavigationCancelShipmentButton")
    private WebElement cancelShipmentButton;

    public void clickCancelShipmentButton(){
        scrollBy(cancelShipmentButton, 0, 200);
        cancelShipmentButton.click();
    }

    @FindBy(id = "nbsCancelShipmentWarningYes")
    private WebElement shipmentWarningYesButton;

    public void clickShipmentWarningYes(){
        scrollBy(shipmentWarningYesButton, 0, 200);
        shipmentWarningYesButton.click();
    }








}
