package itunes.handler.constants;

import java.util.HashMap;
import java.util.Map;

public enum TagType {

    KEY("key"),
    STRING("string"),
    INTEGER("integer"),
    DATE("date"),
    ARRAY("array"),
    TRUE("true"),
    FALSE("false"),
    DATA("data"),
    PLIST("plist");

    private static final Map<String, TagType> tags = new HashMap<String, TagType>();

    static {
        for (TagType tagType : TagType.values()) {
            tags.put(tagType.name, tagType);
        }
    }

    private final String name;

    private TagType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TagType get(String name) {
        return tags.get(name);
    }

}

