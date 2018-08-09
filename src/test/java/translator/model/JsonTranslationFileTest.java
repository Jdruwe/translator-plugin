package translator.model;

import org.junit.Test;
import translator.exception.InvalidFileException;
import translator.exception.InvalidJsonException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonTranslationFileTest {

    @Test
    public void addTranslation_keyAndValue_updatedJson() throws IOException, InvalidFileException, InvalidJsonException {

        String json = "{ \"account.account\" : \"Account\", \"account.activation.already.exists\" : \"Gelieve je aan te melden via het accountmenu.\" }";
        JsonTranslationFile jsonTranslationFile = new JsonTranslationFile(new MockVirtualFile(json, true));
        String newContent = jsonTranslationFile.addTranslation("this.is.a.key", "This is a translation");
        assertEquals("{\n" +
                "  \"account.account\": \"Account\",\n" +
                "  \"account.activation.already.exists\": \"Gelieve je aan te melden via het accountmenu.\",\n" +
                "  \"this.is.a.key\": \"This is a translation\"\n" +
                "}", newContent);
    }

}