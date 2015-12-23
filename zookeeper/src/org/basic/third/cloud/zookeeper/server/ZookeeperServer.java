package org.basic.third.cloud.zookeeper.server;


public class ZookeeperServer {
    public static void main(String[] args) {
        
        String [] zooKeepServerArgs = new String[]{"C:/OpenSrc/soa_hadop/hdata/zookeeper/conf/zoo.cfg"};
//        -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.local.only=false
        System.setProperty("com.sun.management.jmxremote", "true");
        System.setProperty("com.sun.management.jmxremote.local.only", "false");
        org.apache.zookeeper.server.quorum.QuorumPeerMain.main(zooKeepServerArgs);

    }
}
