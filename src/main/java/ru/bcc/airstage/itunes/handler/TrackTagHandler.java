package ru.bcc.airstage.itunes.handler;

import com.google.inject.Inject;
import ru.bcc.airstage.itunes.data.ITunesLibrary;
import ru.bcc.airstage.itunes.data.ITunesTrack;
import ru.bcc.airstage.itunes.handler.constants.TrackProperty;
import ru.bcc.airstage.itunes.parser.Tag;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Handler for track tag
 */
class TrackTagHandler implements TagHandler {

    private static final Logger log = Logger.getLogger(TrackTagHandler.class);

    @Inject
    private ITunesLibrary library;

    private ITunesTrack currentTrack;

    @NotNull
    private TrackProperty currentProperty = TrackProperty.NO_PROPERTY;

    @Override
    public void key(@NotNull String propertyName) {
        currentProperty = TrackProperty.get(propertyName);
    }

    @Override
    public void value(@NotNull Tag propertyValue) {
        try {
            if (currentProperty.equals(TrackProperty.NO_PROPERTY)) {
                // don't do anything...
            } else if (currentProperty.equals(TrackProperty.TRACK_ID)) {
                // example property: <key>Track ID</key><integer>401</integer>

                // the track id signifies a new track is being parsed
                currentTrack = new ITunesTrack();

                // the library's internal map of tracks is keyed off of the
                // track id, so let's set that first
                currentTrack.setTrackId(DataParser.parseInteger(TrackProperty.TRACK_ID, propertyValue));

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
            } else if (currentProperty.equals(TrackProperty.SORT_ALBUM)) {
                // example property: <key>Sort Album</key><string>Brothers of Chico Dusty</string>
                currentTrack.setSortAlbum(DataParser.parseString(TrackProperty.SORT_ALBUM, propertyValue));
            } else if (currentProperty.equals(TrackProperty.RELEASE_DATE)) {
                // example property: <key>Release Date</key><date>2010-11-10T11:48:31Z</date>
                currentTrack.setReleaseDate(DataParser.parseDate(TrackProperty.RELEASE_DATE, propertyValue));
            } else if (currentProperty.equals(TrackProperty.ITUNES_U)) {
                // example property: <key>iTunesU</key><true/>
                currentTrack.setiTunesU(DataParser.parseBoolean(TrackProperty.ITUNES_U, propertyValue));
            } else if (currentProperty.equals(TrackProperty.UNPLAYED)) {
                // example property: <key>Unplayed</key><true/>
                currentTrack.setUnplayed(DataParser.parseBoolean(TrackProperty.UNPLAYED, propertyValue));
            } else if (currentProperty.equals(TrackProperty.HAS_VIDEO)) {
                // example property: <key>Has Video</key><true/>
                currentTrack.setHasVideo(DataParser.parseBoolean(TrackProperty.HAS_VIDEO, propertyValue));
            } else if (currentProperty.equals(TrackProperty.HD)) {
                // example property: <key>HD</key><false/>
                currentTrack.setHd(DataParser.parseBoolean(TrackProperty.HD, propertyValue));
            } else if (currentProperty.equals(TrackProperty.VIDEO_WIDTH)) {
                // example property: <key>Video Width</key><integer>1280</integer>
                currentTrack.setVideoWith(DataParser.parseInteger(TrackProperty.VIDEO_WIDTH, propertyValue));
            } else if (currentProperty.equals(TrackProperty.VIDEO_HEIGHT)) {
                // example property: <key>Video Height</key><integer>720</integer>
                currentTrack.setVideoHeight(DataParser.parseInteger(TrackProperty.VIDEO_HEIGHT, propertyValue));
            } else if (currentProperty.equals(TrackProperty.MOVIE)) {
                // example property: <key>Movie</key><true/>
                currentTrack.setMovie(DataParser.parseBoolean(TrackProperty.MOVIE, propertyValue));
            } else {
                log.info("Supported Itunes Track Property Was Not Handled Correctly: " + currentProperty);
            }
        } catch (Exception e) {
            log.error("Error occured during track property parsing: " + e.getMessage());
        }

        currentProperty = TrackProperty.NO_PROPERTY;
    }
}
