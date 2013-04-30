package itunes.handler;

import itunes.handler.constants.LibraryProperty;
import itunes.handler.constants.TagType;
import itunes.parser.Tag;

import com.google.inject.Inject;

/**
 * Base handler for library.xml file
 */
public class ITunesTagHandler {

    private final TrackTagHandler trackTagHandler;

    private TagHandler tagHandler;

    @Inject
    public ITunesTagHandler(TrackTagHandler trackTagHandler,
                            LibraryTagHandler libraryTagHandler) {
        this.trackTagHandler = trackTagHandler;
        tagHandler = libraryTagHandler;
    }


    public void handleTag(Tag tag) {
        if (tag.getName().equals(TagType.KEY) && tag.getInnerText().equals(LibraryProperty.TRACKS.getName())) {
            tagHandler = trackTagHandler;
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
            tagHandler.key(tag.getInnerText());
        } else {
            tagHandler.value(tag);
        }
    }

}
