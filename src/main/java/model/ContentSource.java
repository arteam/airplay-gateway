package model;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 30.04.13
 * Time: 17:03
 *
 * @author Artem Prigoda
 */
public enum ContentSource {
    ITUNES("iTunes"), USER_DIRECTORY("userDirectory");

    @NotNull
    private final String name;

    private ContentSource(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
