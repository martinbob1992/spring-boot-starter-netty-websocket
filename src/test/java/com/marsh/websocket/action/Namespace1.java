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
@NamespaceEndpoint(path = "/chat1")
public class Namespace1 extends SocketIOSupport {

    @OnConnect
    public void onConnect(SocketIOClient client){
        log.info("Namespace1 ---> 欢迎uid:"+client.getSessionId());
        // 这里让这个客户端加入了某个房间，后续可以通过如下方式给指定房间用户发送消息
        //server.getRoomOperations("test").sendEvent(...);
        client.joinRoom("test");
    }

    @OnEvent("message")
    public void message(SocketIOClient client, ChatObject data, AckRequest ackRequest){
        currentNamespace.getBroadcastOperations().sendEvent("message", data);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client){
        log.info("Namespace1 ---> 退出uid:"+client.getSessionId());
    }
}
