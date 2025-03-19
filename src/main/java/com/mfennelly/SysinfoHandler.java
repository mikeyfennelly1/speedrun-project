package com.mfennelly;

import java.net.Socket;

public class SysinfoHandler implements Runnable {

    private Socket clientSocket;

    public SysinfoHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("SysinfoHandler here, client address is: " + this.clientSocket.getInetAddress());
    }
}
