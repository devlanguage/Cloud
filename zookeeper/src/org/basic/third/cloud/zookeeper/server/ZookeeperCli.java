package org.basic.third.cloud.zookeeper.server;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class ZookeeperCli {
    public static void main(String[] args) {
//        System.setProperty("zookeeper.log.dir", ".");
//        System.setProperty("zookeeper.root.logger", "INFO,CONSOLE");
        String[] zooKeepCliArgs = new String[] { "-server", "localhost:2181"};
        try {
            org.apache.zookeeper.ZooKeeperMain.main(zooKeepCliArgs);
        } catch (KeeperException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        // hserver1 31315 status
        // org.apache.zookeeper.client.FourLetterWordMain.main(args);
    }

}
