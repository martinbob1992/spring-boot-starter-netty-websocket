package com.marsh.websocket.hook;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Marsh
 * @date 2022-05-23日 9:16
 */
@Slf4j
public class WebsocketShutdownHook extends Thread {

    private SocketIOServer server;

    public WebsocketShutdownHook(SocketIOServer server){
        this.server = server;
    }

    @Override
    public void run() {
        if (server != null){
            try {
                server.stop();
            } catch (Exception e){
                log.error("SocketIOServer.stop()失败!",e);
            }
        }
    }
}
