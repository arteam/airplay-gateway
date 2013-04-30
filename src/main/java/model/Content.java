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

    private final int id;

    @NotNull
    private final String name;

    @NotNull
    private final String artist;

    @NotNull
    private final ContentType type;

    @NotNull
    private String url;
    private final boolean isHd;

    public Content(@NotNull ITunesTrack track) {
        id = track.getTrackID();
        name = track.getName();
        artist = track.getArtist();
        type = ContentType.get(track.getKind());
        try {
            url = URLDecoder.decode(track.getLocation(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            url = "";
        }
        isHd = track.isHd();
    }

    public int getId() {
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
    public ContentType getType() {
        return type;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public boolean isHd() {
        return isHd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Content{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", artist='").append(artist).append('\'');
        sb.append(", type=").append(type);
        sb.append(", url='").append(url).append('\'');
        sb.append(", isHd=").append(isHd);
        sb.append('}');
        return sb.toString();
    }
}
