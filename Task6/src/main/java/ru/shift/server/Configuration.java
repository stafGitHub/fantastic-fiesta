package ru.shift.server;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class Configuration {
    public static void serverStart() throws IOException {

        try (var serverSocket = new ServerSocket(8080);
             var socket = serverSocket.accept();
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var dataInputStream = new DataInputStream(socket.getInputStream())) {
            log.info("{}:{}",serverSocket.getLocalSocketAddress(),serverSocket.getLocalPort());

            System.out.println(dataInputStream.readUTF());
            outputStream.writeUTF("");
        }
    }
}
