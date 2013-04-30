
package itunes.data;

/*
    Track properties example xml:
	<key>401</key>
	<dict>
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
	</dict>
*/
public class ITunesTrack {

    protected int trackID = -1;
    protected String name = null;
    protected String artist = null;
    protected String album = null;
    protected String genre = null;
    protected String kind = null;
    protected int size = -1;
    protected int totalTime = -1;
    protected int trackNumber = -1;
    protected int trackCount = -1;
    protected int year = -1;
    protected String dateModified = null;
    protected String dateAdded = null;
    protected int bitRate = -1;
    protected int sampleRate = -1;
    protected String comments = null;
    protected int playCount = -1;
    protected long playDate = -1;
    protected String playDateUTC = null;
    protected String persistentID = null;
    protected String trackType = null;
    protected String location = null;
    protected int fileFolderCount = -1;
    protected int libraryFolderCount = -1;
    protected boolean disabled = false;
    protected int skipCount = -1;
    protected String skipDate = null;
    protected String composer = null;
    protected String albumArtist = null;
    protected int artworkCount = -1;
    protected String grouping = null;
    protected int discNumber = -1;
    protected int discCount = -1;
    protected int BPM = -1;

    /**
     * the track number in a given playlist
     */
    protected int playlistTrackNumber = -1;

    public int getTrackID() {
        return trackID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public long getPlayDate() {
        return playDate;
    }

    public void setPlayDate(long playDate) {
        this.playDate = playDate;
    }

    public String getPlayDateUTC() {
        return playDateUTC;
    }

    public void setPlayDateUTC(String playDateUTC) {
        this.playDateUTC = playDateUTC;
    }

    public String getPersistentID() {
        return persistentID;
    }

    public void setPersistentID(String persistentID) {
        this.persistentID = persistentID;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFileFolderCount() {
        return fileFolderCount;
    }

    public void setFileFolderCount(int fileFolderCount) {
        this.fileFolderCount = fileFolderCount;
    }

    public int getLibraryFolderCount() {
        return libraryFolderCount;
    }

    public void setLibraryFolderCount(int libraryFolderCount) {
        this.libraryFolderCount = libraryFolderCount;
    }

    public int getPlaylistTrackNumber() {
        return playlistTrackNumber;
    }

    public void setPlaylistTrackNumber(int playlistTrackNumber) {
        this.playlistTrackNumber = playlistTrackNumber;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public String getSkipDate() {
        return skipDate;
    }

    public void setSkipDate(String skipDate) {
        this.skipDate = skipDate;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public int getArtworkCount() {
        return artworkCount;
    }

    public void setArtworkCount(int artworkCount) {
        this.artworkCount = artworkCount;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public int getDiscCount() {
        return discCount;
    }

    public void setDiscCount(int discCount) {
        this.discCount = discCount;
    }

    public int getBPM() {
        return BPM;
    }

    public void setBPM(int bpm) {
        BPM = bpm;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItunesTrack{").append("\n");
        sb.append(" trackID=").append(trackID).append("\n");
        sb.append(" name=").append(name).append("\n");
        sb.append(" artist=").append(artist).append("\n");
        sb.append(" album=").append(album).append("\n");
        sb.append(" genre=").append(genre).append("\n");
        sb.append(" kind=").append(kind).append("\n");
        sb.append(" size=").append(size).append("\n");
        sb.append(" totalTime=").append(totalTime).append("\n");
        sb.append(" trackNumber=").append(trackNumber).append("\n");
        sb.append(" trackCount=").append(trackCount).append("\n");
        sb.append(" year=").append(year).append("\n");
        sb.append(" dateModified=").append(dateModified).append("\n");
        sb.append(" dateAdded=").append(dateAdded).append("\n");
        sb.append(" bitRate=").append(bitRate).append("\n");
        sb.append(" sampleRate=").append(sampleRate).append("\n");
        sb.append(" comments=").append(comments).append("\n");
        sb.append(" playCount=").append(playCount).append("\n");
        sb.append(" playDate=").append(playDate).append("\n");
        sb.append(" playDateUTC=").append(playDateUTC).append("\n");
        sb.append(" persistentID=").append(persistentID).append("\n");
        sb.append(" trackType=").append(trackType).append("\n");
        sb.append(" location=").append(location).append("\n");
        sb.append(" fileFolderCount=").append(fileFolderCount).append("\n");
        sb.append(" libraryFolderCount=").append(libraryFolderCount).append("\n");
        sb.append(" disabled=").append(disabled).append("\n");
        sb.append(" skipCount=").append(skipCount).append("\n");
        sb.append(" skipDate=").append(skipDate).append("\n");
        sb.append(" composer=").append(composer).append("\n");
        sb.append(" albumArtist=").append(albumArtist).append("\n");
        sb.append(" artworkCount=").append(artworkCount).append("\n");
        sb.append(" grouping=").append(grouping).append("\n");
        sb.append(" discNumber=").append(discNumber).append("\n");
        sb.append(" discCount=").append(discCount).append("\n");
        sb.append(" BPM=").append(BPM).append("\n");
        sb.append(" playlistTrackNumber=").append(playlistTrackNumber).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
