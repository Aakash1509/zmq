package org.example;

import org.zeromq.ZMQ;

public class Requester
{
    public static void main(String[] args)
    {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:5555");

        while (!Thread.currentThread().isInterrupted())
        {

            String request = "Hello from the client!";
            System.out.println("Sending request: " + request);
            requester.send(request.getBytes(ZMQ.CHARSET), 0);


            byte[] reply = requester.recv(0);
            System.out.println("Received reply: " + new String(reply, ZMQ.CHARSET));
        }

        // Close the socket and context
        requester.close();
        context.close();
    }
}
