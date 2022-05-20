package com.marsh.websocket.scanner;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.marsh.websocket.annotations.NamespaceEndpoint;
import com.marsh.websocket.support.SocketIOSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marsh
 * @date 2021-11-05日 10:24
 */
@Component
@Slf4j
public class NamespaceEndpointScanner implements BeanPostProcessor {

    /**
     * Namespace端点path禁用名单
     */
    private final static List<String> PATH_DISABLE_LIST = Arrays.asList("", "/");


    private final SocketIOServer socketIOServer;

    private Class originalBeanClass;

    public NamespaceEndpointScanner(SocketIOServer socketIOServer){
        super();
        this.socketIOServer = socketIOServer;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(NamespaceEndpoint.class)){
            originalBeanClass = bean.getClass();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (originalBeanClass != null){
            NamespaceEndpoint ne = AnnotationUtils.getAnnotation(bean.getClass(),NamespaceEndpoint.class);
            if (PATH_DISABLE_LIST.contains(ne.path())){
                throw new RuntimeException(bean.getClass().getName() + " @NamespaceEndpoint(path = '请设置一个路径,该路径不能使用空字符串或/')");
            }
            SocketIONamespace socketIONamespace = socketIOServer.addNamespace(ne.path());
            socketIONamespace.addListeners(bean,bean.getClass());
            if (bean instanceof SocketIOSupport){
                ((SocketIOSupport)bean).setCurrentNamespace(socketIONamespace);
            }
            log.info("[{}]添加WebSocket服务端点[{}]",bean.getClass().getName(),ne.path());
            originalBeanClass = null;
        }
        return bean;
    }
}
