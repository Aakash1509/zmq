package org.example;

import org.zeromq.ZMQ;

public class SubscriberA {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        // Connect to the publisher and subscribe to all messages
        subscriber.connect("tcp://localhost:5556");
        subscriber.subscribe("".getBytes(ZMQ.CHARSET)); // Subscribe to all messages

        System.out.println("Subscriber A connected and ready to receive messages...");

        // Simulate consuming messages at a slower pace (100 messages per second)
        int receivedCount = 0;
        while (true) {
            byte[] message = subscriber.recv(0);
            System.out.println("Subscriber A received: " + new String(message, ZMQ.CHARSET));
            receivedCount++;

            // Simulate slow consumption rate
            Thread.sleep(10);
        }
    }
}
