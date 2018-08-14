package translator.model;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidFileException;
import translator.exception.InvalidJsonException;

import java.io.IOException;

public class JsonTranslationFile extends TranslationFile {

    public JsonTranslationFile(VirtualFile virtualFile) {
        super(virtualFile);
    }

    @Override
    public String addTranslation(String key, String value, boolean alphabetize) throws IOException, InvalidFileException, InvalidJsonException {

        validateFile();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        if (alphabetize) {
            mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        }

        JsonNode jsonNode = mapper.readTree(getContent());
        ((ObjectNode) jsonNode).put(key, value);
        final Object obj = mapper.treeToValue(jsonNode, Object.class);
        String s = mapper.writeValueAsString(obj);

        return s.replaceAll("\" :", "\":");

//
//        try {
//            JsonObject jsonObject = parsed.getAsJsonObject();
//            jsonObject.addProperty(key, value);
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            return gson.toJson(jsonObject);
//        } catch (IllegalStateException e) {
//            throw new InvalidJsonException(getVirtualFile().getName() + " is not a valid JSON file.");
//        }
    }

    private void validateFile() throws InvalidFileException {
        if (!getVirtualFile().isValid()) {
            throw new InvalidFileException();
        }
    }
}