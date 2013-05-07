package database;

import com.google.inject.Singleton;
import model.Content;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
    private List<Content> contentList = new CopyOnWriteArrayList<Content>();

    public void addContent(@NotNull Content content) {
        contentList.add(content);
    }

    public void removeContent(@NotNull Content content) {
        contentList.remove(content);
    }

    @NotNull
    public List<Content> getContentList() {
        return contentList;
    }
}
