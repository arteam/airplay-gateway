package itunes;

import itunes.data.ITunesLibrary;
import itunes.handler.ITunesTagHandler;
import itunes.parser.LibraryXmlParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class ITunesLibraryParser {

    public ITunesLibrary parseLibrary(String itunesLibraryFilePath) {
        // Create a JAXP "parser factory" for creating SAX parsers
        SAXParserFactory spf = SAXParserFactory.newInstance();

        // Configure the parser factory for the type of parsers we require
        spf.setValidating(false);

        //Parsing the XML document using the parser
        LibraryXmlParser libraryXmlParser = new LibraryXmlParser();
        try {
            // Now use the parser factory to create a SAXParser object
            SAXParser sp = spf.newSAXParser();
            sp.parse(new File(itunesLibraryFilePath), libraryXmlParser);
        } catch (Exception e) {
        }

        return libraryXmlParser.getLibrary();
    }
}
