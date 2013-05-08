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

package ru.bcc.airstage.itunes.parser;

import ru.bcc.airstage.itunes.handler.ITunesTagHandler;
import ru.bcc.airstage.itunes.handler.constants.TagType;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.inject.Inject;


/**
 * The LibraryXmlTagHandler uses a SAX XML parser to parse the given
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
public class LibraryXmlTagHandler extends DefaultHandler {

    private static final String PLAYLISTS = "Playlists";

    private final ITunesTagHandler tagHandler;

    @Inject
    public LibraryXmlTagHandler(ITunesTagHandler tagHandler) {
        this.tagHandler = tagHandler;
    }

    @NotNull
    private Tag currentTag;

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        currentTag = new Tag(TagType.get(qName));
        //currentTag.setName();
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        tagHandler.handleTag(currentTag);
    }

    @Override
    public void characters(char[] buffer, int start, int length) {
        // put char data into the tag
        currentTag.addInnerText(buffer, start, length);
        if (currentTag.getInnerText().equals(PLAYLISTS) &&
                currentTag.getName().equals(TagType.KEY)) {
            // We don't parse playlists
            throw new IllegalStateException("Stop parsing...");
        }
    }

}
