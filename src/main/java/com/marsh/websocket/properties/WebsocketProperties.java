package com.marsh.websocket.properties;

import com.corundumstudio.socketio.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Marsh
 * @date 2022-05-23日 9:01
 */
@ConfigurationProperties("socket")
public class WebsocketProperties extends Configuration {
}
