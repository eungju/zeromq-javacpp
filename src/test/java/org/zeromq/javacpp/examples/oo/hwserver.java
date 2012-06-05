package org.zeromq.javacpp.examples.oo;

import com.google.common.io.Closeables;
import org.zeromq.javacpp.Zmq;
import org.zeromq.javacpp.ZmqContext;
import org.zeromq.javacpp.ZmqMsg;
import org.zeromq.javacpp.ZmqSocket;

public class hwserver {
    public static void main(String[] args) throws Exception {
        ZmqContext context = new ZmqContext(1);
        try {
            //  Socket to talk to clients
            ZmqSocket responder = new ZmqSocket(context, Zmq.REP);
            try {
                responder.bind("tcp://*:5555");

                while (true) {
                    //  Wait for next request from client
                    ZmqMsg request = new ZmqMsg();
                    try {
                        responder.recv(request, 0);
                        System.out.print(String.format("Received %s\n", new String(request.data())));
                    } finally {
                        Closeables.closeQuietly(request);
                    }

                    //  Do some 'work'
                    Thread.sleep (1 * 1000);

                    //  Send reply back to client
                    ZmqMsg reply = new ZmqMsg("World".getBytes());
                    try {
                        responder.send(reply, 0);
                    } finally {
                        Closeables.closeQuietly(reply);
                    }
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
