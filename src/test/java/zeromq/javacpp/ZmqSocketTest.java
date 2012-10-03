package zeromq.javacpp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static zeromq.javacpp.ZmqJavacpp.*;

public class ZmqSocketTest {
    ZmqContext context;

    @Before public void beforeEach() {
        context = new ZmqContext(1);
    }

    @After public void afterEach() {
        context.close();
    }

    @Test public void
    connect() {
        ZmqSocket socket = new ZmqSocket(context, ZMQ_REQ);
        try {
            socket.connect("tcp://localhost:5555");
        } finally {
            socket.close();
        }
    }

    @Test public void
    bind() {
        ZmqSocket socket = new ZmqSocket(context, ZMQ_REP);
        try {
            socket.bind("tcp://*:5555");
        } finally {
            socket.close();
        }
    }

    @Test public void
    send_and_recv_msg() {
        byte[] data = new byte[] { 1, 2, 3 };

        ZmqSocket responder = new ZmqSocket(context, ZMQ_REP);
        try {
            responder.bind("tcp://*:5555");

            ZmqSocket requester = new ZmqSocket(context, ZMQ_REQ);
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

    @Test public void
    send_and_recv_byte_array() {
        byte[] request = new byte[] { 1, 2, 3 };

        ZmqSocket responder = new ZmqSocket(context, ZMQ_REP);
        try {
            responder.bind("tcp://*:5555");

            ZmqSocket requester = new ZmqSocket(context, ZMQ_REQ);
            try {
                requester.connect("tcp://localhost:5555");
                requester.send(request, 0);
            } finally {
                requester.close();
            }

            byte[] reply = responder.recv(0);
            assertArrayEquals(request, reply);
        } finally {
            responder.close();
        }
    }
}
