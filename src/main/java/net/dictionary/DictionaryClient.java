package net.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DictionaryClient {

    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20180711T123306Z.b012f6b5034cc719.aff4999263480fef925a97d8863cbccc7ab40f50";

    public String sendGet(String textForTranslation, String languageFormat) {
        DictionarySchema dictionaryClient = new DictionarySchema();
        String urlPath = dictionaryClient.getPathFormated(API_KEY, textForTranslation, languageFormat);
        String urlFinal = API_URL + urlPath;

        String result = null;
        try {
            URL url = new URL(urlFinal);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer response = new StringBuffer();
            String inputLine = null;

            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            result = response.toString();

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private class DictionarySchema {
        private final static String KEY = "key";
        private final static String TEXT = "text";
        private final static String LANG = "lang";

        protected String getPathFormated(String key, String text, String languageFormat) {
            return String.format("?key=%s&text=%s&lang=%s", key, text, languageFormat);
        }
    }
}
