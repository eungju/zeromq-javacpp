package org.zeromq.javacpp;

import com.googlecode.javacpp.Pointer;

import java.io.Closeable;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class ZmqContext implements Closeable {
    final Pointer underlying;

    public ZmqContext(int ioThreads) {
        underlying = zmq_init(ioThreads);
        if (underlying == null) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void close() {
        if (zmq_term(underlying) != 0) {
            throw new ZmqException(zmq_errno());
        }
    }
}
