package itunes.handler.constants;


import java.util.HashMap;
import java.util.Map;

/*
    example library properties:
    <key>Major Version</key><integer>1</integer>
    <key>Minor Version</key><integer>1</integer>
    <key>Application Version</key><string>7.0.1</string>
    <key>Features</key><integer>1</integer>
    <key>Show Content Ratings</key><true/>
    <key>Music Folder</key><string>file://localhost/E:/itunes/</string>
    <key>Library Persistent ID</key><string>4EC2FAC25152379E</string>
 */
public enum LibraryProperty {

    MAJOR_VERSION("Major Version"),
    MINOR_VERSION("Minor Version"),
    APPLICATION_VERSION("Application Version"),
    FEATURES("Features"),
    SHOW_CONTENT_RATINGS("Show Content Ratings"),
    MUSIC_FOLDER("Music Folder"),
    LIBRARY_PERSISTENT_ID("Library Persistent ID"),
    TRACKS("Tracks"),
    NO_PROPERTY("NO PROPERTY");

    private static final Map<String, LibraryProperty> properties = new HashMap<String, LibraryProperty>();

    static {
        for (LibraryProperty property : LibraryProperty.values()) {
            properties.put(property.name, property);
        }
    }

    private final String name;

    private LibraryProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LibraryProperty get(String name) {
        LibraryProperty libraryProperty = properties.get(name);
        if (libraryProperty == null) {
            libraryProperty = NO_PROPERTY;
            System.out.println("Unsupported Itunes Library Property: " + name);
        }
        return libraryProperty;
    }
}
