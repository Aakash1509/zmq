package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.zeromq.ZMQ;

import java.util.Arrays;

public class Subscriber extends AbstractVerticle
{
    public void start()
    {
        vertx.executeBlocking(promise ->
        {
            ZMQ.Context context1 = ZMQ.context(2);
            ZMQ.Socket subscriber = context1.socket(ZMQ.SUB);
            subscriber.connect("tcp://localhost:5556");
            subscriber.subscribe("".getBytes(ZMQ.CHARSET));
            while (!Thread.currentThread().isInterrupted()) {
                byte[] msg = subscriber.recv(0);
                if (msg != null) {
//                    String message = new String(msg, ZMQ.CHARSET);
                    System.out.println("Received: " + Arrays.toString(msg));
//                    subscriber.send("Hello");

                    // Introduce a delay to slow down the subscriber
                    try {
                        Thread.sleep(1000); // Delay for 1 second (1000 milliseconds)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            subscriber.close();
            context1.close();
            promise.complete();
        });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Subscriber());
    }
}
