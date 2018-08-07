package translator.model;

import com.intellij.openapi.vfs.VirtualFile;

public abstract class TranslationFile {

    private VirtualFile virtualFile;

    public TranslationFile(VirtualFile virtualFile) {
        this.virtualFile = virtualFile;
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    public String getIsoCode() {
        return virtualFile.getNameWithoutExtension().substring(0, 2);
    }
}
