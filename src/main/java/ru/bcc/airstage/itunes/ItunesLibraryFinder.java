package ru.bcc.airstage.itunes;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Date: 08.05.13
 * Time: 11:45
 * Finder for ITunes library
 *
 * @author Artem Prigoda
 */
public class ItunesLibraryFinder {

    private static final Logger log = Logger.getLogger(ItunesLibraryFinder.class);

    private enum OS {
        MAC_OS, WINDOWS, LINUX, SOLARIS, OTHER;

        public static OS currentOs() {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.startsWith("win")) {
                return WINDOWS;
            } else if (osName.startsWith("mac")) {
                return OS.MAC_OS;
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                return OS.LINUX;
            } else if (osName.contains("sunos")) {
                return OS.SOLARIS;
            }
            return OTHER;
        }
    }

    private OS currentOs = OS.currentOs();

    /**
     * Find ITunes library on the desktop
     *
     * @return path to itunes library
     */
    @Nullable
    public String findLibrary() {
        log.info("Detected OS " + currentOs);
        switch (currentOs) {
            case MAC_OS: {
                // test ~/Music/iTunes/iTunes Music Library.xml
                String libraryPath = pathFromHome("Music/iTunes/iTunes Music Library.xml");
                if (isItunesLibrary(libraryPath)) {
                    return libraryPath;
                }

                // test ~/Documents/iTunes/iTunes Music Library.xml
                libraryPath = pathFromHome("Documents/iTunes/iTunes Music Library.xml");
                if (isItunesLibrary(libraryPath)) {
                    return libraryPath;
                }
                break;
            }

            case WINDOWS: {
                //test ~/My Documents/My Music/iTunes/FILE HERE.xml
                String libraryPath = pathFromHome("My Documents/My Music/iTunes/iTunes Music Library.xml");
                if (isItunesLibrary(libraryPath)) {
                    return libraryPath;
                }
            }
            default:
                log.info("OS " + currentOs + " doesn't support ITunes");
        }

        // no library was found
        return null;
    }


    @NotNull
    private String pathFromHome(@NotNull String path) {
        String basePath = System.getProperty("user.home");
        String newPath = path.replace("/", File.separator);
        return new File(basePath).getPath() + File.separator + newPath;
    }

    /**
     * Check that file exist, readable and really ITunes library
     *
     * @param libraryFilename path to file
     * @return true if that ITunes library
     */
    private boolean isItunesLibrary(@NotNull String libraryFilename) {
        log.info("Looking for iTunes library at: " + libraryFilename);

        File libraryFile = new File(libraryFilename);
        if (!libraryFile.exists()) {
            log.warn("File does not exist: " + libraryFilename);
            return false;
        }

        if (!libraryFile.isFile()) {
            log.warn("File is not a file (probably a directory): " + libraryFilename);
            return false;
        }

        if (!libraryFile.canRead()) {
            log.warn("File is not readable (check permissions): " + libraryFilename);
            return false;
        }

        boolean isTunesLibrary = verifyLibrary(libraryFile);
        if (!isTunesLibrary) {
            log.warn("File " + libraryFile + " exist, but that not ITunes library");
        }
        return isTunesLibrary;
    }


    /**
     * Test that file really ITunes library
     *
     * @param libraryFilename path to file
     * @return true if ITunes library
     */
    public boolean verifyLibrary(@NotNull File libraryFilename) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(libraryFilename);
            char[] charBuffer = new char[3000];
            fileReader.read(charBuffer, 0, 3000);

            String buffer = new String(charBuffer);
            return buffer.contains("Apple Computer//DTD PLIST 1.0//EN");
        } catch (IOException e) {
            log.error("Unable open " + libraryFilename + " for reading", e);
            return false;
        } finally {
            try {
                if (fileReader != null) fileReader.close();
            } catch (IOException e) {
                log.error("I/O error", e);
            }
        }

    }


}
