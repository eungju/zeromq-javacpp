package org.zeromq.javacpp.examples.oo;

import com.google.common.io.Closeables;
import org.zeromq.javacpp.Zmq;
import org.zeromq.javacpp.ZmqContext;
import org.zeromq.javacpp.ZmqSocket;

public class hwserver {
    public static void main(String[] args) throws Exception {
        ZmqContext context = Zmq.init(1);
        try {
            //  Socket to talk to clients
            ZmqSocket responder = context.socket(Zmq.REP);
            try {
                responder.bind("tcp://*:5555");

                while (true) {
                    //  Wait for next request from client
                    byte[] request = responder.recv(0);
                    System.out.print(String.format("Received %s\n", new String(request)));

                    //  Do some 'work'
                    Thread.sleep (1 * 1000);

                    //  Send reply back to client
                    responder.send("World".getBytes(), 0);
                }
                //  We never get here but if we did, this would be how we end
            } finally {
                Closeables.closeQuietly(responder);
            }
        } finally {
            Closeables.closeQuietly(context);
        }
    }
}
