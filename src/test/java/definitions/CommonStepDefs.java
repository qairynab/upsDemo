package definitions;

import io.cucumber.java.en.Given;


import pages.UpsRegion;


public class CommonStepDefs {
    @Given("I go to {string} page oop")
    public void iGoToPageOop(String page) {
        switch (page) {
            case "ups":
                new UpsRegion().open();
                break;
            default:
                throw new Error("Unsupported page " + page);
        }
    }
}
