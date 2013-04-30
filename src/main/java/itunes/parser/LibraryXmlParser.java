/*
 * Source code from iPlaylist Copier is (C) Jason Baker 2006
 * 
 * Please make an effort to document your additions to this source code file,
 * so future developers can give you credit where due.
 * 
 * Please include this copyright information in these source files when
 * redistributing source code. 
 *
 * Please make note of this copyright information in documentation for
 * binary redistributions that contain any or all of the source code. 
 *
 * If you are having any trouble understanding the meaning of this code
 * email jason directly at jason@onejasonforsale.com.
 *
 * Thanks, and happy coding!
 */

package itunes.parser;

import itunes.data.ITunesLibrary;
import itunes.handler.ITunesTagHandler;
import itunes.handler.constants.TagType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * The LibraryXmlParser uses a SAX XML parser to parse the given
 * file into tags.
 * For example in a typical XML format you might expect track information
 * to be formatted like so:
 * <p/>
 * <Track>
 * <Name>Come Together</Name>
 * <Artist>The Beatles</Artist>
 * </Track>
 * <p/>
 * But Apple's format for the same information is a bit different:
 * <p/>
 * <p/>
 * <key>Track ID</key><integer>401</integer>
 * <key>Name</key><string>Come Together</string>
 * <key>Artist</key><string>The Beatles</string>
 * <p/>
 * So for our purposes we can simply pass the latest parsed tag and its
 * inner text up to whatever tag handling mechanism we've implemented.
 */
public class LibraryXmlParser extends DefaultHandler {

    private ITunesLibrary library = new ITunesLibrary();
    private ITunesTagHandler tagHandler = new ITunesTagHandler(library);
    private Tag currentTag = new Tag();


    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        currentTag.clear();
        currentTag.setName(TagType.get(qName));
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        tagHandler.handleTag(currentTag);
    }

    @Override
    public void characters(char[] buffer, int start, int length) {
        //put char data into the tag
        currentTag.addInnerText(buffer, start, length);
        if (currentTag.getInnerText().equals("Playlists") &&
                currentTag.getName().equals(TagType.KEY)) {
            throw new IllegalStateException("Stop parsing...");
        }
    }

    public ITunesLibrary getLibrary() {
        return library;
    }
}
