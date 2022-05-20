package com.marsh.websocket.support;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.marsh.websocket.scanner.NamespaceEndpointScanner;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 子类必须标记@NamespaceEndpoint注解
 * @author Marsh
 * @date 2021-11-08日 14:47
 */
public class SocketIOSupport {

    @Autowired
    protected SocketIOServer server;

    /**
     * 该属性会自动注入,子类可直接使用
     * @see NamespaceEndpointScanner#postProcessAfterInitialization(Object, String)
     */
    @Setter
    @Getter
    protected SocketIONamespace currentNamespace;
}
