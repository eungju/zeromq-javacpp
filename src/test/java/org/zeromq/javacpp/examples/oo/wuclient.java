package org.zeromq.javacpp.examples.oo;

import org.zeromq.javacpp.Zmq;
import org.zeromq.javacpp.ZmqContext;
import org.zeromq.javacpp.ZmqSocket;

/**
 * Weather update client
 * Connects SUB socket to tcp://localhost:5556
 * Collects weather updates and finds avg temp in zipcode
 */
public class wuclient {
    public static void main(String[] args) {
        ZmqContext context = Zmq.init(1);
        try {
            //  Socket to talk to server
            System.out.println("Collecting updates from weather server…");
            ZmqSocket subscriber = context.sub();
            try {
                subscriber.connect("tcp://localhost:5556");

                //  Subscribe to zipcode, default is NYC, 10001
                String filter = (args.length > 0) ? args[0] : "10001 ";
                subscriber.subscribe(filter.getBytes());

                //  Process 100 updates
                int update_nbr;
                long total_temp = 0;
                for (update_nbr = 0; update_nbr < 100; update_nbr++) {
                    String string = new String(subscriber.recv(0));
                    String[] fields = string.split(" ");
                    int zipcode = Integer.parseInt(fields[0]);
                    int temperature = Integer.parseInt(fields[1]);
                    int relhumidity = Integer.parseInt(fields[2]);
                    total_temp += temperature;
                }
                System.out.println(String.format("Average temperature for zipcode '%s' was %dF", filter, (int) (total_temp / update_nbr)));
            } finally {
                subscriber.close();
            }
        } finally {
            context.close();
        }
    }
}
