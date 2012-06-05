package org.zeromq.javacpp;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.zeromq.javacpp.Javacpp.*;

public class ZmqTest {
    @Test public void
    version() {
        int[] version = Zmq.version();
        System.out.println(String.format("%d.%d.%d", version[0], version[1], version[2]));
        assertThat(version[0], is(2));
    }
}
