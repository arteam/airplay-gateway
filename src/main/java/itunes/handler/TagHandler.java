package itunes.handler;

import itunes.parser.Tag;

/**
 * Handler for properties in library.xml
 */
interface TagHandler {

    public void key(String propertyName);

    public void value(Tag propertyValue);
}
