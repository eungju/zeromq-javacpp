package org.zeromq.javacpp;

import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacpp.Pointer;

import java.io.Closeable;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class ZmqSocket implements Closeable {
    final Pointer underlying;

    ZmqSocket(ZmqContext context, int type) {
        underlying = zmq_socket(context.underlying, type);
        Zmq.throwIfNull(underlying);
    }

    public void close() {
        int rc = zmq_close(underlying);
        Zmq.throwIfNotZero(rc);
    }

    public void subscribe(byte[] filter) {
        BytePointer valuePtr = new BytePointer(filter);
        int rc = zmq_setsockopt(underlying, ZmqJavacpp.ZMQ_SUBSCRIBE, valuePtr, filter.length);
        Zmq.throwIfNotZero(rc);
    }

    public void bind(String addr) {
        int rc = zmq_bind(underlying, addr);
        Zmq.throwIfNotZero(rc);
    }

    public void connect(String addr) {
        int rc = zmq_connect(underlying, addr);
        Zmq.throwIfNotZero(rc);
    }

    public void send(ZmqMsg msg, int flags) {
        int rc = zmq_send(underlying, msg.underlying, flags);
        Zmq.throwIfNotZero(rc);
    }

    public void recv(ZmqMsg msg, int flags) {
        int rc = zmq_recv(underlying, msg.underlying, flags);
        Zmq.throwIfNotZero(rc);
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
