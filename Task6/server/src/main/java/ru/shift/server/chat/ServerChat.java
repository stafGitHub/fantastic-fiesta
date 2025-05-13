package ru.shift.server.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.exception.JsonException;
import ru.shift.network.message.ClientMessage;
import ru.shift.server.chat.endpoints.factory.EndpointsFactory;
import ru.shift.server.chat.session.UserSession;
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
                pool.submit(() -> requestProcessing(new UserSession(clienSocket, ClientMessage.class)));
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
                var endpoint = EndpointsFactory.getEndpointByMessageType(protocol);
                endpoint.process(session, message);
            }
        } catch (MessageException | ConnectException | JsonException e) {
            log.warn(e.getMessage(), e);
            log.warn("Сессия закрыта {}", session.getSocket().getRemoteSocketAddress());
            EndpointsFactory.getEndpointByMessageType(MessageType.LOGOUT).process(session, null);
            log.warn("{} удалён", session.getUserName());

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
