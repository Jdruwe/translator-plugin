package translator.model;

import com.google.gson.*;
import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidFileException;

import java.io.IOException;

public class JsonTranslationFile extends TranslationFile {

    public JsonTranslationFile(VirtualFile virtualFile) {
        super(virtualFile);
    }

    @Override
    public String addTranslation(String key, String value) throws IOException, InvalidFileException {

        if (!getVirtualFile().isValid()) {
            throw new InvalidFileException();
        }

        byte[] currentContent = getContent();
        JsonParser parser = new JsonParser();
        JsonElement parsed = parser.parse(new String(currentContent));
        JsonObject jsonObject = parsed.getAsJsonObject();
        jsonObject.addProperty(key, value);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}