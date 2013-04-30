package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Date: 30.04.13
 * Time: 17:03
 *
 * @author Artem Prigoda
 */
public enum ContentType {
    MP3("MPEG audio file"),
    MPEG_4("MPEG-4 video file"),
    UNDEFINED("Undefined");

    @NotNull
    private String name;

    private ContentType(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public static ContentType get(@NotNull String name) {
        for (ContentType ct : values()) {
            if (ct.getName().equals(name)) {
                return ct;
            }
        }
        return UNDEFINED;
    }
}
