package ru.shift.server.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.exception.JsonException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.MessageType;
import ru.shift.server.chat.endpoints.EndpointsDispatcher;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConfigurationException;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
public class ServerChat {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final EndpointsDispatcher dispatcher;

    public void start() throws ConfigurationException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Сервер создан {}:{}", serverSocket.getLocalSocketAddress(), serverSocket.getLocalPort());
            while (true) {
                try {
                    Socket clienSocket = serverSocket.accept();
                    pool.submit(() -> {
                        try {
                            requestProcessing(new UserSession(clienSocket, ClientMessage.class));
                        } catch (ConnectException e) {
                            log.warn("Пользователь отключился: {}", e.getMessage(), e);
                        }
                    });
                }catch (IOException e) {
                    log.warn("Ошибка подключения: {}", e.getMessage(), e);
                }
            }
        } catch (BindException e) {
            throw new ConfigurationException("Не удалось занять порт: " + port + " " + e.getMessage());
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    private void requestProcessing(UserSession session) {
        try (session) {
            while (!session.getSocket().isClosed()) {
                var message = session.readMessageFromTheInternet();
                var protocol = message.messageType();
                var endpoint = dispatcher.getEndpointByMessageType(protocol);
                endpoint.process(session, message);
            }
        } catch (ConnectException | JsonException e) {
            log.warn(e.getMessage(), e);

            try {
                dispatcher.getEndpointByMessageType(MessageType.LOGOUT).process(session, null);
            } catch (ConnectException exception) {
                log.warn(exception.getMessage(), exception);
            }

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
