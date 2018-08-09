package translator.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import translator.exception.TranslationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TranslateUtil {

    public static String translate(String text, String sourceLanguage, String targetLanguage) throws TranslationException {

        try {

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

                String url = buildUrl(text, sourceLanguage, targetLanguage);
                HttpResponse response = httpClient.execute(new HttpGet(url));
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    String responseString = getResponseString(response);
                    return filterTranslation(responseString);
                } else {
                    throw new TranslationException();
                }
            }

        } catch (Exception e) {
            throw new TranslationException();
        }
    }

    private static String buildUrl(String text, String sourceLanguage, String targetLanguage) throws UnsupportedEncodingException {

        String textEncoded = URLEncoder.encode(text, "utf-8");
        return "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&q=" + textEncoded;
    }

    private static String getResponseString(HttpResponse response) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        String responseString = out.toString();
        out.close();
        return responseString;
    }

    private static String filterTranslation(String responseString) {

        responseString = responseString.replace("[", "");
        responseString = responseString.replace("]", "");
        responseString = responseString.substring(1);
        int plusIndex = responseString.indexOf('"');
        responseString = responseString.substring(0, plusIndex);
        return responseString;

    }
}
