package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

public class PullSocket extends AbstractVerticle {
    @Override
    public void start() {
        vertx.executeBlocking(promise -> {
            // Create a ZeroMQ context with 1 I/O thread
            ZContext context = new ZContext();

            // Create a PUSH socket
            ZMQ.Socket pullSocket = context.createSocket(SocketType.PULL);
            pullSocket.bind("tcp://localhost:5555");// Connect to the producer at port 5557

            System.out.println("Worker is ready to receive messages...");

            int count = 0;

            while (!Thread.currentThread().isInterrupted()) {
                String message = "Task-" + count++;
                System.out.println("Sending: " + message);
                pullSocket.send(message.getBytes(ZMQ.CHARSET),ZMQ.DONTWAIT);
                // Receive a message
//                String receivedMessage = pullSocket.recvStr(0);
//                System.out.println("Worker 1 received: " + receivedMessage);

                // Simulate some work being done
                try {
                    Thread.sleep(500); // Processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Close the socket and context
            pullSocket.close();
            context.close();
            promise.complete();
        });
    }

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions().setMaxWorkerExecuteTime(150).setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);
        Vertx vertx = Vertx.vertx(options);
        vertx.deployVerticle("org.example.PullSocket");
    }
}
