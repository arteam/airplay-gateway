package itunes.handler;


import itunes.handler.constants.TagType;
import itunes.parser.Tag;

public final class DataParser {

    private DataParser() {
    }

    public static int parseInteger(Enum property, Tag propertyValue) {
        if (!propertyValue.getName().equals(TagType.INTEGER)) {
            error(property, TagType.INTEGER, propertyValue);
        }

        try {
            return Integer.parseInt(propertyValue.getInnerText());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("NumberFormatException occurred while trying to parse property "
                    + property + "'s integer value from the following string:" + propertyValue.getInnerText());
        }
    }


    public static long parseLong(Enum propertyName, Tag propertyValue) {
        if (!propertyValue.getName().equals(TagType.INTEGER)) {
            error(propertyName, TagType.INTEGER, propertyValue);
            return Long.MIN_VALUE;
        }

        try {
            return Long.parseLong(propertyValue.getInnerText());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("NumberFormatException occurred while trying to parse property "
                    + propertyName + "'s integer value from the following string:" + propertyValue.getInnerText());
        }
    }

    public static String parseDate(Enum propertyName, Tag propertyValue) {
        if (!propertyValue.getName().equals(TagType.DATE)) {
            error(propertyName, TagType.DATE, propertyValue);
        }

        return propertyValue.getInnerText();
    }

    public static String parseString(Enum propertyName, Tag propertyValue) {
        if (!propertyValue.getName().equals(TagType.STRING)) {
            error(propertyName, TagType.STRING, propertyValue);
        }

        return propertyValue.getInnerText();
    }

    public static boolean parseBoolean(Enum propertyName, Tag propertyValue) {
        if (!propertyValue.getName().equals(TagType.TRUE) && !propertyValue.getName().equals(TagType.FALSE)) {
            error(propertyName, TagType.TRUE, propertyValue);
        }

        if (propertyValue.getName().equals(TagType.TRUE)) {
            return true;
        } else if (propertyValue.getName().equals(TagType.FALSE)) {
            return false;
        } else {
            throw new IllegalStateException("Could not parse boolean value for property "
                    + propertyName + " from the following tag: " + propertyValue);
        }
    }

    public static byte[] parseBytes(Enum propertyName, Tag propertyValue) {
        if (!propertyValue.getName().equals(TagType.DATA)) {
            error(propertyName, TagType.DATA, propertyValue);
        }

        return propertyValue.getInnerText().getBytes();
    }

    protected static void error(Enum propertyName, TagType dataType, Tag propertyValue) {
        throw new IllegalStateException("Parsing Exception while parsing property["
                + propertyName + "], property value found is not " + dataType + ": " + propertyValue);
    }
}
