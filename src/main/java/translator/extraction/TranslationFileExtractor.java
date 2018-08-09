package translator.extraction;

import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidOriginException;
import translator.exception.NoTranslationFilesFoundException;
import translator.model.JsonTranslationFile;
import translator.model.TranslationFile;
import translator.util.IsoUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class TranslationFileExtractor {

    private static final String EXTENSION_JSON = "json";

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

    public List<TranslationFile> getTranslationFiles() throws NoTranslationFilesFoundException {

        List<TranslationFile> files = Arrays.stream(origin.getChildren())
                .filter(VirtualFile::isValid)
                .filter(isI18n())
                .map(this::convertFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (files.isEmpty()) {
            throw new NoTranslationFilesFoundException();
        } else {
            return files;
        }
    }

    private Predicate<VirtualFile> isI18n() {
        return file -> IsoUtil.isValidISOLanguage(file.getNameWithoutExtension().substring(0, 2));
    }

    private TranslationFile convertFile(VirtualFile file) {

        final String extension = file.getExtension();

        if (extension != null) {
            switch (extension) {
                case EXTENSION_JSON:
                    return new JsonTranslationFile(file);
            }
        }

        return null;
    }


}
