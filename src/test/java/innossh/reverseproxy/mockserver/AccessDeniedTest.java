package innossh.reverseproxy.mockserver;

import innossh.reverseproxy.mockserver.util.MockServerClients;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.verify.VerificationTimes;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(JUnitParamsRunner.class)
public class AccessDeniedTest {

    @Rule
    public MockServerClients clients = new MockServerClients();

    @Test
    public void testNormalToBackend1() throws IOException {
        clients.getBackend1()
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/foo")
                )
                .respond(
                        response()
                                .withStatusCode(204)
                );

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost/backend1/foo")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        assertEquals(204, response.code());
        clients.getBackend1().verify(
                request()
                        .withMethod("GET")
                        .withPath("/foo"),
                VerificationTimes.once()
        );
    }

    @Test
    public void testNormalToBackend2() throws IOException {
        clients.getBackend2()
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/bar")
                )
                .respond(
                        response()
                                .withStatusCode(204)
                );

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost/backend2/bar")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        assertEquals(204, response.code());
        clients.getBackend2().verify(
                request()
                        .withMethod("GET")
                        .withPath("/bar"),
                VerificationTimes.once()
        );
    }

    @Test
    @Parameters({
            "1.2.3.4,204",
            "4.3.2.1,403"
    })
    public void testAccessDenied(String clientIP, int expectedStatus) throws IOException {
        clients.getBackend2()
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/bar")
                )
                .respond(
                        response()
                                .withStatusCode(204)
                );

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("X-Forwarded-For", clientIP)
                .url("http://localhost/backend2/bar")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        assertEquals(expectedStatus, response.code());
        clients.getBackend2().verify(
                request()
                        .withMethod("GET")
                        .withPath("/bar"),
                VerificationTimes.exactly(expectedStatus == 204 ? 1 : 0)
        );
    }

}
