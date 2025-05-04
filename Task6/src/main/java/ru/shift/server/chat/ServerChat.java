package ru.shift.server.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.server.chat.endpoints.EndpointFactory;
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
            while (session.getSocket().isConnected()) {
                var message = session.getMessage();
                var protocol = message.protocol();
                var endpoint = EndpointFactory.create(protocol);
                endpoint.process(session, message);
            }
        } catch (MessageException | ConnectException | JsonException e) {
            log.warn(e.getMessage() , e);
            log.warn("Сессия закрыта {}",session.getSocket().getRemoteSocketAddress());

            if (session.getUserName() != null) {
                EndpointFactory.create(ApplicationProtocol.LOGOUT).process(session,null);
                log.warn("{} удалён",session.getUserName());
            }

        } catch (IOException e) {
            log.warn(e.getMessage() , e);
        }
    }
}
