package translator.model;

public class TranslationRequest {

    private String language;
    private String key;
    private String translation;
    private boolean alphabetize;

    public TranslationRequest(String language, String key, String translation, boolean alphabetize) {
        this.language = language;
        this.key = key;
        this.translation = translation;
        this.alphabetize = alphabetize;
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

    public boolean isAlphabetizeRequested() {
        return alphabetize;
    }
}
