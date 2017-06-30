package innossh.reverseproxy.mockserver.util;


import org.junit.rules.ExternalResource;
import org.mockserver.client.server.MockServerClient;

public class MockServerClients extends ExternalResource {

    private MockServerClient backend1 = new MockServerClient("localhost", 1081);
    private MockServerClient backend2 = new MockServerClient("localhost", 1082);

    @Override
    protected void before() throws Throwable {
        backend1.reset();
        backend2.reset();
    }

    public MockServerClient getBackend1() {
        return backend1;
    }

    public MockServerClient getBackend2() {
        return backend2;
    }

}
