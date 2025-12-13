package org.selenide.examples.cucumber;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Map;

import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchStepDefinitions {
  static boolean primerRetraso = false;	
	
  @Given("an open browser with google.com")
  public void openGoogleSearch() {
    Configuration.reportsFolder = "target/surefire-reports";
	Configuration.timeout = 4000;

	ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("prefs", Map.of("intl.accept_languages", "es_ES"));
		chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
		chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
	Configuration.browserCapabilities = chromeOptions;
	
	open("https://google.com/");
	
	// RETRASO PARA QUE ME DÉ TIEMPO A SUBIR LA PANTALLA AL OTRO MONITOR
	if (!primerRetraso)
	{
		primerRetraso = true;
		try { Thread.sleep(5000); } catch(Exception e) {}
	}
  }

  @When("a keyword {string} is entered in input field")
  public void enterKeyword(String keyword) {
    $(By.name("q")).val(keyword).pressEnter();
	screenshot(keyword);
  }

  @Then("at least top {int} matches should be shown")
  public void topTenMatchesShouldBeShown(int resultsCount) {
    $$(".BToiNc").shouldHave(sizeGreaterThanOrEqual(resultsCount));
  }

  @Then("the first one should contain {string}")
  public void theFirstOneShouldContainKeyword(String expectedText) {
    $(".BToiNc").shouldHave(text(expectedText));
  }
}
