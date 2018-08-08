package translator.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import translator.exception.TranslationException;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

public class TranslateUtil {

    public static String translate(String text, String sourceLanguage, String targetLanguage) throws TranslationException {

        String returnString;

        try {

            String textEncoded = URLEncoder.encode(text, "utf-8");
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&q=" + textEncoded;
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                out.close();

                String aJsonString = responseString;
                aJsonString = aJsonString.replace("[", "");
                aJsonString = aJsonString.replace("]", "");
                aJsonString = aJsonString.substring(1);
                int plusIndex = aJsonString.indexOf('"');
                aJsonString = aJsonString.substring(0, plusIndex);
                returnString = aJsonString;

            } else {
                response.getEntity().getContent().close();
                throw new TranslationException();
            }
        } catch (Exception e) {
            throw new TranslationException();
        }

        return returnString;
    }

}
