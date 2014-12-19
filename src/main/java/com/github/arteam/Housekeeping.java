package com.github.arteam;

import com.google.inject.Singleton;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 13.05.13
 * Time: 15:27
 * Housekeeping over resources
 *
 * @author Artem Prigoda
 */
@Singleton
public class Housekeeping {

    private static final Logger log = Logger.getLogger(Housekeeping.class);

    public Housekeeping() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (Runnable task : tasks) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        log.error("Error in shutdown task", e);
                    }
                }
            }
        });
    }

    private final List<Runnable> tasks = Collections.synchronizedList(new ArrayList<Runnable>());

    public void afterShutdown(Runnable task) {
        tasks.add(task);
    }


}
