package org.zeromq.javacpp;

import com.googlecode.javacpp.IntPointer;
import com.googlecode.javacpp.Loader;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class Zmq {
    static { Loader.load(ZmqJavacpp.class); }

    /*  Send/recv options. */
    public static final int NOBLOCK = ZMQ_NOBLOCK,
            SNDMORE = ZMQ_SNDMORE;

    public static int[] version() {
        IntPointer version = new IntPointer(3);
        IntPointer major = new IntPointer(version.position(0));
        IntPointer minor = new IntPointer(version.position(1));
        IntPointer patch = new IntPointer(version.position(2));
        zmq_version(major, minor, patch);
        return new int[] { major.get(), minor.get(), patch.get() };
    }

    public static ZmqContext init(int ioThreads) {
        return new ZmqContext(ioThreads);
    }
}
