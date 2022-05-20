package com.marsh.websocket.interfaces;

import com.corundumstudio.socketio.Configuration;

/**
 * SocketIO配置初始化后置处理器，可以通过实现这个接口继续设置配置
 * @author marsh
 * @date 2021年11月08日 11:34
 */
public interface ISocketIOConfigBeanPostProcessor {

    void postProcessBeforeInitialization(Configuration config);
}
