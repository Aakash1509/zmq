package org.example;

import org.zeromq.ZMQ;

public class LingerSubscriber {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        subscriber.connect("tcp://localhost:5556");
        subscriber.subscribe(""); // Subscribe to all messages

        while (true) {
            String receivedMessage = subscriber.recvStr();
            System.out.println("Received: " + receivedMessage);
        }
    }
}
