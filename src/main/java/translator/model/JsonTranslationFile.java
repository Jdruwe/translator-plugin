package translator.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
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
        final ObjectMapper mapper = buildMapper(alphabetize);
        final Object obj = insertTranslation(key, value, mapper);
        final String s = mapper.writeValueAsString(obj);
        return removeLeadingSpace(s);
    }

    private Object insertTranslation(String key, String value, ObjectMapper mapper) throws IOException {
        JsonNode jsonNode = mapper.readTree(getContent());
        ((ObjectNode) jsonNode).put(key, value);
        return mapper.treeToValue(jsonNode, Object.class);
    }

    @NotNull
    private String removeLeadingSpace(String s) {
        return s.replaceAll("\" :", "\":");
    }

    @NotNull
    private ObjectMapper buildMapper(boolean alphabetize) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        if (alphabetize) {
            mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        }
        return mapper;
    }

    private void validateFile() throws InvalidFileException {
        if (!getVirtualFile().isValid()) {
            throw new InvalidFileException();
        }
    }
}