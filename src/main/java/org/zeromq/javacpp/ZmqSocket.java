package org.zeromq.javacpp;

import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacpp.Pointer;

import java.io.Closeable;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class ZmqSocket implements Closeable {
    final Pointer underlying;

    ZmqSocket(ZmqContext context, int type) {
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

    public void subscribe(byte[] filter) {
        BytePointer valuePtr = new BytePointer(filter);
        int rc = zmq_setsockopt(underlying, ZmqJavacpp.ZMQ_SUBSCRIBE, valuePtr, filter.length);
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

    public void send(byte[] data, int flags) {
        ZmqMsg msg = new ZmqMsg(data);
        try {
            send(msg, flags);
        } finally {
            msg.close();
        }
    }

    public byte[] recv(int flags) {
        ZmqMsg msg = new ZmqMsg();
        try {
            recv(msg, flags);
            return msg.data();
        } finally {
            msg.close();
        }
    }
}
