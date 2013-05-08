
package ru.bcc.airstage.itunes.data;

/**
 * Represents info about iTunes track (mp3 file or mpeg4 video)
 */
public class ITunesTrack {

    private int trackId = -1;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String kind;
    private int size = -1;
    private int totalTime = -1;
    private int trackNumber = -1;
    private int trackCount = -1;
    private int year = -1;
    private String dateModified;
    private String dateAdded;
    private int bitRate = -1;
    private int sampleRate = -1;
    private String comments;
    private String releaseDate;
    private boolean iTunesU;
    private boolean unplayed;
    private boolean hasVideo;
    private boolean hd;
    private int videoWith;
    private int videoHeight;
    private boolean movie;
    private int playCount = -1;
    private long playDate = -1;
    private String playDateUTC;
    private String persistentID;
    private String trackType;
    private String location;
    private int fileFolderCount = -1;
    private int libraryFolderCount = -1;
    private boolean disabled = false;
    private int skipCount = -1;
    private String skipDate;
    private String composer;
    private String albumArtist;
    private int artworkCount = -1;
    private String sortAlbum;
    private String grouping;
    private int discNumber = -1;
    private int discCount = -1;
    private int BPM = -1;

    /**
     * the track number in a given playlist
     */
    private int playlistTrackNumber = -1;

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
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

    public String getSortAlbum() {
        return sortAlbum;
    }

    public void setSortAlbum(String sortAlbum) {
        this.sortAlbum = sortAlbum;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isiTunesU() {
        return iTunesU;
    }

    public void setiTunesU(boolean iTunesU) {
        this.iTunesU = iTunesU;
    }

    public boolean isUnplayed() {
        return unplayed;
    }

    public void setUnplayed(boolean unplayed) {
        this.unplayed = unplayed;
    }

    public boolean hasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public boolean isHd() {
        return hd;
    }

    public void setHd(boolean hd) {
        this.hd = hd;
    }

    public int getVideoWith() {
        return videoWith;
    }

    public void setVideoWith(int videoWith) {
        this.videoWith = videoWith;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public boolean isMovie() {
        return movie;
    }

    public void setMovie(boolean movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItunesTrack{").append("\n");
        sb.append(" trackID=").append(trackId).append("\n");
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
        sb.append(" releaseDate=").append(releaseDate).append("\n");
        sb.append(" iTunesU=").append(iTunesU).append("\n");
        sb.append(" unplayed=").append(unplayed).append("\n");
        sb.append(" hasVideo=").append(hasVideo).append("\n");
        sb.append(" hd=").append(hd).append("\n");
        sb.append(" videoWith=").append(videoWith).append("\n");
        sb.append(" videoHeight=").append(videoHeight).append("\n");
        sb.append(" movie=").append(movie).append("\n");
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
        sb.append(" artworkCount=").append(artworkCount).append("\n");
        sb.append(" sortAlbum=").append(sortAlbum).append("\n");
        sb.append(" discNumber=").append(discNumber).append("\n");
        sb.append(" discCount=").append(discCount).append("\n");
        sb.append(" BPM=").append(BPM).append("\n");
        sb.append(" playlistTrackNumber=").append(playlistTrackNumber).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
