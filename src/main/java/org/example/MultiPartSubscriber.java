package org.example;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class MultiPartSubscriber {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(ZMQ.SUB);
            subscriber.connect("tcp://localhost:5556");
            subscriber.subscribe("weather".getBytes(ZMQ.CHARSET)); // Subscribe to the "weather" topic

            while (true) {
                // Receive each part of the message
                String topic = subscriber.recvStr();
                String location = subscriber.recvStr();
                String temperature = subscriber.recvStr();

                System.out.println("Received message:");
                System.out.println("  Topic: " + topic);
                System.out.println("  Location: " + location);
                System.out.println("  Temperature: " + temperature);
            }
        }
    }
}
