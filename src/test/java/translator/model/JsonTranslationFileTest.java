package translator.model;

import com.intellij.mock.MockVirtualFile;
import org.junit.Test;
import translator.exception.InvalidFileException;
import translator.exception.InvalidJsonException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonTranslationFileTest {

    @Test
    public void addTranslation_existing_updatedJson() throws IOException, InvalidFileException, InvalidJsonException {

        String json = "{ \"account.account\" : \"Account\", \"account.activation.already.exists\" : \"Gelieve je aan te melden via het accountmenu.\" }";
        JsonTranslationFile jsonTranslationFile = new JsonTranslationFile(buildMockFile(json));
        String newContent = jsonTranslationFile.addTranslation("account.account", "Account new translation", false);
        assertEquals("{\n" +
                "  \"account.account\": \"Account new translation\",\n" +
                "  \"account.activation.already.exists\": \"Gelieve je aan te melden via het accountmenu.\"\n" +
                "}", newContent);
    }

    @Test
    public void addTranslation_nonExistingAlphabetizeRequested_updatedJson() throws IOException, InvalidFileException, InvalidJsonException {

        String json = "{\"credit: s\": 10, \"account.account\": \"account\"}";
        JsonTranslationFile jsonTranslationFile = new JsonTranslationFile(buildMockFile(json));
        String newContent = jsonTranslationFile.addTranslation("between", "This translation should be added in between", true);
        assertEquals("{\n" +
                "  \"account.account\": \"account\",\n" +
                "  \"between\": \"This translation should be added in between\",\n" +
                "  \"credit: s\": 10\n" +
                "}", newContent);
    }

    @Test
    public void addTranslation_nonExisting_updatedJson() throws IOException, InvalidFileException, InvalidJsonException {

        String json = "{\"credit: s\": 10, \"account.account\": \"account\"}";
        JsonTranslationFile jsonTranslationFile = new JsonTranslationFile(buildMockFile(json));
        String newContent = jsonTranslationFile.addTranslation("between", "This translation should be added in between", false);
        assertEquals("{\n" +
                "  \"credit: s\": 10,\n" +
                "  \"account.account\": \"account\",\n" +
                "  \"between\": \"This translation should be added in between\"\n" +
                "}", newContent);
    }

    private MockVirtualFile buildMockFile(String content) {
        return new MockVirtualFile("mock.json", content);
    }

}