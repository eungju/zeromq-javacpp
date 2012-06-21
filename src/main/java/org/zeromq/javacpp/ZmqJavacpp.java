package org.zeromq.javacpp;

import com.googlecode.javacpp.FunctionPointer;
import com.googlecode.javacpp.IntPointer;
import com.googlecode.javacpp.Loader;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacpp.SizeTPointer;
import com.googlecode.javacpp.annotation.Cast;
import com.googlecode.javacpp.annotation.Const;
import com.googlecode.javacpp.annotation.Platform;
import com.googlecode.javacpp.annotation.Properties;

@Properties({
        @Platform(value="macosx", include = "zmq.h", link = "zmq")
})
public class ZmqJavacpp {
    static { Loader.load(); }

    public static native void zmq_version(IntPointer major, IntPointer minor, IntPointer patch);

    public static native int zmq_errno();
    public static native String zmq_strerror(int errnum);

    public static class zmq_msg_t extends Pointer {
        static { Loader.load(); }

        public zmq_msg_t() { allocate(); }
        private native void allocate();
    }

    public static class zmq_free_fn extends FunctionPointer {
        static { Loader.load(); }

        protected zmq_free_fn() { allocate(); }
        protected native void allocate();

        public native void call(Pointer data, Pointer hint);
    }

    public static native int zmq_msg_init(zmq_msg_t msg);
    public static native int zmq_msg_init_size(zmq_msg_t msg, @Cast("size_t") int size);
    public static native int zmq_msg_init_data(zmq_msg_t msg, Pointer data, @Cast("size_t") int size, zmq_free_fn ffn, Pointer hint);
    public static native int zmq_msg_close(zmq_msg_t msg);
    public static native int zmq_msg_move(zmq_msg_t dest, zmq_msg_t src);
    public static native int zmq_msg_copy(zmq_msg_t dest, zmq_msg_t src);
    public static native Pointer zmq_msg_data(zmq_msg_t msg);
    public static native @Cast("size_t") int zmq_msg_size(zmq_msg_t msg);

    public static native Pointer zmq_init(int io_threads);
    public static native int zmq_term(Pointer context);

    /*  Socket types. */
    public static final int ZMQ_PAIR = 0,
        ZMQ_PUB = 1,
        ZMQ_SUB = 2,
        ZMQ_REQ = 3,
        ZMQ_REP = 4,
        ZMQ_DEALER = 5,
        ZMQ_ROUTER = 6,
        ZMQ_PULL = 7,
        ZMQ_PUSH = 8,
        ZMQ_XPUB = 9,
        ZMQ_XSUB = 10,
        ZMQ_XREQ = ZMQ_DEALER,
        ZMQ_XREP = ZMQ_ROUTER,
        ZMQ_UPSTREAM = ZMQ_PULL,
        ZMQ_DOWNSTREAM = ZMQ_PUSH;

    /*  Socket options. */
    public static final int ZMQ_HWM = 1,
        ZMQ_SWAP = 3,
        ZMQ_AFFINITY = 4,
        ZMQ_IDENTITY = 5,
        ZMQ_SUBSCRIBE = 6,
        ZMQ_UNSUBSCRIBE = 7,
        ZMQ_RATE = 8,
        ZMQ_RECOVERY_IVL = 9,
        ZMQ_MCAST_LOOP = 10,
        ZMQ_SNDBUF = 11,
        ZMQ_RCVBUF = 12,
        ZMQ_RCVMORE = 13,
        ZMQ_FD = 14,
        ZMQ_EVENTS = 15,
        ZMQ_TYPE = 16,
        ZMQ_LINGER = 17,
        ZMQ_RECONNECT_IVL = 18,
        ZMQ_BACKLOG = 19,
        ZMQ_RECOVERY_IVL_MSEC = 20,
        ZMQ_RECONNECT_IVL_MAX = 21,
        ZMQ_RCVTIMEO = 27,
        ZMQ_SNDTIMEO = 28;

    /*  Send/recv options. */
    public static final int ZMQ_NOBLOCK = 1,
        ZMQ_SNDMORE = 2;

    public static native Pointer zmq_socket(Pointer context, int type);
    public static native int zmq_close(Pointer s);
    public static native int zmq_setsockopt(Pointer s, int option, @Const Pointer optval, @Cast("size_t") int optvallen);
    public static native int zmq_getsockopt(Pointer s, int option, Pointer optval, SizeTPointer optvallen);
    public static native int zmq_bind(Pointer s, String addr);
    public static native int zmq_connect(Pointer s, String addr);
    public static native int zmq_send(Pointer s, zmq_msg_t msg, int flags);
    public static native int zmq_recv(Pointer s, zmq_msg_t msg, int flags);
}
