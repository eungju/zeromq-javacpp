package zeromq.javacpp.examples.raw;

import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacpp.Pointer;

import static zeromq.javacpp.ZmqJavacpp.*;

public class hwserver {
    public static void main(String[] args) throws Exception {
        Pointer context = zmq_init (1);

        //  Socket to talk to clients
        Pointer responder = zmq_socket (context, ZMQ_REP);
        zmq_bind (responder, "tcp://*:5555");

        while (true) {
            //  Wait for next request from client
            zmq_msg_t request = new zmq_msg_t();
            zmq_msg_init (request);
            zmq_recv (responder, request, 0);
            System.out.print ("Received Hello\n");
            zmq_msg_close (request);

            //  Do some 'work'
            Thread.sleep (1 * 1000);

            //  Send reply back to client
            zmq_msg_t reply = new zmq_msg_t();
            zmq_msg_init_size (reply, 5);
            new BytePointer(zmq_msg_data (reply)).put("World".getBytes(), 0, 5);
            zmq_send (responder, reply, 0);
            zmq_msg_close (reply);
        }
        //  We never get here but if we did, this would be how we end
//        zmq_close (responder);
//        zmq_term (context);
    }
}
