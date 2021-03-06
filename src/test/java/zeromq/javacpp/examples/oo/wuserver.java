package zeromq.javacpp.examples.oo;

import zeromq.javacpp.Zmq;
import zeromq.javacpp.ZmqContext;
import zeromq.javacpp.ZmqSocket;

import java.util.Random;

/**
 * Weather update server
 * Binds PUB socket to tcp://*:5556
 * Publishes random weather updates
 */
public class wuserver {
    public static void main(String[] args) {
        //  Prepare our context and publisher
        ZmqContext context = Zmq.init(1);
        try {
            ZmqSocket publisher = context.socket(Zmq.PUB);
            try {
                publisher.bind("tcp://*:5556");
                publisher.bind("ipc://weather.ipc");

                //  Initialize random number generator
                Random random = new Random(System.currentTimeMillis());
                while (true) {
                    //  Get values that will fool the boss
                    int zipcode, temperature, relhumidity;
                    zipcode     = random.nextInt(100000);
                    temperature = random.nextInt(215) - 80;
                    relhumidity = random.nextInt(50) + 10;

                    //  Send message to all subscribers
                    String update = String.format("%05d %d %d", zipcode, temperature, relhumidity);
                    publisher.send(update.getBytes(), 0);
                }
            } finally {
                publisher.close();
            }
        } finally {
            context.close();
        }
    }
}
