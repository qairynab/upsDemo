package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UpsDestination extends Page {

    @FindBy(id = "origin_showSummaryAddress")
    private WebElement originSummary;
    @FindBy(id = "destination-cac_country")
    private WebElement country;
    @FindBy(id = "destination-cac_companyOrName")
    private WebElement name;
    @FindBy(id = "destination-cac_singleLineAddress")
    private WebElement address;
    @FindBy(xpath = "//ngb-typeahead-window/button")
    private List<WebElement> addressSuggestions;
    @FindBy(xpath = "//button[@id='destination-singleLineAddressEditButton']/../p")
    private WebElement selectedAddress;

    public String getOriginSummaryFields() {
        return originSummary.getText();
    }
    public void selectCountry(String text) {

        new Select(country).selectByVisibleText(text);
    }

    public void fillName(String value) {
        name.sendKeys(value);
    }
    public void fillAddress(String value) {
        address.sendKeys(value);
        addressSuggestions.get(0).click();
        assertThat(selectedAddress.getText()).isEqualTo(value);
    }


}
