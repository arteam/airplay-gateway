package com.github.arteam.model;

import com.github.arteam.itunes.data.ITunesTrack;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Date: 30.04.13
 * Time: 16:33
 * Represents an indexed content from different sources
 *
 * @author Artem Prigoda
 */
public class Content {

    /**
     * Content unique id
     */
    @NotNull
    private final String id;

    /**
     * Content name
     */
    @NotNull
    private final String name;

    /**
     * Content author(s) or performer
     */
    @NotNull
    private final String artist;

    /**
     * Content format (MPEG-4, MP3, etc...)
     */
    @NotNull
    private final ContentFormat format;

    /**
     * Is HD-content
     */
    private final boolean isHd;

    /**
     * Content locations
     */
    @NotNull
    private final String url;

    /**
     * Content source (ITunes, Internet, user directory...)
     */
    private final ContentSource source;

    public Content(@NotNull ITunesTrack track) {
        id = String.valueOf(track.getTrackId());
        name = track.getName();
        artist = track.getArtist();
        format = ContentFormat.get(track.getKind());
        isHd = track.isHd();
        source = ContentSource.ITUNES;
        url = decodeUrl(track.getLocation(), "UTF-8");
    }

    public Content(@NotNull String id, @NotNull String name, @NotNull String artist,
                   @NotNull ContentFormat format, @NotNull String url, boolean hd, ContentSource source) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.format = format;
        this.url = url;
        isHd = hd;
        this.source = source;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getArtist() {
        return artist;
    }

    @NotNull
    public ContentFormat getFormat() {
        return format;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public boolean isHd() {
        return isHd;
    }

    @NotNull
    public ContentSource getSource() {
        return source;
    }

    @NotNull
    private static String decodeUrl(@NotNull String encodedUrl, @NotNull String encoding) {
        try {
            return URLDecoder.decode(encodedUrl, encoding);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return "Content{" + "id=" + id + ", name=" + name +
                ", artist=" + artist + ", format=" + format +
                ", url='" + url + ", isHd=" + isHd +
                ", source=" + source + '}';
    }
}
