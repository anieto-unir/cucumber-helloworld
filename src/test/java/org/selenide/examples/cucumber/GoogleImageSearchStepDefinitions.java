package org.selenide.examples.cucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GoogleImageSearchStepDefinitions {
  private String keyword;
  
  @When("click \"Images\" link")
  public void chooseImagesAsSearchTarget() {
    $(byText("Aceptar todo")).click();
	$(byText("Aceptar todo")).should(disappear);

	$(byText("Imágenes")).shouldBe(visible);
	$(byText("Imágenes")).click();
  }

  @When("enter a keyword {string} in input field")
  public void enterKeyword(String keyword) {
    this.keyword = keyword;
	$(By.name("q")).shouldBe(visible);
    $(By.name("q")).val(keyword).pressEnter();
	screenshot("Images");
  }

  @Then("at least top {int} matching images should be shown")
  public void topTenMatchedImagesShouldBeShown(int resultsCount) {
    $$(".ivg-i").shouldHave(sizeGreaterThanOrEqual(resultsCount));
  }
}
