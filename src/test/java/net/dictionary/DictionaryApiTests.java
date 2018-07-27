package net.dictionary;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DictionaryApiTests {

    @Test
    public void firstTest(){
        DictionaryClient dictionaryClient = new DictionaryClient();
        String translation = dictionaryClient.sendGet("Привет", "ru-en");
//        assertThat(translation, equalTo("Hi"));
        Assert.assertEquals(translation, "Hi");

    }
}
