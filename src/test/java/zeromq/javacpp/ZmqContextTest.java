package zeromq.javacpp;

import org.junit.Test;

public class ZmqContextTest {
    @Test public void
    lifecycle() {
        ZmqContext context = new ZmqContext(1);
        try {
        } finally {
            context.close();
        }
    }
}
