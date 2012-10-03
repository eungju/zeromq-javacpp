package zeromq.javacpp;

public class ZmqException extends RuntimeException {
    private final int errno;

    public ZmqException(String message, int errno) {
        super(message);
        this.errno = errno;
    }

    public int getErrno() {
        return errno;
    }
}
