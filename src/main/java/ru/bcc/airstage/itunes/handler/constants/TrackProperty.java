package ru.bcc.airstage.itunes.handler.constants;

import java.util.HashMap;
import java.util.Map;

/*
example track properties:
<key>Track ID</key><integer>401</integer>
<key>Name</key><string>The Hollow</string>
<key>Artist</key><string>A Perfect Circle</string>
<key>Album</key><string>Mer de Noms</string>
<key>Genre</key><string>Progressive Rock</string>
<key>Kind</key><string>MPEG audio file</string>
<key>Size</key><integer>4299529</integer>
<key>Total Time</key><integer>179043</integer>
<key>Track Number</key><integer>1</integer>
<key>Track Count</key><integer>12</integer>
<key>Year</key><integer>2000</integer>
<key>Date Modified</key><date>2006-07-21T21:48:32Z</date>
<key>Date Added</key><date>2005-10-20T02:43:19Z</date>
<key>Bit Rate</key><integer>192</integer>
<key>Sample Rate</key><integer>44100</integer>
<key>Comments</key><string>Track 1</string>
<key>Play Count</key><integer>5</integer>
<key>Play Date</key><integer>3236345492</integer>
<key>Play Date UTC</key><date>2006-07-21T21:51:32Z</date>
<key>Persistent ID</key><string>51C977107B2FE940</string>
<key>Track Type</key><string>File</string>
<key>Location</key><string>file://localhost/E:/itunes/A%20Perfect%20Circle/Mer%20de%20Noms/01%20The%20Hollow.mp3</string>
<key>File Folder Count</key><integer>4</integer>
<key>Library Folder Count</key><integer>1</integer>
<key>Disabled</key><true/>
<key>Skip Count</key><integer>1</integer>
<key>Skip Date</key><date>2007-04-21T00:25:49Z</date>
<key>Composer</key><string>Trent Reznor</string>
<key>Album Artist</key><string>The Red Jumpsuit Apparatus</string>
<key>Artwork Count</key><integer>1</integer>
<key>Grouping</key><string>Alternative General</string>
<key>Disc Number</key><integer>1</integer>
<key>Disc Count</key><integer>2</integer>
<key>BPM</key><integer>192</integer>
 */
public enum TrackProperty {

    TRACK_ID("Track ID"),
    NAME("Name"),
    ARTIST("Artist"),
    ALBUM("Album"),
    GENRE("Genre"),
    KIND("Kind"),
    SIZE("Size"),
    TOTAL_TIME("Total Time"),
    NUMBER("Track Number"),
    COUNT("Track Count"),
    YEAR("Year"),
    DATE_MODIFIED("Date Modified"),
    DATE_ADDED("Date Added"),
    BIT_RATE("Bit Rate"),
    SAMPLE_RATE("Sample Rate"),
    COMMENTS("Comments"),
    PLAY_COUNT("Play Count"),
    PLAY_DATE("Play Date"),
    PLAY_DATE_UTC("Play Date UTC"),
    PERSISTENT_ID("Persistent ID"),
    TRACK_TYPE("Track Type"),
    LOCATION("Location"),
    FILE_FOLDER_COUNT("File Folder Count"),
    LIBRARY_FOLDER_COUNT("Library Folder Count"),
    DISABLED("Disabled"),
    SKIP_COUNT("Skip Count"),
    SKIP_DATE("Skip Date"),
    COMPOSER("Composer"),
    ALBUM_ARTIST("Album Artist"),
    ARTWORK_COUNT("Artwork Count"),
    GROUPING("Grouping"),
    DISC_NUMBER("Disc Number"),
    DISC_COUNT("Disc Count"),
    BPM("BPM"),
    SORT_ALBUM("Sort Album"),
    RELEASE_DATE("Release Date"),
    ITUNES_U("iTunesU"),
    UNPLAYED("Unplayed"),
    HAS_VIDEO("Has Video"),
    HD("HD"),
    VIDEO_WIDTH("Video Width"),
    VIDEO_HEIGHT("Video Height"),
    MOVIE("Movie"),
    NO_PROPERTY("NO PROPERTY");

    private static final Map<String, TrackProperty> properties = new HashMap<String, TrackProperty>();

    static {
        for (TrackProperty property : TrackProperty.values()) {
            properties.put(property.name, property);
        }
    }

    private final String name;

    private TrackProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TrackProperty get(String name) {
        TrackProperty trackProperty = properties.get(name);
        if (trackProperty == null) {
            trackProperty = NO_PROPERTY;
            //System.out.println("Unsupported Itunes Track Property: " + name);
        }
        return trackProperty;
    }
}
