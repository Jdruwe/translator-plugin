package translator.extraction;

import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidOriginException;
import translator.model.TranslationFile;

import java.util.ArrayList;
import java.util.List;

public class TranslationFileExtractor {

    private VirtualFile origin;

    public TranslationFileExtractor(VirtualFile origin) throws InvalidOriginException {
        this.origin = origin;
        validateOrigin();
    }

    private void validateOrigin() throws InvalidOriginException {
        if (!this.origin.isDirectory()) {
            throw new InvalidOriginException();
        }
    }

    public List<TranslationFile> getTranslationFiles(VirtualFile virtualFile) {
        return new ArrayList<>();
    }

}
