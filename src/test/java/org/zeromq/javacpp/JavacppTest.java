package org.zeromq.javacpp;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.zeromq.javacpp.Javacpp.*;

public class JavacppTest {
    @Test
    public void
    errno() {
        assertThat(zmq_errno(), is(2));
    }

    @Test public void
    strerrno() {
        assertThat(zmq_strerror(2), is("No such file or directory"));
    }
}
