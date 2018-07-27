package net.dictionary;

import io.restassured.RestAssured;
import net.dictionary.api.client.EndpointURI;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

public class RestAssuredTest {
    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20180711T123306Z.b012f6b5034cc719.aff4999263480fef925a97d8863cbccc7ab40f50";
//    dict.1.1.20180718T054858Z.4b9ed07e9901c034.aaf8150d9f67585458294f126d8ca041fbc9e983
    @BeforeMethod
    public static void before(){
        RestAssured.baseURI = "https://translate.yandex.net/api/v1.5/tr.json";
    }

    @DataProvider
    public Object[][] dictionary(){
        return new Object[][] {
                {"Привет","Hi"},
                {"Клиент","The client"},
                {"Лекция","Lecture"}
        };
    }

    @Test(dataProvider = "dictionary")
    public void dictionaryTest(String text, String answer){
        String path = getPathFormated(API_KEY,text , "ru-en");

        RestAssured.useRelaxedHTTPSValidation();
        given()
                .header("User-Agent", "Mozilla...")
                .header("JWT", "jwt_token")
                .when()
                .get(EndpointURI.TRANSLATE.addPath(path))
                .then()
                .statusCode(200)
                .body("text", hasItem(answer))
                .body("lang", equalTo("ru-en"))
                .body("code", equalTo(200))
                .extract().body().jsonPath();
    }

    protected String getPathFormated(String key, String text, String languageFormat) {
        return String.format("?key=%s&text=%s&lang=%s", key, text, languageFormat);
    }
}
