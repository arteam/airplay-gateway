package http;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import junit.framework.Assert;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.mock.Mock;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.stream.StreamServer;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Date: 21.05.13
 * Time: 11:52
 *
 * @author Artem Prigoda
 */
public class HttpServerTest {

    private StreamServer streamServer;

    private Mock<ContentDao> contentDaoMock;

    @Before
    public void start() {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ContentDao.class).to(ContentDaoStub.class);
                bindConstant().annotatedWith(Names.named("streamPort")).to(9789);
            }
        });
        streamServer = injector.getInstance(StreamServer.class);
        streamServer.start();
    }

    @Test
    public void testRequest() throws Exception {
        String url = "http://192.168.52.248:9789/stream?code=5456";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet firstGet = new HttpGet(url);
            firstGet.addHeader("range", "bytes=0-1");
            HttpResponse response = httpClient.execute(firstGet);
            System.out.println(response);
            byte[] byteArray = EntityUtils.toByteArray(response.getEntity());

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 206);
            Assert.assertEquals(response.getEntity().getContentType().getValue(), "video/x-m4v");
            Assert.assertEquals(response.getEntity().getContentLength(), 2);
            Assert.assertEquals(response.getFirstHeader("Accept-Ranges").getValue(), "bytes");
            Assert.assertEquals(byteArray.length, 2);

            HttpGet secondGet = new HttpGet(url);
            secondGet.addHeader("range", "bytes=2-20001");
            response = httpClient.execute(secondGet);
            System.out.println(response);
            byteArray = EntityUtils.toByteArray(response.getEntity());
            Assert.assertEquals(response.getEntity().getContentLength(), 20000);
            Assert.assertEquals(byteArray.length, 20000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    @After
    public void stop() {
        streamServer.stop();
    }
}
