// Worker1 (PullSocket1.java)
package org.example;

import org.zeromq.ZMQ;

public class PullSocket1 {
    public static void main(String[] args) throws InterruptedException
    {
        ZMQ.Context context = ZMQ.context(1);

        // Create a PULL socket
        ZMQ.Socket pullSocket1 = context.socket(ZMQ.PULL);
        pullSocket1.connect("tcp://localhost:5557"); // Connect to the PUSH socket

        // Receive messages
        while (true) {
            String receivedMessage = pullSocket1.recvStr(0);
            System.out.println("Worker 1 received: " + receivedMessage);
            Thread.sleep(100);
        }
    }
}
