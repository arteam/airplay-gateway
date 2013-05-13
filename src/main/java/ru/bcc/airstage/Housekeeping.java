package ru.bcc.airstage;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 13.05.13
 * Time: 15:27
 *
 * @author Artem Prigoda
 */
@Singleton
public class Housekeeping {

    public Housekeeping() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (Runnable task : tasks) {
                    task.run();
                }
            }
        });
    }

    private final List<Runnable> tasks = Collections.synchronizedList(new ArrayList<Runnable>());

    public void afterShutdown(Runnable task) {
        tasks.add(task);
    }


}
