package com.marsh.websocket.config;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.marsh.websocket.hook.WebsocketShutdownHook;
import com.marsh.websocket.interfaces.ISocketIOConfigBeanPostProcessor;
import com.marsh.websocket.properties.WebsocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ApplicationObjectSupport;

import java.util.List;

/**
 * @author Marsh
 * @date 2021-11-01日 9:05
 */
@Slf4j
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(WebsocketProperties.class)
@ComponentScan("com.marsh.websocket")
public class SocketIOAutoConfiguration extends ApplicationObjectSupport {

    @Autowired(required = false)
    private List<ISocketIOConfigBeanPostProcessor> socketIOConfigBeanPostProcessors;
    @Autowired(required = false)
    private List<ConnectListener> connectListeners;
    @Autowired(required = false)
    private List<DisconnectListener> disconnectListeners;

    @Bean
    public SocketIOServer socketIOServer(WebsocketProperties config){
        deployExceptionListener(config);
        deployAuthorizationListener(config);
        beanPostProcessor(config);

        SocketIOServer server = new SocketIOServer(config);
        if (connectListeners != null && connectListeners.size() > 0){
            connectListeners.stream().forEach(server::addConnectListener);
        }
        if (disconnectListeners != null && disconnectListeners.size() > 0){
            disconnectListeners.stream().forEach(server::addDisconnectListener);
        }
        // 程序退出时，关闭网络连接，清理资源
        Runtime.getRuntime().addShutdownHook(new WebsocketShutdownHook(server));
        return server;
    }

    private void beanPostProcessor(Configuration socketIOConfig) {
        if (socketIOConfigBeanPostProcessors != null && socketIOConfigBeanPostProcessors.size() > 0){
            socketIOConfigBeanPostProcessors.stream().forEach(processors -> {
                processors.postProcessBeforeInitialization(socketIOConfig);
            });
        }
    }

    private void deployExceptionListener(Configuration socketIOConfig){
        ApplicationContext applicationContext = getApplicationContext();
        String[] exceptionListeners = applicationContext.getBeanNamesForType(ExceptionListener.class);
        if (exceptionListeners != null && exceptionListeners.length > 0){
            socketIOConfig.setExceptionListener((ExceptionListener)applicationContext.getBean(exceptionListeners[0]));
        }
    }

    private void deployAuthorizationListener(Configuration socketIOConfig){
        ApplicationContext applicationContext = getApplicationContext();
        String[] authorizationListeners = applicationContext.getBeanNamesForType(AuthorizationListener.class);
        if (authorizationListeners != null && authorizationListeners.length > 0){
            socketIOConfig.setAuthorizationListener((AuthorizationListener)applicationContext.getBean(authorizationListeners[0]));
        }
    }

}
