package org.example;

import org.zeromq.ZMQ;

public class SubDontWait {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:5555");

        // Subscribe to all messages
        subscriber.subscribe("".getBytes(ZMQ.CHARSET));

        while (true) {
            String receivedMessage = subscriber.recvStr();
            System.out.println("Received: " + receivedMessage);
        }
    }
}
