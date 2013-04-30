package itunes.parser;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.xml.parsers.SAXParser;
import java.io.File;

/**
 * Itunes library parser
 */
public class ITunesLibraryParser {

    @Inject
    private LibraryXmlTagHandler libraryXmlTagHandler;

    @Inject
    private SAXParser sp;

    public void parse(@NotNull String itunesLibraryFilePath) {
        try {
            sp.parse(new File(itunesLibraryFilePath), libraryXmlTagHandler);
        } catch (Exception e) {
            // omit exception
        }
    }

}
