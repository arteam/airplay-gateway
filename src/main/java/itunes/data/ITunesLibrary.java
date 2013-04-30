package itunes.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents info about Itunes library (with tracks)
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
    private Map<Integer, ITunesTrack> tracks = new HashMap<Integer, ITunesTrack>();

    public void addTrack(ITunesTrack track) {
        tracks.put(track.getTrackId(), track);
    }

    public ITunesTrack getTrackById(int trackId) {
        if (!tracks.containsKey(trackId)) {
            throw new IllegalStateException("Can't find the track with id: " + trackId);
        }
        return tracks.get(trackId);
    }

    public Map<Integer, ITunesTrack> getTracks() {
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
        sb.append("tracks=").append(tracks).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
