package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class UpsPackage extends Page {

    @FindBy(id = "nbsPackagePackagingTypeDropdown0")
    private WebElement type;

    @FindBy(id = "nbsPackagePackageWeightField0")
    private WebElement weight;

    public void selectPackageByText(String text) {
        new Select(type).selectByVisibleText(text);
    }
    public void fillWeight(String value) {
        weight.sendKeys(value);
    }
}