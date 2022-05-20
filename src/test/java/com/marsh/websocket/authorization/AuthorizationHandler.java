package com.marsh.websocket.authorization;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 自定义授权认证
 * @author Marsh
 * @date 2021-11-05日 9:39
 */
@Component
@Slf4j
public class AuthorizationHandler implements AuthorizationListener {

    @Override
    public boolean isAuthorized(HandshakeData data) {
        String uid = UUID.randomUUID().toString();
        data.getHttpHeaders().set("userId", uid);
        log.info("用户正在进行认证uid:"+uid);
        return true;
    }
}
