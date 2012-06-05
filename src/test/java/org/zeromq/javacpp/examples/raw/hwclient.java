package org.zeromq.javacpp.examples.raw;

import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacpp.Pointer;

import static org.zeromq.javacpp.Javacpp.*;

public class hwclient {
    public static void main(String[] args) {
        Pointer context = zmq_init (1);

        //  Socket to talk to server
        System.out.print("Connecting to hello world server…\n");
        Pointer requester = zmq_socket (context, ZMQ_REQ);
        zmq_connect (requester, "tcp://localhost:5555");

        int request_nbr;
        for (request_nbr = 0; request_nbr != 10; request_nbr++) {
            zmq_msg_t request = new zmq_msg_t();
            zmq_msg_init_size (request, 5);
            new BytePointer(zmq_msg_data (request)).put("Hello".getBytes());
            System.out.print(String.format("Sending Hello %d…\n", request_nbr));
            zmq_send (requester, request, 0);
            zmq_msg_close (request);

            zmq_msg_t reply = new zmq_msg_t();
            zmq_msg_init (reply);
            zmq_recv (requester, reply, 0);
            System.out.print(String.format("Received World %d\n", request_nbr));
            zmq_msg_close (reply);
        }
        zmq_close (requester);
        zmq_term (context);
    }
}
