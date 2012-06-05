package org.zeromq.javacpp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZmqSocketTest {
    ZmqContext context;

    @Before public void beforeEach() {
        context = new ZmqContext(1);
    }

    @After public void afterEach() {
        context.close();
    }

    @Test public void
    client_socket() {
        ZmqSocket socket = new ZmqSocket(context, Zmq.REQ);
        try {
            socket.connect("tcp://localhost:5555");
        } finally {
            socket.close();
        }
    }

    @Test public void
    server_socket() {
        ZmqSocket socket = new ZmqSocket(context, Zmq.REP);
        try {
            socket.bind("tcp://*:5555");
        } finally {
            socket.close();
        }
    }

    @Test public void
    send_and_recv() {
        byte[] data = new byte[] { 1, 2, 3 };

        ZmqSocket responder = new ZmqSocket(context, Zmq.REP);
        try {
            responder.bind("tcp://*:5555");

            ZmqSocket requester = new ZmqSocket(context, Zmq.REQ);
            try {
                requester.connect("tcp://localhost:5555");
                ZmqMsg request = new ZmqMsg(data);
                try {
                    requester.send(request, 0);
                } finally {
                    request.close();
                }
            } finally {
                requester.close();
            }

            ZmqMsg reply = new ZmqMsg();
            try {
                responder.recv(reply, 0);
                assertArrayEquals(data, reply.data());
            } finally {
                reply.close();
            }
        } finally {
            responder.close();
        }
    }
}
