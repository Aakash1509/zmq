package org.example;

import org.zeromq.ZMQ;

public class SubHWM {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        // Set HWM to 10 messages on the subscriber side as well
        subscriber.setHWM(10);
        subscriber.connect("tcp://localhost:5556");
        subscriber.subscribe("".getBytes(ZMQ.CHARSET)); // Subscribe to all messages

        while (true) {
            byte[] msg = subscriber.recv(0);
            if (msg != null) {
                String message = new String(msg, ZMQ.CHARSET);
                System.out.println("Received: " + message);

                // Slow down the subscriber to overflow the queue
                Thread.sleep(1000);
            }
        }
    }
}
