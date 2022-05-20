package com.marsh.websocket.listener;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 每当一个客户端建立链接触发监听
 * @author Marsh
 * @date 2022-05-20日 14:32
 */
@Slf4j
@Component
public class MyConnectListener implements ConnectListener {

    @Override
    public void onConnect(SocketIOClient client) {
        HandshakeData data = client.getHandshakeData();
        log.info("uid:"+data.getHttpHeaders().get("userId"));
        log.info("客户端已链接 clientId"+client.getSessionId());
    }
}
