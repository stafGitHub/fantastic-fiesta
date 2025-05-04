package ru.shift.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.model.HttpClient;
import ru.shift.client.presenter.ChatPresenter;
import ru.shift.client.presenter.ConnectNamePresenter;
import ru.shift.client.presenter.ConnectPresenter;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.client.view.concrete.ConnectNameView;
import ru.shift.client.view.concrete.ConnectView;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.ServerMessage;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.UUID;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception ignored){}

        var connectView = new ConnectView();
        var connectNameView = new ConnectNameView();
        var chatView = new ChatView();

        var httpClient = new HttpClient();

        var connectPresenter = new ConnectPresenter(connectView,httpClient);
        var connectNamePresenter = new ConnectNamePresenter(connectNameView,httpClient);
        var chatPresenter = new ChatPresenter(chatView,httpClient);

        connectView.setVisible(true);

    }






    void startTest() throws UnknownHostException {
        var inetAddress = Inet4Address.getByName("localhost");
        var inetPort = 8080;
        ObjectMapper objectMapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);

        ServerMessage message;
        try (var socket = new Socket(inetAddress, inetPort);
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var reader = new DataInputStream(socket.getInputStream())) {
            var name = new ClientMessage(ApplicationProtocol.LOGIN, "zalypa " + UUID.randomUUID(), Calendar.getInstance());
            var s = objectMapper.writeValueAsString(name);
            outputStream.writeUTF(s);
            message = objectMapper.readValue(reader.readUTF(), ServerMessage.class);
            log.info(message.toString() + "Connect");

            new Thread(() -> {
                ServerMessage message1 = null;
                while (true) {
                    try {
                        message1 = objectMapper.readValue(reader.readUTF(), ServerMessage.class);
                    } catch (IOException e) {
                        log.info(e.getMessage());
                        throw new RuntimeException(e);
                    }
                    log.info(message1.toString());
                }
            }).start();
            while (true) {
                log.info("Текст");
                outputStream.writeUTF(objectMapper.writeValueAsString(new ClientMessage(
                        ApplicationProtocol.SEND_MESSAGE,
                        scanner.nextLine(),
                        Calendar.getInstance())));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}