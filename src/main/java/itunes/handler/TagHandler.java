package itunes.handler;

import itunes.parser.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * Handler for properties in library.xml
 */
interface TagHandler {

    public void key(@NotNull String propertyName);

    public void value(@NotNull Tag propertyValue);
}
