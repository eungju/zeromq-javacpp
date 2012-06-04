package org.zeromq.javacpp;

import com.googlecode.javacpp.IntPointer;
import com.googlecode.javacpp.Loader;

public class ZeroMq {
    static { Loader.load(Javacpp.class); }

    public static int[] version() {
        IntPointer major = new IntPointer(1);
        IntPointer minor = new IntPointer(1);
        IntPointer patch = new IntPointer(1);
        Javacpp.zmq_version(major, minor, patch);
        return new int[] { major.get(), minor.get(), patch.get() };
    }

    public static int errno() {
        return Javacpp.zmq_errno();
    }

    public static String strerror(int errnum) {
        return Javacpp.zmq_strerror(errnum);
    }
}
