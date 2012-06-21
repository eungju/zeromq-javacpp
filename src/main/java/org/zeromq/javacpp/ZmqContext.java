package org.zeromq.javacpp;

import com.googlecode.javacpp.Pointer;

import java.io.Closeable;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class ZmqContext implements Closeable {
    final Pointer underlying;

    ZmqContext(int ioThreads) {
        underlying = zmq_init(ioThreads);
        if (underlying == null) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void close() {
        int rc = zmq_term(underlying);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public ZmqSocket pair() {
        return new ZmqSocket(this, ZMQ_PAIR);
    }

    public ZmqSocket pub() {
        return new ZmqSocket(this, ZMQ_PUB);
    }

    public ZmqSocket sub() {
        return new ZmqSocket(this, ZMQ_SUB);
    }

    public ZmqSocket req() {
        return new ZmqSocket(this, ZMQ_REQ);
    }

    public ZmqSocket rep() {
        return new ZmqSocket(this, ZMQ_REP);
    }
}
