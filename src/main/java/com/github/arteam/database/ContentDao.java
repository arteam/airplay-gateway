package com.github.arteam.database;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.github.arteam.model.Content;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Date: 30.04.13
 * Time: 16:33
 * DAO for work with indexed content
 *
 * @author Artem Prigoda
 */
@Singleton
public class ContentDao {

    @NotNull
    private Map<String, Content> contentMap = new HashMap<String, Content>();

    public void setContent(@NotNull Map<String, Content> contentMap) {
        this.contentMap = contentMap;
    }

    @Nullable
    public Content getById(@NotNull String id) {
        return contentMap.get(id);
    }

    @NotNull
    public Collection<Content> getContentList() {
        return contentMap.values();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Found content: \n");
        Iterator<Content> iterator = contentMap.values().iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) builder.append("\n");
        }
        return builder.toString();
    }
}
