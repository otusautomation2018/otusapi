package net.dictionary.yandex;

import io.restassured.RestAssured;
import net.dictionary.api.client.EndpointURI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class DictionaryYandexTests {
    private static final String apiKey = PropertyReader
            .getPropertyFromFile("properties/yandexDictionary.properties", "api.key");
    private static final String LANGUAGE_FORMAT = "ru-en";

    @BeforeMethod
    public void start(){
        RestAssured.baseURI = "https://dictionary.yandex.net/api/v1/dicservice.json";
        RestAssured.useRelaxedHTTPSValidation();
    }

    @DataProvider
    public Object[][] dictionary(){
        return new Object[][] {
                {"лошадь","конь"}
        };
    }

    @Test(dataProvider = "dictionary")
    public void lookupTest(String text, String answer){
        String pattern = "?key=%s&text=%s&lang=%s";
        String path = String.format(pattern, apiKey, text, LANGUAGE_FORMAT);

        given()
                .header("User-Agent", "Mozilla...")
                .header("JWT", "jwt_token")
                .when()
                .get(EndpointURI.LOOKUP.addPath(path))
                .then()
                .statusCode(200)
                .body("def[0].tr[0].mean[0].text", equalTo(answer));

    }

    @Test
    public void getLangsTest(){
        String pattern = "?key=%s";
        String[] languages = {"be-be","be-ru","bg-ru","cs-en","cs-ru","da-en","da-ru","de-de","de-en","de-ru",
                "de-tr","el-en","el-ru","en-cs","en-da","en-de","en-el","en-en","en-es","en-et",
                "en-fi","en-fr","en-it","en-lt","en-lv","en-nl","en-no","en-pt","en-ru","en-sk",
                "en-sv","en-tr","en-uk","es-en","es-es","es-ru","et-en","et-ru","fi-en","fi-ru",
                "fi-fi","fr-fr","fr-en","fr-ru","hu-hu","hu-ru","it-en","it-it","it-ru","lt-en",
                "lt-lt","lt-ru","lv-en","lv-ru","mhr-ru","mrj-ru","nl-en","nl-ru","no-en","no-ru",
                "pl-ru","pt-en","pt-ru","ru-be","ru-bg","ru-cs","ru-da","ru-de","ru-el","ru-en",
                "ru-es","ru-et","ru-fi","ru-fr","ru-hu","ru-it","ru-lt","ru-lv","ru-mhr","ru-mrj",
                "ru-nl","ru-no","ru-pl","ru-pt","ru-ru","ru-sk","ru-sv","ru-tr","ru-tt","ru-uk",
                "sk-en","sk-ru","sv-en","sv-ru","tr-de","tr-en","tr-ru","tt-ru","uk-en","uk-ru","uk-uk"};
        String path = String.format(pattern, apiKey);
        String[] response = given()
                .header("User-Agent", "Mozilla...")
                .header("JWT", "jwt_token")
                .when()
                .get(EndpointURI.GET_LANGS.addPath(path)).andReturn().body().print().replaceAll("[\"\\[\\]]", "")
                .split(",");

        Assert.assertEquals(response, languages);

    }
}
