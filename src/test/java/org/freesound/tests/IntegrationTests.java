package org.freesound.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$$;

public class IntegrationTests {

    private static final String URL = "https://freesound.org/";
    private static final String API_PATH = "apiv2/";
    private static final String QUERY_PATH = "search/text";
    private static final String API_KEY = "rcBTauOX7I4qJbioXWtd04GWp1f9lLq9RFd2rXpJ";

    private static String query = "dogs";

    @BeforeMethod
    public static void beforeTest() throws UnirestException {
        String json =  Unirest.get(URL + API_PATH + QUERY_PATH)
                .queryString("token", API_KEY)
                .queryString("query", query)
                .asString()
                .getBody();

        JsonPath.read(json, "/");
    }

    @Test
    public void restValidationTest(){
        Selenide.open("https://freesound.org/search/?q=dogs");
        $$(".sound_filename").get(0).should(Condition.text("ZOOM0004_LR.WAV"));
    }
}
