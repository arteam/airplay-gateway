package com.github.arteam.itunes.parser;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import java.io.*;

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
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            Reader reader = new InputStreamReader(fis, "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            sp.parse(is, libraryXmlTagHandler);
            fis.close();
        } catch (IllegalStateException e) {
            log.info("Done");
        } catch (Exception e) {
            log.error("Error in parsing", e);
            throw new RuntimeException(e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("I/O error", e);
                }
        }
    }

}
