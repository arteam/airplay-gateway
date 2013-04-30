package itunes.handler;

import itunes.data.ITunesLibrary;
import itunes.data.ITunesTrack;
import itunes.handler.constants.LibraryProperty;
import itunes.handler.constants.TrackProperty;
import itunes.parser.Tag;

import java.util.HashSet;
import java.util.Set;

class TrackPropertyTagHandler implements PropertyTagHandler {

    protected ITunesLibrary library = null;
    protected ITunesTrack currentTrack = null;

    protected TrackProperty currentProperty = TrackProperty.NO_PROPERTY;


    public TrackPropertyTagHandler(ITunesLibrary library) {
        this.library = library;
    }

    @Override
    public void key(String propertyName) {
        currentProperty = TrackProperty.get(propertyName);
    }

    @Override
    public void value(Tag propertyValue) {
        try {
            if (currentProperty.equals(TrackProperty.NO_PROPERTY)) {
                // don't do anything...
            } else if (currentProperty.equals(TrackProperty.TRACK_ID)) {
                // example property: <key>Track ID</key><integer>401</integer>

                // the track id signifies a new track is being parsed
                currentTrack = new ITunesTrack();

                // the library's internal map of tracks is keyed off of the
                // track id, so let's set that first
                currentTrack.setTrackID(DataParser.parseInteger(TrackProperty.TRACK_ID, propertyValue));

                // add a reference to the track to the library's list..
                library.addTrack(currentTrack);

            } else if (currentProperty.equals(TrackProperty.NAME)) {
                // example property: <key>Name</key><string>The Hollow</string>
                currentTrack.setName(DataParser.parseString(TrackProperty.NAME, propertyValue));
            } else if (currentProperty.equals(TrackProperty.ARTIST)) {
                // example property: <key>Artist</key><string>A Perfect
                // Circle</string>
                currentTrack.setArtist(DataParser.parseString(TrackProperty.ARTIST, propertyValue));
            } else if (currentProperty.equals(TrackProperty.ALBUM)) {
                // example property: <key>Album</key><string>Mer de
                // Noms</string>
                currentTrack.setAlbum(DataParser.parseString(TrackProperty.ALBUM, propertyValue));
            } else if (currentProperty.equals(TrackProperty.GENRE)) {
                // example property: <key>Genre</key><string>Progressive
                // Rock</string>
                currentTrack.setGenre(DataParser.parseString(TrackProperty.GENRE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.KIND)) {
                // example property: <key>Kind</key><string>MPEG audio
                // file</string>
                currentTrack.setKind(DataParser.parseString(TrackProperty.KIND, propertyValue));
            } else if (currentProperty.equals(TrackProperty.SIZE)) {
                // example property: <key>Size</key><integer>4299529</integer>
                currentTrack.setSize(DataParser.parseInteger(TrackProperty.SIZE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.TOTAL_TIME)) {
                // example property: <key>Total
                // Time</key><integer>179043</integer>
                currentTrack.setTotalTime(DataParser.parseInteger(TrackProperty.TOTAL_TIME, propertyValue));
            } else if (currentProperty.equals(TrackProperty.NUMBER)) {
                // example property: <key>Track Number</key><integer>1</integer>
                currentTrack.setTrackNumber(DataParser.parseInteger(TrackProperty.NUMBER, propertyValue));
            } else if (currentProperty.equals(TrackProperty.COUNT)) {
                // example property: <key>Track Count</key><integer>12</integer>
                currentTrack.setTrackCount(DataParser.parseInteger(TrackProperty.COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.YEAR)) {
                // example property: <key>Year</key><integer>2000</integer>
                currentTrack.setYear(DataParser.parseInteger(TrackProperty.YEAR, propertyValue));
            } else if (currentProperty.equals(TrackProperty.DATE_MODIFIED)) {
                // example property: <key>Date
                // Modified</key><date>2006-07-21T21:48:32Z</date>
                currentTrack.setDateModified(DataParser.parseDate(TrackProperty.DATE_MODIFIED, propertyValue));
            } else if (currentProperty.equals(TrackProperty.DATE_ADDED)) {
                // example property: <key>Date
                // Added</key><date>2005-10-20T02:43:19Z</date>
                currentTrack.setDateAdded(DataParser.parseDate(TrackProperty.DATE_ADDED, propertyValue));
            } else if (currentProperty.equals(TrackProperty.BIT_RATE)) {
                // example property: <key>Bit Rate</key><integer>192</integer>
                currentTrack.setBitRate(DataParser.parseInteger(TrackProperty.BIT_RATE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.SAMPLE_RATE)) {
                // example property: <key>Sample
                // Rate</key><integer>44100</integer>
                currentTrack.setSampleRate(DataParser.parseInteger(TrackProperty.SAMPLE_RATE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.COMMENTS)) {
                // example property: <key>Comments</key><string>Track 1</string>
                currentTrack.setComments(DataParser.parseString(TrackProperty.COMMENTS, propertyValue));
            } else if (currentProperty.equals(TrackProperty.PLAY_COUNT)) {
                // example property: <key>Play Count</key><integer>5</integer>
                currentTrack.setPlayCount(DataParser.parseInteger(TrackProperty.PLAY_COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.PLAY_DATE)) {
                // example property: <key>Play
                // Date</key><integer>3236345492</integer>
                currentTrack.setPlayDate(DataParser.parseLong(TrackProperty.PLAY_DATE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.PLAY_DATE_UTC)) {
                // example property: <key>Play Date
                // UTC</key><date>2006-07-21T21:51:32Z</date>
                currentTrack.setPlayDateUTC(DataParser.parseDate(TrackProperty.PLAY_DATE_UTC, propertyValue));
            } else if (currentProperty.equals(TrackProperty.PERSISTENT_ID)) {
                // example property: <key>Persistent
                // ID</key><string>51C977107B2FE940</string>
                currentTrack.setPersistentID(DataParser.parseString(TrackProperty.PERSISTENT_ID, propertyValue));
            } else if (currentProperty.equals(TrackProperty.TRACK_TYPE)) {
                // example property: <key>Track Type</key><string>File</string>
                currentTrack.setTrackType(DataParser.parseString(TrackProperty.TRACK_TYPE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.LOCATION)) {
                // example property:
                // <key>Location</key><string>file://localhost/E:/itunes/A%20Perfect%20Circle/Mer%20de%20Noms/01%20The%20Hollow.mp3</string>
                currentTrack.setLocation(DataParser.parseString(TrackProperty.LOCATION, propertyValue));
            } else if (currentProperty.equals(TrackProperty.FILE_FOLDER_COUNT)) {
                // example property: <key>File Folder
                // Count</key><integer>4</integer>
                currentTrack.setFileFolderCount(DataParser.parseInteger(TrackProperty.FILE_FOLDER_COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.LIBRARY_FOLDER_COUNT)) {
                // example property: <key>Library Folder
                // Count</key><integer>1</integer>
                currentTrack.setLibraryFolderCount(DataParser.parseInteger(TrackProperty.LIBRARY_FOLDER_COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.DISABLED)) {
                // example property: <key>Disabled</key><true/>
                currentTrack.setDisabled(DataParser.parseBoolean(TrackProperty.DISABLED, propertyValue));
            } else if (currentProperty.equals(TrackProperty.SKIP_COUNT)) {
                // example property: <key>Skip Count</key><integer>1</integer>
                currentTrack.setSkipCount(DataParser.parseInteger(TrackProperty.SKIP_COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.SKIP_DATE)) {
                // example property: <key>Skip
                // Date</key><date>2007-04-21T00:25:49Z</date>
                currentTrack.setSkipDate(DataParser.parseDate(TrackProperty.SKIP_DATE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.COMPOSER)) {
                // example property: <key>Composer</key><string>Trent
                // Reznor</string>
                currentTrack.setComposer(DataParser.parseString(TrackProperty.COMPOSER, propertyValue));
            } else if (currentProperty.equals(TrackProperty.ALBUM_ARTIST)) {
                // example property: <key>Album Artist</key><string>The Red
                // Jumpsuit Apparatus</string>
                currentTrack.setAlbumArtist(DataParser.parseString(TrackProperty.ALBUM_ARTIST, propertyValue));
            } else if (currentProperty.equals(TrackProperty.ARTWORK_COUNT)) {
                // example property: <key>Artwork
                // Count</key><integer>1</integer>
                currentTrack.setArtworkCount(DataParser.parseInteger(TrackProperty.ARTWORK_COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.GROUPING)) {
                // example property: <key>Grouping</key><string>Alternative
                // General</string>
                currentTrack.setGrouping(DataParser.parseString(TrackProperty.GROUPING, propertyValue));
            } else if (currentProperty.equals(TrackProperty.DISC_NUMBER)) {
                // example property: <key>Disc Number</key><integer>1</integer>
                currentTrack.setDiscNumber(DataParser.parseInteger(TrackProperty.DISC_NUMBER, propertyValue));
            } else if (currentProperty.equals(TrackProperty.DISC_COUNT)) {
                // example property: <key>Disc Count</key><integer>2</integer>
                currentTrack.setDiscCount(DataParser.parseInteger(TrackProperty.DISC_COUNT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.BPM)) {
                // example property: <key>BPM</key><integer>192</integer>
                currentTrack.setBPM(DataParser.parseInteger(TrackProperty.BPM, propertyValue));
            } else {
                System.out.println("Supported Itunes Track Property Was Not Handled Correctly: "+ currentProperty);
            }
        } catch (Exception e) {
            System.err.println("Error occured during track property parsing: " + e.getMessage());
        }

        currentProperty = TrackProperty.NO_PROPERTY;
    }
}
