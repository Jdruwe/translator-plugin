package translator.model;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonTranslationFileTest {

    @Test
    public void addTranslation_keyAndValue_updatedJson() throws IOException {
        
        String json = "{ \"account.account\" : \"Account\", \"account.activation.already.exists\" : \"Gelieve je aan te melden via het accountmenu.\" }";
        JsonTranslationFile jsonTranslationFile = new JsonTranslationFile(new MockVirtualFile(json));
        String newContent = jsonTranslationFile.addTranslation("this.is.a.key", "This is a translation");
        assertEquals("{\"account.account\":\"Account\",\"account.activation.already.exists\":\"Gelieve je aan te melden via het accountmenu.\",\"this.is.a.key\":\"This is a translation\"}", newContent);
    }

}