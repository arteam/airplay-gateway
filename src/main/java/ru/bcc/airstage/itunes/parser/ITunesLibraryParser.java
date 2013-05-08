package ru.bcc.airstage.itunes.parser;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;

import javax.xml.parsers.SAXParser;
import java.io.File;

/**
 * Itunes library SAX parser
 */
public class ITunesLibraryParser {

    @Inject
    private LibraryXmlTagHandler libraryXmlTagHandler;

    @Inject
    private SAXParser sp;

    public void parse(@NotNull String itunesLibraryFilePath) {
        File file = new File(itunesLibraryFilePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + itunesLibraryFilePath + " doesn't exist");
        }
        try {
            sp.parse(file, libraryXmlTagHandler);
        } catch (Exception e) {
            // omit exceptions
        }
    }

}
