package ru.shift.client.presenter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.HttpClient;
import ru.shift.client.model.event.Event;
import ru.shift.client.model.event.Login;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.common.protocol.message.output.LoginMessageSuccess;

import java.io.IOException;

public class ChatPresenter extends Observer implements Presenter {
    private final ChatView view;
    private final HttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public ChatPresenter(ChatView view, HttpClient client, Publisher... publishers) {
        super(publishers);
        this.view = view;
        this.client = client;
        view.addActionListener(this);
    }

    @Override
    public void serverEvent(Event event) {
        if (event instanceof Login login){
            try {
                mapper.readValue((JsonParser) login.serverMessage(), LoginMessageSuccess.class);
            } catch (IOException e) {
                return;
            }
            view.setVisible(true);
        }
    }

    @Override
    public void onMouseClicked() {
        String body = view.getMessage();
        client.sendMessage(body);
    }
}
