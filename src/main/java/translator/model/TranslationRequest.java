package translator.model;

public class TranslationRequest {

    private String language;
    private String key;
    private String translation;

    public TranslationRequest(String language, String key, String translation) {
        this.language = language;
        this.key = key;
        this.translation = translation;
    }

    public String getLanguage() {
        return language;
    }

    public String getKey() {
        return key;
    }

    public String getTranslation() {
        return translation;
    }
}
