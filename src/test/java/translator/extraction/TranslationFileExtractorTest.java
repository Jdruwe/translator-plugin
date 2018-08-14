package translator.extraction;

import com.intellij.mock.MockVirtualFile;
import org.junit.Test;
import translator.exception.InvalidOriginException;
import translator.exception.NoTranslationFilesFoundException;
import translator.model.TranslationFile;

import java.util.List;

import static org.junit.Assert.*;

public class TranslationFileExtractorTest {

    @Test(expected = NoTranslationFilesFoundException.class)
    public void getTranslationFiles_emptyDirectory_noTranslationFilesFoundException() throws InvalidOriginException, NoTranslationFilesFoundException {
        MockVirtualFile mockDirectory = new MockVirtualFile(true, "translationsDirectory");
        TranslationFileExtractor translationFileExtractor = new TranslationFileExtractor(mockDirectory);
        translationFileExtractor.getTranslationFiles();
    }

    @Test(expected = InvalidOriginException.class)
    public void getTranslationFiles_emptyDirectory_d() throws InvalidOriginException, NoTranslationFilesFoundException {
        MockVirtualFile mockFile = new MockVirtualFile("file");
        TranslationFileExtractor translationFileExtractor = new TranslationFileExtractor(mockFile);
        translationFileExtractor.getTranslationFiles();
    }

    @Test
    public void getTranslationFiles_directoryWithFiles_onlyTranslationReturned() throws InvalidOriginException, NoTranslationFilesFoundException {
        MockVirtualFile translationNl = new MockVirtualFile("nl.json");
        MockVirtualFile translationNlNl = new MockVirtualFile("nl_nl.json");
        MockVirtualFile translationFr = new MockVirtualFile("fr.json");
        MockVirtualFile translationEn = new MockVirtualFile("en.json");
        MockVirtualFile translationDe = new MockVirtualFile("de.json");
        MockVirtualFile otherFile = new MockVirtualFile("other.txt");

        MockVirtualFile mockDirectory = new MockVirtualFile(true, "translationsDirectory");
        mockDirectory.addChild(translationNl);
        mockDirectory.addChild(translationNlNl);
        mockDirectory.addChild(translationFr);
        mockDirectory.addChild(translationEn);
        mockDirectory.addChild(translationDe);
        mockDirectory.addChild(otherFile);

        TranslationFileExtractor translationFileExtractor = new TranslationFileExtractor(mockDirectory);
        List<TranslationFile> translationFiles = translationFileExtractor.getTranslationFiles();
        assertEquals(5, translationFiles.size());
    }

}