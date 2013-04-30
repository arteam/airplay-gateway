package itunes.handler;

import itunes.data.ITunesLibrary;
import itunes.handler.constants.LibraryProperty;
import itunes.handler.constants.TagType;
import itunes.parser.Tag;

public class ITunesTagHandler {

    private final PropertyTagHandler trackPropertyTagHandler;

    private PropertyTagHandler propertyTagHandler;

    public ITunesTagHandler(ITunesLibrary library) {
        trackPropertyTagHandler = new TrackPropertyTagHandler(library);
        propertyTagHandler = new LibraryPropertyTagHandler(library);
    }

    public void handleTag(Tag tag) {
        if (tag.getName().equals(TagType.KEY) && tag.getInnerText().equals(LibraryProperty.TRACKS.getName())) {
            propertyTagHandler = trackPropertyTagHandler;
        }

		/*
         * All properties in the library xml file are layed out with a property
		 * name surrounded by "key" tags, and then the value follows.. for example:
		 * 
		 * 	<key>Artist</key><string>A Perfect Circle</string>
		 * 
		 * So if this is the '<key>something</key>' bit, we call the handler's 
		 * handlePropertyChange() method, otherwise we call the handlePropertyValue() method. 
		 * 
		 */
        if (tag.getName().equals(TagType.KEY)) {
            propertyTagHandler.key(tag.getInnerText());
        } else {
            propertyTagHandler.value(tag);
        }
    }

}
