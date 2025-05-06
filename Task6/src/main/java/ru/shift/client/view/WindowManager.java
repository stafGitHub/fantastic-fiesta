package ru.shift.client.view;

import lombok.Getter;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.client.view.concrete.ConnectNameView;
import ru.shift.client.view.concrete.ConnectView;

@Getter
public enum WindowManager {
    INSTANCE;

    private final ConnectView connectView = new ConnectView();
    private final ConnectNameView connectNameView = new ConnectNameView();
    private final ChatView chatView = new ChatView();

}
