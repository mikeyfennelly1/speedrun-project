package com.mfennelly;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

enum ConcurrencyStyle {
    Fixed, Cached, Single, Scheduled, SingleThreadScheduled;

    static class UnrecognisedConcurrencyStyleException extends RuntimeException {
        public UnrecognisedConcurrencyStyleException(String errorMsg) {
        }
    };

    public static ConcurrencyStyle parseConcurrencyStyle(String concurrencyStyleStr) throws UnrecognisedConcurrencyStyleException
    {
        ConcurrencyStyle cs = null;
        if (concurrencyStyleStr == "fixed")
            cs = Fixed;
        if (concurrencyStyleStr == "cached")
            cs = Cached;
        if (concurrencyStyleStr == "single")
            cs = Single;
        if (concurrencyStyleStr == "scheduled")
            cs = Scheduled;
        if (concurrencyStyleStr == "singleThreadScheduled")
            cs = SingleThreadScheduled;

        if (cs == null)
            throw new UnrecognisedConcurrencyStyleException("Unrecognised ConcurrencyStyle: " + concurrencyStyleStr);
        return cs;
    }
}

public class SysinfoServer
{
    int portNum;
    ConcurrencyStyle concurrencyStyle;

    public SysinfoServer(int portNum, ConcurrencyStyle concurrencyStyle)
    {
        this.portNum = portNum;
        this.concurrencyStyle = concurrencyStyle;
        ExecutorService threadPool = getThreadPool();
    }

    /**
     * Start server on a socket.
     * @return ServerSocket - the socket that the server is running on.
     * @throws java.io.IOException
     */
    public ServerSocket start() throws java.io.IOException
    {
        ServerSocket serverSocket = new ServerSocket(portNum);
        System.out.println("Server started on port " + portNum);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + serverSocket.getInetAddress());

            new Thread(new SysinfoHandler(clientSocket)).start();
        }
    }

    /***
     * Get a thread pool based on one of the specified concurrency styles.
     *
     * @return An ExecutorService based on the concurrency style you request
     */
    private ExecutorService getThreadPool()
    {
        ExecutorService es = switch (this.concurrencyStyle)
        {
            case Fixed -> Executors.newFixedThreadPool(10);
            case Cached -> Executors.newCachedThreadPool();
            case Single -> Executors.newSingleThreadExecutor();
            case Scheduled -> Executors.newScheduledThreadPool(10);
            case SingleThreadScheduled -> Executors.newSingleThreadScheduledExecutor();
        };

        return es;
    }


}