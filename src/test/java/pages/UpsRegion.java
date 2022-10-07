package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpsRegion extends Page {

    public UpsRegion() {
        url = "https://www.ups.com";
    }

    @FindBy(xpath = "//a[text()='North America']")
    private WebElement usMenuAccordion;

    @FindBy(xpath = "//a[text()='United States - English']")
    private WebElement usEnSubMenu;

    @FindBy(xpath = "//a[text()='Europe']")
    private WebElement euMenuAccordion;

    @FindBy(xpath = "//a[text()='Deutschland - Deutsch']")
    private WebElement deDESubMenu;

    public void gotoUsEnglish() {
        if (usEnSubMenu.isDisplayed()) {
            usEnSubMenu.click();
        } else {
            usMenuAccordion.click();
            usEnSubMenu.click();
        }
    }
    public void gotoDeDeutsch() {
        if (deDESubMenu.isDisplayed()) {
            deDESubMenu.click();
        } else {
            euMenuAccordion.click();
            deDESubMenu.click();
        }
    }
}
