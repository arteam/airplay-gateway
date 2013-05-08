package ru.bcc.airstage.itunes;

import org.junit.Test;

/**
 * Date: 08.05.13
 * Time: 12:26
 *
 * @author Artem Prigoda
 */
public class ItunesLibraryFinderTest {

    ItunesLibraryFinder itunesLibraryFinder = new ItunesLibraryFinder();

    @Test
    public void testFindLibrary() {
        String library = itunesLibraryFinder.findLibrary();
        System.out.println(library);
    }
}
