package itunes;

import com.google.inject.*;
import itunes.data.ITunesLibrary;
import itunes.parser.ITunesLibraryParser;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Date: 30.04.13
 * Time: 11:53
 * Provider for ITunes Library
 *
 * @author Artem Prigoda
 */
@Singleton
public class ITunesLibraryProvider {

    @NotNull
    public ITunesLibrary get(@NotNull String itunesLibraryFilePath) {
        ITunesLibrary iTunesLibrary = new ITunesLibrary();

        // Create Injector and bind iTunesLibrary to request scope
        Injector injector = Guice.createInjector(new ITunesModule(iTunesLibrary));
        ITunesLibraryParser parser = injector.getInstance(ITunesLibraryParser.class);

        parser.parse(itunesLibraryFilePath);
        return iTunesLibrary;
    }

    private static class ITunesModule extends AbstractModule {

        final ITunesLibrary iTunesLibrary;

        private ITunesModule(ITunesLibrary iTunesLibrary) {
            this.iTunesLibrary = iTunesLibrary;
        }

        @Override
        protected void configure() {
        }

        @Provides
        @Singleton
        public ITunesLibrary getITunesLibrary() {
            return iTunesLibrary;
        }

        // Create SAX parser
        @Provides
        public SAXParser saxParser() throws ParserConfigurationException, SAXException {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false);
            return spf.newSAXParser();
        }
    }
}
