package ru.bcc.airstage.database;

import com.google.inject.Singleton;
import ru.bcc.airstage.model.Content;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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


}
