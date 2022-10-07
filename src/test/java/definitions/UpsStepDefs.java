package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.*;
import support.TestContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UpsStepDefs {

    //    UpsRegion regionPage = new UpsRegion();
    UpsLanding landingPage= new UpsLanding();
    UpsOrigin originPage = new UpsOrigin();
    UpsDestination destinationPage = new UpsDestination();
    UpsShippingControls shippingControls = new UpsShippingControls();
    UpsPackage packagePage = new UpsPackage();
    UpsPickupService pickupServicePage = new UpsPickupService();
    Map<String, String> originData = TestContext.getDataByFileName("upsOrigin");
    Map<String, String> destinationData = TestContext.getDataByFileName("upsDestination");
    Map<String, String> packageData = TestContext.getDataByFileName("upsPackage");

    @And("I go to Create a Shipment")
    public void iGoToCreateAShipment() {
        new UpsRegion().gotoUsEnglish();
        landingPage.closeCookiesPopup();
        landingPage.clickShipMenu();
    }

    @When("I fill out origin shipment fields")
    public void iFillOutOriginShipmentFields() {
        originPage.selectCountry(originData.get("country"));
        originPage.fillName(originData.get("name"));
        originPage.fillAddress(originData.get("address"));
        originPage.fillEmail(originData.get("email"));
        originPage.fillPhone(originData.get("phone"));
    }

    @Then("I verify origin shipment fields submitted")
    public void iVerifyOriginShipmentFieldsSubmitted() {
        String result = destinationPage.getOriginSummaryFields();
        assertThat(result).contains(
                originData.get("countryResult"),
                originData.get("name"),
                originData.get("address"),
                originData.get("email"),
                originData.get("phone")
        );
    }

    @When("I fill out destination shipment fields")
    public void iFillOutDestinationShipmentFields() {
        destinationPage.selectCountry(destinationData.get("country"));
        destinationPage.fillName(destinationData.get("name"));
        destinationPage.fillAddress(destinationData.get("address"));
    }


    @And("I submit the shipment form")
    public void iSubmitTheShipmentForm() {
        shippingControls.clickSubmit();
    }

    @And("I {string} residential address")
    public void iResidentialAddress(String action) {
        if (action.equals("confirm")) {
            shippingControls.enableResidential();
        } else {
            shippingControls.disableResidential();
        }
        shippingControls.clickConfirmAddress();
    }

    @And("I set packaging type and weight")
    public void iSetPackagingTypeAndWeight() throws InterruptedException {
        packagePage.selectPackageByText(packageData.get("packageType"));
        // bug # workaround
        Thread.sleep(1000);
        packagePage.fillWeight(packageData.get("packageWeight"));
    }

    @Then("I verify total charges appear")
    public void iVerifyTotalChargesAppear() {
        assertThat(shippingControls.areTotalChargesAppear()).isTrue();
    }

    @And("I select cheapest delivery option")
    public void iSelectCheapestDeliveryOption() {
        pickupServicePage.selectCheapestPrice();
    }

    @And("I set description and check Saturday Delivery type if available")
    public void iSetDescriptionAndCheckSaturdayDeliveryTypeIfAvailable() {
        pickupServicePage.fillShipmentDescription("parfume");

    }

    @And("I check Deliver only to receiver's address")
    public void iCheckDeliverOnlyToReceiverSAddress() throws InterruptedException {
        pickupServicePage.checkDeliveryOnlyReciverAddress();
        Thread.sleep(3000);
    }

    @Then("I verify total charges changed")
    public void iVerifyTotalChargesChanged() {
        assertThat(pickupServicePage.areTotalChargesChangedAppear()).isTrue();
    }

    @And("I select Paypal payment type")
    public void iSelectPaypalPaymentType() {
        pickupServicePage.checkPayPall();
    }

    @And("I review the shipment form")
    public void iReviewTheShipmentForm() {
        pickupServicePage.clickReviewButton();
    }

    @Then("I review all recorded details on the review page")
    public void iReviewAllRecordedDetailsOnTheReviewPage() {
        pickupServicePage.getShipFrom();
        pickupServicePage.getShipTo();
        pickupServicePage.getPackageInfo();
        pickupServicePage.getShippingService();
        pickupServicePage.getAdditionalOptions();
        pickupServicePage.getPayment();
    }

    @And("I cancel the shipment form")
    public void iCancelTheShipmentForm() {
        pickupServicePage.clickCancelShipmentButton();

    }

    @Then("I verify shipment form is reset")
    public void iVerifyShipmentFormIsReset() {
        pickupServicePage.clickShipmentWarningYes();
    }
}