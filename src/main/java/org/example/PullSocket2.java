// Worker2 (PullSocket2.java)
package org.example;

import org.zeromq.ZMQ;

public class PullSocket2 {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        // Create a PULL socket
        ZMQ.Socket pullSocket2 = context.socket(ZMQ.PULL);
        pullSocket2.connect("tcp://localhost:5557"); // Connect to the PUSH socket

        // Receive messages
        while (true) {
            String receivedMessage = pullSocket2.recvStr(0);
            System.out.println("Worker 2 received: " + receivedMessage);
        }
    }
}
