package itunes;

import itunes.data.ITunesLibrary;
import org.junit.Test;

/**
 * Date: 30.04.13
 * Time: 12:47
 *
 * @author Artem Prigoda
 */
public class ITunesLibraryProviderTest {

    ITunesLibraryProvider iTunesLibraryProvider = new ITunesLibraryProvider();

    @Test
    public void testGet() throws Exception {
        ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get("./src/main/resources/library.xml");
        System.out.println(iTunesLibrary);
    }
}
