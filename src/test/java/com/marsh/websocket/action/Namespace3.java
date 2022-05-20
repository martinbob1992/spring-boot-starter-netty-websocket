package com.marsh.websocket.action;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.marsh.websocket.annotations.NamespaceEndpoint;
import com.marsh.websocket.support.SocketIOSupport;
import com.marsh.websocket.vo.ChatObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Marsh
 * @date 2021-11-05日 16:43
 */
@Slf4j
@NamespaceEndpoint(path = "/chat1/chat3")
public class Namespace3 extends SocketIOSupport {

    @OnConnect
    public void onConnect(SocketIOClient client){
        log.info("Namespace3 ---> 欢迎uid:"+client.getSessionId());
    }

    @OnEvent("message")
    public void message(SocketIOClient client, ChatObject data, AckRequest ackRequest){
        currentNamespace.getBroadcastOperations().sendEvent("message", data);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client){
        log.info("Namespace3 ---> 退出uid:"+client.getSessionId());
    }
}
