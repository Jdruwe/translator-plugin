package translator.model;

import com.intellij.openapi.vfs.VirtualFile;
import translator.exception.InvalidFileException;

import java.io.IOException;

public abstract class TranslationFile {

    private VirtualFile virtualFile;

    TranslationFile(VirtualFile virtualFile) {
        this.virtualFile = virtualFile;
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    public String getIsoCode() {
        return virtualFile.getNameWithoutExtension().substring(0, 2);
    }

    byte[] getContent() throws IOException {
        return virtualFile.contentsToByteArray();
    }

    public abstract String addTranslation(String key, String value) throws IOException, InvalidFileException;
}
