package net.therpservices.simpleWebserver.server;

import net.therpservices.simpleWebserver.client.ClientRequestProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: rifatul.islam
 * Date: 5/6/14
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerRunner {
    private static final int TOTAL_THREAD = 20;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(TOTAL_THREAD);
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            System.out.println("Server Started...");

            while (true) {
                executor.execute(new ClientRequestProcessor(serverSocket.accept()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
