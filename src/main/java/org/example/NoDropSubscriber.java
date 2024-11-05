package org.example;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class NoDropSubscriber {

    public static void main(String[] args) throws InterruptedException {
        try (ZContext context = new ZContext()) {
            // Connect to publisher's XPUB socket
            ZMQ.Socket subSocket = context.createSocket(ZMQ.SUB);
            subSocket.connect("tcp://localhost:5555");
            subSocket.subscribe("".getBytes(ZMQ.CHARSET));  // Subscribe to all messages

            while (!Thread.currentThread().isInterrupted()) {
                String receivedMessage = subSocket.recvStr();
                System.out.println("Received: " + receivedMessage);
                Thread.sleep(5000);  // Slow down subscriber to simulate high-water mark conditions
            }
        }
    }
}
