package org.zeromq.javacpp;

import com.googlecode.javacpp.IntPointer;
import com.googlecode.javacpp.Loader;

import static org.zeromq.javacpp.Javacpp.*;

public class Zmq {
    static { Loader.load(Javacpp.class); }

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

    /*  Socket options. */
    public static final int HWM = ZMQ_HWM,
            SWAP = ZMQ_SWAP,
            AFFINITY = ZMQ_AFFINITY,
            IDENTITY = ZMQ_IDENTITY,
            SUBSCRIBE = ZMQ_SUBSCRIBE,
            UNSUBSCRIBE = ZMQ_UNSUBSCRIBE,
            RATE = ZMQ_RATE,
            RECOVERY_IVL = ZMQ_RECOVERY_IVL,
            MCAST_LOOP = ZMQ_MCAST_LOOP,
            SNDBUF = ZMQ_SNDBUF,
            RCVBUF = ZMQ_RCVBUF,
            RCVMORE = ZMQ_RCVMORE,
            FD = ZMQ_FD,
            EVENTS = ZMQ_EVENTS,
            TYPE = ZMQ_TYPE,
            LINGER = ZMQ_LINGER,
            RECONNECT_IVL = ZMQ_RECONNECT_IVL,
            BACKLOG = ZMQ_BACKLOG,
            RECOVERY_IVL_MSEC = ZMQ_RECOVERY_IVL_MSEC,
            RECONNECT_IVL_MAX = ZMQ_RECONNECT_IVL_MAX,
            RCVTIMEO = ZMQ_RCVTIMEO,
            SNDTIMEO = ZMQ_SNDTIMEO;

    /*  Send/recv options. */
    public static final int NOBLOCK = ZMQ_NOBLOCK,
            SNDMORE = ZMQ_SNDMORE;

    public static int[] version() {
        IntPointer version = new IntPointer(3);
        IntPointer major = new IntPointer(version.position(0));
        IntPointer minor = new IntPointer(version.position(1));
        IntPointer patch = new IntPointer(version.position(2));
        Javacpp.zmq_version(major, minor, patch);
        return new int[] { major.get(), minor.get(), patch.get() };
    }
}
