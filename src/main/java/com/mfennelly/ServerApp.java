package com.mfennelly;

import java.io.IOException;

public class ServerApp {
    private static final int PORT_NUM = 8080;
    private static final ConcurrencyStyle concurrencyStyle = ConcurrencyStyle.Fixed;

    public static void main(String[] args) throws IOException {
        SysinfoServer sysinfoServer = new SysinfoServer(PORT_NUM, concurrencyStyle);
        sysinfoServer.start();
    }
}
