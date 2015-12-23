package org.basic.third.cloud.bookkeeper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.BookKeeper.DigestType;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.zookeeper.KeeperException;

/**
 * <Pre>
 * Programming with BookKeeper
 *   Instantiating BookKeeper.
 *   Creating a ledger.
 *   Adding entries to a ledger.
 *   Closing a ledger.
 *   Opening a ledger.
 *   Reading from ledger
 *      Deleting a ledger
 * </pre>
 * 
 * @author ygong
 *
 */
public class BookKeeperTest {
    public static void main(String[] args) {
        try {
            new BookKeeperTest().start();
        } catch (InterruptedException | BKException | IOException | KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void start() throws InterruptedException, BKException, IOException, KeeperException {
        BookKeeper bkClient = new BookKeeper("localhost:2181");
        byte[] ledgerPassword = "password".getBytes();
        LedgerHandle lh = bkClient.createLedger(1,1,DigestType.CRC32, ledgerPassword);
        long ledgerId = lh.getId();

        List<byte[]> entries = new ArrayList<byte[]>();
        ByteBuffer entry = ByteBuffer.allocate(4);
        for (int i = 0; i < 10; i++) {
            entry.putInt(i);
            entry.position(0);
            entries.add(entry.array());
            lh.addEntry(entry.array());
        }
        lh.close();
        lh = bkClient.openLedger(ledgerId, DigestType.CRC32, ledgerPassword);

        Enumeration<LedgerEntry> ls = lh.readEntries(0, 9);
        int i = 0;
        while (ls.hasMoreElements()) {
            ByteBuffer origbb = ByteBuffer.wrap(entries.get(i++));
            Integer origEntry = origbb.getInt();
            ByteBuffer result = ByteBuffer.wrap(ls.nextElement().getEntry());

            Integer retrEntry = result.getInt();
        }
        lh.close();
    }
}
