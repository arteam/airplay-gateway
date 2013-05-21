package http;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.model.Content;
import ru.bcc.airstage.model.ContentFormat;
import ru.bcc.airstage.model.ContentSource;

/**
 * Date: 21.05.13
 * Time: 11:56
 *
 * @author Artem Prigoda
 */
public class ContentDaoStub extends ContentDao {

    @Nullable
    @Override
    public Content getById(@NotNull String id) {
        if (id.equals("5456")) {
            String url = "file://localhost/home/artem/Загрузки/13. Blocks and Multithreading (November 2, 2010) - HD.m4v";
            return new Content("442342", "Walk from Colorado", "Set Jones", ContentFormat.MPEG_4, url, false, ContentSource.ITUNES);
        }
        return super.getById(id);
    }
}
