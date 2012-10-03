package zeromq.javacpp;

import com.googlecode.javacpp.Pointer;

import java.io.Closeable;

import static zeromq.javacpp.ZmqJavacpp.*;

public class ZmqContext implements Closeable {
    final Pointer underlying;

    ZmqContext(int ioThreads) {
        underlying = zmq_init(ioThreads);
        Zmq.throwIfNull(underlying);
    }

    public void close() {
        int rc = zmq_term(underlying);
        Zmq.throwIfNotZero(rc);
    }

    public ZmqSocket socket(int type) {
        return new ZmqSocket(this, type);
    }
}
