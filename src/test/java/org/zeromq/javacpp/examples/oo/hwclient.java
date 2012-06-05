package org.zeromq.javacpp.examples.oo;

import com.google.common.io.Closeables;
import org.zeromq.javacpp.Zmq;
import org.zeromq.javacpp.ZmqContext;
import org.zeromq.javacpp.ZmqMsg;
import org.zeromq.javacpp.ZmqSocket;

public class hwclient {
    public static void main(String[] args) {
        ZmqContext context = new ZmqContext(1);
        try {
            //  Socket to talk to server
            System.out.print("Connecting to hello world server…\n");
            ZmqSocket requester = new ZmqSocket(context, Zmq.REQ);
            try {
                requester.connect("tcp://localhost:5555");

                for (int request_nbr = 0; request_nbr != 10; request_nbr++) {
                    ZmqMsg request = new ZmqMsg("Hello".getBytes());
                    try {
                        System.out.print(String.format("Sending %s %d…\n", new String(request.data()), request_nbr));
                        requester.send(request, 0);
                    } finally {
                        Closeables.closeQuietly(request);
                    }

                    ZmqMsg reply = new ZmqMsg();
                    try {
                        requester.recv(reply, 0);
                        System.out.print(String.format("Received %s %d\n", new String(reply.data()), request_nbr));
                    } finally {
                        Closeables.closeQuietly(reply);
                    }
                }
            } finally {
                Closeables.closeQuietly(requester);
            }
        } finally {
            Closeables.closeQuietly(context);
        }
    }
}
