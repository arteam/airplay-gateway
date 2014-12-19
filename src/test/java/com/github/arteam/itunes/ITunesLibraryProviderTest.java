package com.github.arteam.itunes;

import com.github.arteam.itunes.data.ITunesLibrary;
import com.github.arteam.itunes.data.ITunesTrack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        assertEquals(iTunesLibrary.getMinorVersion(), 1);
        assertEquals(iTunesLibrary.getMajorVersion(), 1);
        assertEquals(iTunesLibrary.getDate(), "2013-04-29T11:11:15Z");
        assertEquals(iTunesLibrary.getApplicationVersion(), "11.0.2");
        assertEquals(iTunesLibrary.getFeatures(), 5);
        assertEquals(iTunesLibrary.isShowContentRatings(), true);
        assertEquals(iTunesLibrary.getMusicFolder(), "file://localhost/Users/user/Music/iTunes/iTunes%20Media/");
        assertEquals(iTunesLibrary.getLibraryPersistentID(), "E42C1259AE9A045A");

        ITunesTrack musicTrack = iTunesLibrary.getTrackById(81);
        assertEquals(musicTrack.getTrackId(), 81);
        assertEquals(musicTrack.getName(), "Afraid of The General");
        assertEquals(musicTrack.getArtist(), "Wick-it the Instigator");
        assertEquals(musicTrack.getAlbum(), "The Brothers of Chico Dusty");
        assertEquals(musicTrack.getGenre(), "Hip Hop ");
        assertEquals(musicTrack.getKind(), "MPEG audio file");
        assertEquals(musicTrack.getSize(), 8601253);
        assertEquals(musicTrack.getTotalTime(), 210480);
        assertEquals(musicTrack.getTrackCount(), 8);
        assertEquals(musicTrack.getTrackNumber(), 2);
        assertEquals(musicTrack.getYear(), 2011);
        assertEquals(musicTrack.getDateModified(), "2013-03-12T08:23:43Z");
        assertEquals(musicTrack.getDateAdded(), "2013-03-12T08:23:54Z");
        assertEquals(musicTrack.getBitRate(), 320);
        assertEquals(musicTrack.getSampleRate(), 48000);
        assertEquals(musicTrack.getPlayCount(), 52);
        assertEquals(musicTrack.getPlayDate(), 3449494999L);
        assertEquals(musicTrack.getPlayDateUTC(), "2013-04-22T13:03:19Z");
        assertEquals(musicTrack.getArtworkCount(), 1);
        assertEquals(musicTrack.getSortAlbum(), "Brothers of Chico Dusty");
        assertEquals(musicTrack.getPersistentID(), "65F68A53E6AD515D");
        assertEquals(musicTrack.getTrackType(), "File");
        assertEquals(musicTrack.getLocation(),
                "file://localhost/Users/user/Music/iTunes/iTunes%20Media/Music/Wick-it%20the%20Instigator/The%20Brothers%20of%20Chico%20Dusty/02%20Afraid%20of%20The%20General.mp3");
        assertEquals(musicTrack.getFileFolderCount(), 5);
        assertEquals(musicTrack.getLibraryFolderCount(), 1);

        ITunesTrack videoTrack = iTunesLibrary.getTrackById(5456);
        assertEquals(videoTrack.getTrackId(), 5456);
        assertEquals(videoTrack.getName(), "13. Blocks and Multithreading (November 2, 2010) - HD");
        assertEquals(videoTrack.getArtist(), "Paul Hegarty");
        assertEquals(videoTrack.getComposer(), "Stanford");
        assertEquals(videoTrack.getAlbum(), "Developing Apps for iOS (HD)");
        assertEquals(videoTrack.getGrouping(), "Software engineering, programming language, operating system, iOS, OS, iPhone, iPad objective c, cocoa touch, SDK, object oriented design, Apple, Macintosh, tools, frameworks, language, runtime, Xcode, Interface Builder, App Store, Core Graphics, platform");
        assertEquals(videoTrack.getGenre(), "iTunes U");
        assertEquals(videoTrack.getKind(), "MPEG-4 video file");
        assertEquals(videoTrack.getSize(), 806917419);
        assertEquals(videoTrack.getTotalTime(), 3982716);
        assertEquals(videoTrack.getDiscNumber(), 1);
        assertEquals(videoTrack.getYear(), 2010);
        assertEquals(videoTrack.getDateModified(), "2013-04-29T11:11:05Z");
        assertEquals(videoTrack.getDateAdded(), "2013-04-29T11:11:03Z");
        assertEquals(videoTrack.getBitRate(), 110);
        assertEquals(videoTrack.getComments(), "(November 2, 2010) Paul Hegarty covers blocks and when to use them.  He then discusses multithreading with the Grand Central Dispatch API.");
        assertEquals(videoTrack.getReleaseDate(), "2010-11-10T11:48:31Z");
        assertEquals(videoTrack.getArtworkCount(), 1);
        assertEquals(videoTrack.getPersistentID(), "008727A676CA4A73");
        assertEquals(videoTrack.getTrackType(), "File");
        assertEquals(videoTrack.isiTunesU(), true);
        assertEquals(videoTrack.isUnplayed(), true);
        assertEquals(videoTrack.hasVideo(), true);
        assertEquals(videoTrack.isHd(), false);
        assertEquals(videoTrack.getVideoWith(), 1280);
        assertEquals(videoTrack.getVideoHeight(), 720);
        assertEquals(videoTrack.isMovie(), true);
        assertEquals(videoTrack.getLocation(), "file://localhost/Users/user/Music/iTunes/iTunes%20Media/iTunes%20U/Developing%20Apps%20for%20iOS%20(HD)/13.%20Blocks%20and%20Multithreading%20(November%202,%202010)%20-%20HD.m4v");
        assertEquals(videoTrack.getFileFolderCount(), 4);
        assertEquals(videoTrack.getLibraryFolderCount(), 1);

    }
}
