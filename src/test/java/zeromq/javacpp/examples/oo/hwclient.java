package zeromq.javacpp.examples.oo;

import com.google.common.io.Closeables;
import zeromq.javacpp.Zmq;
import zeromq.javacpp.ZmqContext;
import zeromq.javacpp.ZmqSocket;

public class hwclient {
    public static void main(String[] args) {
        ZmqContext context = Zmq.init(1);
        try {
            //  Socket to talk to server
            System.out.print("Connecting to hello world server…\n");
            ZmqSocket requester = context.socket(Zmq.REQ);
            try {
                requester.connect("tcp://localhost:5555");

                for (int request_nbr = 0; request_nbr != 10; request_nbr++) {
                    byte[] request = "Hello".getBytes();
                    System.out.print(String.format("Sending %s %d…\n", new String(request), request_nbr));
                    requester.send(request, 0);

                    byte[] reply = requester.recv(0);
                    System.out.print(String.format("Received %s %d\n", new String(reply), request_nbr));
                }
            } finally {
                Closeables.closeQuietly(requester);
            }
        } finally {
            Closeables.closeQuietly(context);
        }
    }
}
