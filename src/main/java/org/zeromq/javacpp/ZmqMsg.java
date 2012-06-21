package org.zeromq.javacpp;

import com.googlecode.javacpp.BytePointer;

import java.io.Closeable;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class ZmqMsg implements Closeable {
    final zmq_msg_t underlying;

    public ZmqMsg() {
        underlying = new zmq_msg_t();
        int rc = zmq_msg_init(underlying);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public ZmqMsg(byte[] data) {
        underlying = new zmq_msg_t();
        int rc = zmq_msg_init_data(underlying, new BytePointer(data), data.length, null, null);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public void close() {
        int rc = zmq_msg_close(underlying);
        if (rc != 0) {
            throw new ZmqException(zmq_errno());
        }
    }

    public int size() {
        return zmq_msg_size(underlying);
    }

    public byte[] data() {
        BytePointer dataPtr = new BytePointer(zmq_msg_data(underlying));
        byte[] data = new byte[zmq_msg_size(underlying)];
        dataPtr.get(data);
        return data;
    }
}
