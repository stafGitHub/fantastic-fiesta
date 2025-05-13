package ru.shift.server.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.server.chat.endpoints.EndpointProvider;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.JsonException;
import ru.shift.server.expections.MessageException;

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
                pool.submit(() -> requestProcessing(new UserSession(clienSocket)));
            }

        } catch (IOException e) {
            pool.shutdown();
        }
    }

    private void requestProcessing(UserSession session) {
        try (session) {
            while (!session.getSocket().isClosed()) {
                var message = session.getMessage();
                var protocol = message.messageType();
                var endpoint = EndpointProvider.getEndpointByMessageType(protocol);
                endpoint.process(session, message);
            }
        } catch (MessageException | ConnectException | JsonException e) {
            log.warn(e.getMessage(), e);
            log.warn("Сессия закрыта {}", session.getSocket().getRemoteSocketAddress());
            EndpointProvider.getEndpointByMessageType(MessageType.LOGOUT).process(session, null);
            log.warn("{} удалён", session.getUserName());

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
