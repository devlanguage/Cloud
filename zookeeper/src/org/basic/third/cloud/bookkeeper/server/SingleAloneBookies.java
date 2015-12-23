package org.basic.third.cloud.bookkeeper.server;

import java.io.IOException;

import org.apache.bookkeeper.bookie.BookieException;
import org.apache.bookkeeper.replication.ReplicationException.CompatibilityException;
import org.apache.bookkeeper.replication.ReplicationException.UnavailableException;
import org.apache.bookkeeper.util.LocalBookKeeper;
import org.apache.zookeeper.KeeperException;

public class SingleAloneBookies {
    public static void main(String[] args) {
        org.apache.bookkeeper.util.LocalBookKeeper localBookKeeper = new LocalBookKeeper();
        args = new String[]{"1"};
        try {
            localBookKeeper.main(args);
        } catch (UnavailableException | CompatibilityException | IOException | KeeperException | InterruptedException | BookieException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
