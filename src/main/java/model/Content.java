package model;

import itunes.data.ITunesTrack;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Date: 30.04.13
 * Time: 16:33
 *
 * @author Artem Prigoda
 */
public class Content {

    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String artist;

    @NotNull
    private final ContentFormat format;

    @NotNull
    private String url;
    private final boolean isHd;

    private final ContentSource source;

    public Content(@NotNull ITunesTrack track) {
        id = String.valueOf(track.getTrackId());
        name = track.getName();
        artist = track.getArtist();
        format = ContentFormat.get(track.getKind());
        try {
            url = URLDecoder.decode(track.getLocation(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            url = "";
        }
        isHd = track.isHd();
        source = ContentSource.ITUNES;
    }

    public Content(String id, @NotNull String name, @NotNull String artist,
                   @NotNull ContentFormat format, @NotNull String url, boolean hd, ContentSource source) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.format = format;
        this.url = url;
        isHd = hd;
        this.source = source;
    }

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

    public ContentSource getSource() {
        return source;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Content{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", artist='").append(artist).append('\'');
        sb.append(", type=").append(format);
        sb.append(", url='").append(url).append('\'');
        sb.append(", isHd=").append(isHd);
        sb.append('}');
        return sb.toString();
    }
}
