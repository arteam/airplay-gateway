package ru.bcc.airstage.itunes.watch;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.itunes.ITunesLibraryProvider;
import ru.bcc.airstage.itunes.ItunesLibraryFinder;
import ru.bcc.airstage.itunes.data.ITunesLibrary;
import ru.bcc.airstage.itunes.data.ITunesTrack;
import ru.bcc.airstage.model.Content;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Date: 21.05.13
 * Time: 20:07
 * Monitoring for changes in ITunes library
 *
 * @author Artem Prigoda
 */
public class ITunesLibraryWatcher {

    private static final Logger log = Logger.getLogger(ITunesLibraryWatcher.class);
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private File file;
    private Date lastModified;

    @Inject
    private ItunesLibraryFinder itunesLibraryFinder;

    @Inject
    private ContentDao contentDao;

    @Inject
    private ITunesLibraryProvider iTunesLibraryProvider;

    /**
     * Start monitoring
     */
    public void start() {
        String path = itunesLibraryFinder.findLibrary();
        if (path == null) {
            throw new IllegalStateException("Unable find path to ITunes library");
        }
        file = new File(path);
        lastModified = new Date(file.lastModified());

        log.info("Starting monitoring ITunes library in " + file.getAbsolutePath() + " for changes");
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!file.exists()) {
                    return;
                }
                Date newModifiedTime = new Date(file.lastModified());
                if (newModifiedTime.after(lastModified)) {
                    log.info("ITunes library has changed. New last modified time: " + newModifiedTime);
                    updateContent();
                    lastModified = newModifiedTime;
                }
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    /**
     * Update library
     */
    private void updateContent() {
        ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get(file.getPath());

        Map<String, Content> contentMap = new HashMap<String, Content>();
        for (ITunesTrack track : iTunesLibrary.getTracks().values()) {
            Content content = new Content(track);
            contentMap.put(content.getId(), content);
        }
        contentDao.setContent(contentMap);
        log.info(contentDao);
    }

    public void stop() {
        log.info("Stop monitoring...");
        executor.shutdown();
    }
}
