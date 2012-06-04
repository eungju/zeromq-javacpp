package org.zeromq.javacpp;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ZeroMqTest {
    @Test public void
    version() {
        int[] version = ZeroMq.version();
        System.out.println(String.format("%d.%d.%d", version[0], version[1], version[2]));
        assertThat(version[0], is(2));
    }

    @Test public void
    errno() {
        System.out.println(ZeroMq.errno());
    }

    @Test public void
    strerrno() {
        System.out.println(ZeroMq.strerror(1));
    }
}
