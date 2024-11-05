package org.example;

import io.vertx.core.json.JsonObject;
import org.zeromq.ZMQ;

public class JsonReceiver
{
    public static void main(String[] args)
    {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket pullSocket = context.socket(ZMQ.PULL);

        pullSocket.connect("tcp://localhost:5555");

        byte[] receivedBytes = pullSocket.recv(0);
        String receivedString = new String(receivedBytes,ZMQ.CHARSET);

        JsonObject receivedJson = new JsonObject(receivedString);
        System.out.println("Received JSON: "+receivedJson.encodePrettily());

        pullSocket.close();
        context.close();

    }
}
