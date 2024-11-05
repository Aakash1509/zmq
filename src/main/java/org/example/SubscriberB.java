package org.example;

import org.zeromq.ZMQ;

public class SubscriberB {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        // Simulate Subscriber B being disconnected initially
        Thread.sleep(3000); // Wait for 3 seconds to simulate disconnection

        // Connect to the publisher and subscribe to all messages
        subscriber.connect("tcp://localhost:5556");
        subscriber.subscribe("".getBytes(ZMQ.CHARSET)); // Subscribe to all messages

        System.out.println("Subscriber B reconnected and ready to receive messages...");

        // Receive messages after reconnecting
        while (true) {
            byte[] message = subscriber.recv(0);
            System.out.println("Subscriber B received: " + new String(message, ZMQ.CHARSET));
        }
    }
}
