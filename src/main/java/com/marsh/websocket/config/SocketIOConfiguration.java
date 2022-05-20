package com.marsh.websocket.config;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.marsh.websocket.interfaces.ISocketIOConfigBeanPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@ComponentScan("com.marsh.websocket")
public class SocketIOConfiguration extends ApplicationObjectSupport {

    @Value("${socket.hostname:localhost}")
    private String hostname;
    @Value("${socket.port:9092}")
    private Integer port;

    @Autowired(required = false)
    private List<ISocketIOConfigBeanPostProcessor> socketIOConfigBeanPostProcessors;


    @ConditionalOnMissingBean(Configuration.class)
    @Bean
    public Configuration socketIOConfig(){
        Configuration socketIOConfig = new Configuration();
        socketIOConfig.setHostname(hostname);
        socketIOConfig.setPort(port);

        deployExceptionListener(socketIOConfig);
        deployAuthorizationListener(socketIOConfig);

        beanPostProcessor(socketIOConfig);

        return socketIOConfig;
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
