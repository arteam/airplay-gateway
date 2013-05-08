package ru.bcc.airstage.model;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 30.04.13
 * Time: 17:03
 *
 * @author Artem Prigoda
 */
public enum ContentFormat {
    MP3("MP3", "MPEG audio file"),
    MPEG_4("MPEG-4", "MPEG-4 video file"),
    UNDEFINED("Undefined", "Undefined");

    @NotNull
    private final String name;

    @NotNull
    private final String bkey;

    private ContentFormat(@NotNull String name, @NotNull String bkey) {
        this.name = name;
        this.bkey = bkey;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public static ContentFormat get(@NotNull String bkey) {
        for (ContentFormat ct : values()) {
            if (ct.bkey.equals(bkey)) {
                return ct;
            }
        }
        return UNDEFINED;
    }
}
