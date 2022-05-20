package com.marsh.websocket.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Marsh
 * @date 2022-05-20日 14:33
 */
@Slf4j
@Component
public class MyDisconnectListener implements DisconnectListener {

    @Override
    public void onDisconnect(SocketIOClient client) {
        log.info("客户端已断开链接 clientId"+client.getSessionId());
    }
}
