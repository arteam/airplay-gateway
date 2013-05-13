package ru.bcc.airstage.stream.server;

import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bcc.airstage.stream.BinaryFileHandler;

import java.util.Map;

/**
 * Date: 5/12/13
 * Time: 9:04 PM
 *
 * @author Artem Prigoda
 */
@ImplementedBy(BinaryFileHandler.class)
public interface SessionHandler {

    @Nullable
    Response serve(@NotNull Request request);
}
