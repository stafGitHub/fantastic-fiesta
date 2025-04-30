package ru.shift.client;

import ru.shift.client.view.Chat;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
//        try {
//            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
//        } catch (Exception e) {
//        }
//        SwingUtilities.invokeLater(() -> new Chat().setVisible(true));

        var inetAddress = Inet4Address.getByName("localhost");
        var inetPort = 8080;

        try(var socket = new Socket(inetAddress, inetPort );
            var outputStream = new DataOutputStream(socket.getOutputStream());
            var inputStream = new DataInputStream(socket.getInputStream())) {
            outputStream.writeUTF("Hello World");
            System.out.println(inputStream.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
