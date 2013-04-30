package itunes.data;

import java.util.HashMap;
import java.util.Map;

/*
    Example Itunes Library Properties:
	<key>Major Version</key><integer>1</integer>
	<key>Minor Version</key><integer>1</integer>
	<key>Application Version</key><string>7.0.1</string>
	<key>Features</key><integer>1</integer>
	<key>Show Content Ratings</key><true/>
	<key>Music Folder</key><string>file://localhost/E:/itunes/</string>
	<key>Library Persistent ID</key><string>4EC2FAC25152379E</string>
*/
public class ITunesLibrary {

    private int majorVersion = -1;
    private int minorVersion = -1;
    private String date;
    private String applicationVersion;
    private int features = -1;
    private boolean showContentRatings = false;
    private String musicFolder;
    private String libraryPersistentID;
    // this stores the path the library was parsed from
    private String libraryXmlPath;
    private Map<Integer, ITunesTrack> tracks = new HashMap<Integer, ITunesTrack>();

    public void addTrack(ITunesTrack track) {
        tracks.put(track.getTrackID(), track);
    }

    public ITunesTrack getTrackById(int trackId) {
        if (!tracks.containsKey(trackId)) {
            throw new IllegalStateException("Can't find the track with id: " + trackId);
        }
        return tracks.get(trackId);
    }

    public Map getTracks() {
        return tracks;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public int getFeatures() {
        return features;
    }

    public void setFeatures(int features) {
        this.features = features;
    }

    public boolean isShowContentRatings() {
        return showContentRatings;
    }

    public void setShowContentRatings(boolean showContentRatings) {
        this.showContentRatings = showContentRatings;
    }

    public String getMusicFolder() {
        return musicFolder;
    }

    public void setMusicFolder(String musicFolder) {
        this.musicFolder = musicFolder;
    }

    public String getLibraryPersistentID() {
        return libraryPersistentID;
    }

    public void setLibraryPersistentID(String libraryPersistentID) {
        this.libraryPersistentID = libraryPersistentID;
    }

    public String getLibraryXmlPath() {
        return libraryXmlPath;
    }

    public void setLibraryXmlPath(String libraryXmlPath) {
        this.libraryXmlPath = libraryXmlPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItunesLibrary{");
        sb.append("majorVersion=").append(majorVersion).append("\n");
        sb.append("minorVersion=").append(minorVersion).append("\n");
        sb.append("date=").append(date).append("\n");
        sb.append("applicationVersion=").append(applicationVersion).append("\n");
        sb.append("features=").append(features).append("\n");
        sb.append("showContentRatings=").append(showContentRatings).append("\n");
        sb.append("musicFolder=").append(musicFolder).append("\n");
        sb.append("libraryPersistentID=").append(libraryPersistentID).append("\n");
        sb.append("libraryXmlPath=").append(libraryXmlPath).append("\n");
        sb.append("tracks=").append(tracks).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
