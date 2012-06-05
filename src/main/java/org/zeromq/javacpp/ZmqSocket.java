package org.zeromq.javacpp;

import com.googlecode.javacpp.Pointer;

import java.io.Closeable;

import static org.zeromq.javacpp.Javacpp.*;

public class ZmqSocket implements Closeable {
    final Pointer underlying;

    public ZmqSocket(ZmqContext context, int type) {
        underlying = zmq_socket(context.underlying, type);
        if (underlying == null) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void close() {
        int rc = zmq_close(underlying);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void bind(String addr) {
        int rc = zmq_bind(underlying, addr);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void connect(String addr) {
        int rc = zmq_connect(underlying, addr);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void send(ZmqMsg msg, int flags) {
        int rc = zmq_send(underlying, msg.underlying, flags);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void recv(ZmqMsg msg, int flags) {
        int rc = zmq_recv(underlying, msg.underlying, flags);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }
}
