package itunes.handler;

import itunes.data.ITunesLibrary;
import itunes.handler.constants.LibraryProperty;
import itunes.parser.Tag;

public class LibraryPropertyTagHandler implements PropertyTagHandler {

    private final ITunesLibrary library;
    private LibraryProperty currentProperty = LibraryProperty.NO_PROPERTY;

    public LibraryPropertyTagHandler(ITunesLibrary library) {
        this.library = library;
    }

    @Override
    public void key(String propertyName) {
        currentProperty = LibraryProperty.get(propertyName);
    }

    @Override
    public void value(Tag propertyValue) {
        try {
            if (currentProperty.equals(LibraryProperty.NO_PROPERTY)) {
                //don't do anything...
            } else if (currentProperty.equals(LibraryProperty.MAJOR_VERSION)) {
                //example property: <key>Major Version</key><integer>1</integer>
                library.setMajorVersion(DataParser.parseInteger(LibraryProperty.MAJOR_VERSION, propertyValue));
            } else if (currentProperty.equals(LibraryProperty.MINOR_VERSION)) {
                //example property: <key>Minor Version</key><integer>1</integer>
                library.setMinorVersion(DataParser.parseInteger(LibraryProperty.MINOR_VERSION, propertyValue));
            } else if (currentProperty.equals(LibraryProperty.APPLICATION_VERSION)) {
                //example property: <key>Application Version</key><string>7.0.1</string>
                library.setApplicationVersion(DataParser.parseString(LibraryProperty.APPLICATION_VERSION, propertyValue));
            } else if (currentProperty.equals(LibraryProperty.FEATURES)) {
                //example property: <key>Features</key><integer>1</integer>
                library.setFeatures(DataParser.parseInteger(LibraryProperty.FEATURES, propertyValue));
            } else if (currentProperty.equals(LibraryProperty.SHOW_CONTENT_RATINGS)) {
                //example property: <key>Show Content Ratings</key><true/>
                library.setShowContentRatings(DataParser.parseBoolean(LibraryProperty.SHOW_CONTENT_RATINGS, propertyValue));
            } else if (currentProperty.equals(LibraryProperty.MUSIC_FOLDER)) {
                //example property: <key>Music Folder</key><string>file://localhost/E:/itunes/</string>
                library.setMusicFolder(DataParser.parseString(LibraryProperty.MUSIC_FOLDER, propertyValue));
            } else if (currentProperty.equals(LibraryProperty.LIBRARY_PERSISTENT_ID)) {
                //example property: <key>Library Persistent ID</key><string>4EC2FAC25152379E</string>
                library.setLibraryPersistentID(DataParser.parseString(LibraryProperty.LIBRARY_PERSISTENT_ID, propertyValue));
            } else {
                System.out.println("Supported Itunes Library Property Was Not Handled Correctly: " + currentProperty);
            }
        } catch (Exception e) {
            System.err.println("Error occured during library property parsing: " + e.getMessage());
        }

        //now that we've handled the data for this property, we need to reset ourselves back to "no property"
        currentProperty = LibraryProperty.NO_PROPERTY;
    }

}
