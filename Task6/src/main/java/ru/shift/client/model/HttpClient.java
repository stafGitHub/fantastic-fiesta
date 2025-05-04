package ru.shift.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.event.Connect;
import ru.shift.client.model.event.Event;
import ru.shift.client.model.event.Login;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.ServerMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Slf4j
public class HttpClient implements Publisher {
    private final ObjectMapper mapper = new ObjectMapper();
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addListener(Observer... observer) {
        observers.addAll(Arrays.asList(observer));
    }

    @Override
    public void notifyListeners(Event event) {
        observers.forEach(observer -> observer.serverEvent(event));
    }

    public void connect(String url) {
        String[] hosts = url.split(":");
        try (Socket socket = new Socket(hosts[0], Integer.parseInt(hosts[1]));
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            notifyListeners(new Connect());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendName(String name) {
        try {
            outputStream.writeUTF(mapper.writeValueAsString(new ClientMessage(
                    ApplicationProtocol.LOGIN,
                    name,
                    Calendar.getInstance())
            ));

            ServerMessage serverMessage = mapper.readValue(inputStream.readUTF(), ServerMessage.class);
            notifyListeners(new Login(serverMessage));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String body) {
        try {
            outputStream.writeUTF(mapper.writeValueAsString(new ClientMessage(
                    ApplicationProtocol.LOGIN,
                    body,
                    Calendar.getInstance())
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void observer(DataInputStream inputStream) {
        new Thread(() -> {
            try {
                ServerMessage serverMessage = mapper.readValue(inputStream.readUTF(), ServerMessage.class);
                log.info(serverMessage.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
