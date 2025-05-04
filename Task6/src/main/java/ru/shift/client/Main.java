package ru.shift.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.UserMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.UUID;

@Slf4j
public class Main {
    public static void main(String[] args) throws UnknownHostException {
//        try {
//            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
//        } catch (Exception e) {
//        }
//        SwingUtilities.invokeLater(() -> new Chat().setVisible(true));

        var inetAddress = Inet4Address.getByName("localhost");
        var inetPort = 8080;
        ObjectMapper objectMapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);

        UserMessage message;
        try (var socket = new Socket(inetAddress, inetPort);
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var reader = new DataInputStream(socket.getInputStream())) {
            var name = new UserMessage(ApplicationProtocol.LOGIN, "zalypa "+ UUID.randomUUID());
            var s = objectMapper.writeValueAsString(name);
            outputStream.writeUTF(s);
            message = objectMapper.readValue(reader.readUTF(), UserMessage.class);
            log.info(message.toString() + "Connect");
            while (true) {
                log.info("Текст");
                name = new UserMessage(ApplicationProtocol.SEND_MESSAGE, scanner.nextLine());
                outputStream.writeUTF(objectMapper.writeValueAsString(name));
                message = objectMapper.readValue(reader.readUTF(), UserMessage.class);
                log.info(message.body().toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}