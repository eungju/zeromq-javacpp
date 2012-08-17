package org.zeromq.javacpp;

import com.googlecode.javacpp.IntPointer;
import com.googlecode.javacpp.Loader;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class Zmq {
    static { Loader.load(ZmqJavacpp.class); }

    /*  Socket types. */
    public static final int PAIR = ZMQ_PAIR,
            PUB = ZMQ_PUB,
            SUB = ZMQ_SUB,
            REQ = ZMQ_REQ,
            REP = ZMQ_REP,
            DEALER = ZMQ_DEALER,
            ROUTER = ZMQ_ROUTER,
            PULL = ZMQ_PULL,
            PUSH = ZMQ_PUSH,
            XPUB = ZMQ_XPUB,
            XSUB = ZMQ_XSUB,
            XREQ = ZMQ_XREQ,
            XREP = ZMQ_XREP,
            UPSTREAM = ZMQ_UPSTREAM,
            DOWNSTREAM = ZMQ_DOWNSTREAM;

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

    static void throwIfNotZero(int rc) {
        if (rc != 0) {
            int errno = zmq_errno();
            throw new ZmqException(zmq_strerror(errno), errno);
        }
    }

    static void throwIfNull(Object o) {
        if (o == null) {
            int errno = zmq_errno();
            throw new ZmqException(zmq_strerror(errno), errno);
        }
    }
}
