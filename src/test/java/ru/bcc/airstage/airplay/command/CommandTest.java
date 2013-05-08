package ru.bcc.airstage.airplay.command;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Date: 07.05.13
 * Time: 13:49
 *
 * @author Artem Prigoda
 */
public class CommandTest {

    @Test
    public void testPlayCommand() {
        String url = "http://192.168.52.15:8989/SUI/perlaws/samples/sample_iTunes.mov";
        String request = new PlayCommand(url, 0.0).request();
        System.out.println(request);
        Assert.assertEquals("POST /play HTTP/1.1\n" +
                "Content-Length: 107\n" +
                "User-Agent: MediaControl/1.0\n" +
                "\n" +
                "Content-Location: http://192.168.52.15:8989/SUI/perlaws/samples/sample_iTunes.mov\n" +
                "Start-Position: 0,000000" +
                "\n", request);
    }

    @Test
    public void testScrubCommand() {
        String request = new ScrubCommand().request();
        System.out.println(request);
        Assert.assertEquals("GET /scrub HTTP/1.1\n" +
                "Content-Length: 0\n" +
                "User-Agent: MediaControl/1.0\n", request);
    }
}
