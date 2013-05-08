package ru.bcc.airstage.itunes.parser;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;

import javax.xml.parsers.SAXParser;
import java.io.File;

/**
 * Itunes library SAX parser
 */
public class ITunesLibraryParser {

    private static final Logger log = Logger.getLogger(ITunesLibraryParser.class);

    @Inject
    private LibraryXmlTagHandler libraryXmlTagHandler;

    @Inject
    private SAXParser sp;

    public void parse(@NotNull String itunesLibraryFilePath) {
        File file = new File(itunesLibraryFilePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + itunesLibraryFilePath + " doesn't exist");
        }
        log.info("Start parsing...");
        try {
            sp.parse(file, libraryXmlTagHandler);
        } catch (IllegalStateException e) {
            log.info("Done");
        } catch (Exception e) {
            log.error("Error in parsing", e);
            throw new RuntimeException(e);
        }
    }

}
