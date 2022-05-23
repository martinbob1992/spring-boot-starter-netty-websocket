package com.marsh.websocket.config;

import com.corundumstudio.socketio.Configuration;
import com.marsh.websocket.interfaces.ISocketIOConfigBeanPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marsh
 * @date 2021-11-08日 11:56
 */
@Slf4j
//@Component
public class SocketIOConfigBeanPostProcessor2 implements ISocketIOConfigBeanPostProcessor {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void postProcessBeforeInitialization(Configuration config) {
        log.info(this.getClass().getName());
        log.info("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }
}
