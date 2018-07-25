package com.o2o.massage.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Liu on 16/12/14.
 */
public class ZKUtils {
    private static Logger logger = LoggerFactory.getLogger(ZKUtils.class);

    public static String getZkHostAndPortString(String zkServers) throws Exception, Exception {
        logger.info("zk servers:{}", zkServers);
        String[] servers = zkServers.split(",");
        String hostPortListStr = null;
        String defaultPort = null;

        String lastServer = servers[servers.length - 1];
        String[] lastServerPorts = lastServer.split(":");
        if (lastServerPorts.length == 2) {
            defaultPort = lastServerPorts[1];
        }

        for (int i = 0; i < servers.length; ++i) {
            String[] hostAndPort = servers[i].trim().split(":");
            if (hostAndPort.length > 2) {
                throw new Exception("wrong format zk server format, zkServers=" + zkServers);
            }

            String currentHostPort;
            if (hostAndPort.length == 2) {
                currentHostPort = hostAndPort[0].trim() + ":" + hostAndPort[1].trim();
            } else if (defaultPort != null) {
                currentHostPort = hostAndPort[0].trim() + ":" + defaultPort;
            } else {
                currentHostPort = hostAndPort[0].trim();
            }

            if (hostPortListStr == null) {
                hostPortListStr = currentHostPort;
            } else {
                hostPortListStr = hostPortListStr + "," + currentHostPort;
            }
            if (hostAndPort.length > 1) {
                String port = hostAndPort[1].trim();
                if (defaultPort != null && !defaultPort.equals(port)) {
                    throw new Exception("port is not same among all servers, zkServers=" + zkServers);
                }
            }
        }

        if (defaultPort == null) {
            throw new Exception("no port found in zk server, zkServers=" + zkServers);
        } else {
            logger.info("zk servers processed result:{}", hostPortListStr);
            return hostPortListStr;
        }
    }
}
