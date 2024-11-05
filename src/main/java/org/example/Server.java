package org.example;

import org.zeromq.ZMQ;

public class Server {
    public static void main(String[] args) throws InterruptedException
    {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket repSocket = context.socket(ZMQ.REP);
        repSocket.bind("tcp://*:5555");

        while (!Thread.currentThread().isInterrupted()) {
//            Thread.sleep(15000);
            String request = repSocket.recvStr();
            System.out.println("Received request: " + request);
            repSocket.send("World");
        }

        repSocket.close();

        context.close();
//        context.term();
    }
}
