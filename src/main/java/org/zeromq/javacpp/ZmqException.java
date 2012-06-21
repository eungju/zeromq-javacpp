package org.zeromq.javacpp;

import static org.zeromq.javacpp.ZmqJavacpp.*;

public class ZmqException extends RuntimeException {
    private final int errnum;

    public ZmqException(int errnum) {
        super(zmq_strerror(errnum));
        this.errnum = errnum;
    }

    public int getErrnum() {
        return errnum;
    }
}
