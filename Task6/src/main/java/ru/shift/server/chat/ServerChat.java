package ru.shift.server.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.server.chat.endpoints.EndpointFactory;
import ru.shift.server.chat.session.UserSession;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
public class ServerChat {
    private final Integer port;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Сервер создан {}:{}", serverSocket.getLocalSocketAddress(), serverSocket.getLocalPort());
            while (true) {
                Socket clienSocket = serverSocket.accept();
                pool.submit(() -> {
                    try {
                        requestProcessing(new UserSession(clienSocket));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (IOException e) {
            pool.shutdown();
        }
    }

    private void requestProcessing(UserSession session) throws Exception {
        try (session) {
            while (session.getSocket().isConnected()) {
                var message = session.getMessage();
                var protocol = message.protocol();
                var endpoint = EndpointFactory.create(protocol);
                endpoint.process(session, message);
            }
        }
    }
}
