package com.marsh.websocket.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ApplicationObjectSupport;

import java.util.List;

/**
 * @author Marsh
 * @date 2021-11-01日 9:05
 */
@Slf4j
@org.springframework.context.annotation.Configuration
@ConditionalOnMissingBean(SocketIOServer.class)
@AutoConfigureAfter(SocketIOConfiguration.class)
public class SocketIOServerConfiguration extends ApplicationObjectSupport {

    @Autowired(required = false)
    private List<ConnectListener> connectListeners;
    @Autowired(required = false)
    private List<DisconnectListener> disconnectListeners;

    @Bean
    public SocketIOServer socketIOServer(Configuration config){
        SocketIOServer server = new SocketIOServer(config);

        if (connectListeners != null && connectListeners.size() > 0){
            connectListeners.stream().forEach(server::addConnectListener);
        }
        if (disconnectListeners != null && disconnectListeners.size() > 0){
            disconnectListeners.stream().forEach(server::addDisconnectListener);
        }
        return server;
    }

}
