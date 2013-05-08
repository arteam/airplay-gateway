package ru.bcc.airstage.itunes;

import com.google.inject.*;
import org.apache.log4j.Logger;
import org.xml.sax.XMLReader;
import ru.bcc.airstage.itunes.data.ITunesLibrary;
import ru.bcc.airstage.itunes.parser.ITunesLibraryParser;
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

    private static final Logger log = Logger.getLogger(ITunesLibraryProvider.class);

    @Inject
    private ItunesLibraryFinder itunesLibraryFinder;

    @NotNull
    public ITunesLibrary get(@NotNull String itunesLibraryFilePath) {
        ITunesLibrary iTunesLibrary = new ITunesLibrary();

        // Create Injector and bind iTunesLibrary to request scope
        Injector injector = Guice.createInjector(new ITunesModule(iTunesLibrary));
        ITunesLibraryParser parser = injector.getInstance(ITunesLibraryParser.class);

        log.info("Path to library " + itunesLibraryFilePath);
        parser.parse(itunesLibraryFilePath);
        log.info(iTunesLibrary);
        return iTunesLibrary;
    }

    @NotNull
    public ITunesLibrary get() {
        String libraryPath = itunesLibraryFinder.findLibrary();
        if (libraryPath == null) {
            throw new IllegalStateException("Unable find ITunes library");
        }
        return get(libraryPath);
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
            SAXParser saxParser = spf.newSAXParser();
            XMLReader parser = saxParser.getXMLReader();

            // Ignore the DTD declaration
            parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            return saxParser;
        }
    }
}
