package org.basic.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class HBaseTestWithHTable {

    static org.apache.log4j.Logger logger = Logger
	    .getLogger(HBaseTestWithHTable.class);
    static org.apache.hadoop.hbase.HBaseConfiguration HBASE_CONFIGURATION = null;
    static {
	org.apache.hadoop.conf.Configuration HBASE_CONFIG = org.apache.hadoop.hbase.HBaseConfiguration
		.create();
	HBASE_CONFIG
		.set("hbase.zookeeper.quorum", "hserver1,hserver2,hserver3");
	HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "31315");
	// HBASE_CONFIG.set("hbase.master", "192.168.1.100:600000");

	// <property>
	// <name>hbase.zookeeper.property.clientPort</name>
	// <value>2222</value>
	// </property>
	HBASE_CONFIGURATION = new HBaseConfiguration(HBASE_CONFIG);
    }

    /**
     * 创建一张表
     * 
     * <pre>
     * 2)关于建表
     *   
     * public void createTable(HTableDescriptor desc)
     *  
     * HTableDescriptor 代表的是表的schema, 提供的方法中比较有用的有
     * setMaxFileSize，指定最大的region size
     * setMemStoreFlushSize 指定memstore flush到HDFS上的文件大小
     * 增加family通过 addFamily方法
     *  
     * public void addFamily(final HColumnDescriptor family)
     *  
     * HColumnDescriptor代表的是column的schema，提供的方法比较常用的有
     * setTimeToLive:指定最大的TTL,单位是ms,过期数据会被自动删除。
     * setInMemory:指定是否放在内存中，对小表有用，可用于提高效率。默认关闭
     * setBloomFilter:指定是否使用BloomFilter,可提高随机查询效率。默认关闭
     * setCompressionType:设定数据压缩类型。默认无压缩。
     * setMaxVersions:指定数据最大保存的版本个数。默认为3。
     *  
     * 注意的是，一般我们不去setInMemory为true,默认是关闭的
     * </pre>
     */
    public static void creatTable(String tableName) throws Exception {
	HBaseAdmin admin = new HBaseAdmin(HBASE_CONFIGURATION);
	if (admin.tableExists(tableName)) {
	    logger.info("table   Exists!!!");
	} else {
	    HTableDescriptor tableDesc = new HTableDescriptor(tableName);
	    tableDesc.addFamily(new HColumnDescriptor("emp_id"));
	    tableDesc.addFamily(new HColumnDescriptor("emp_name"));
	    tableDesc.addFamily(new HColumnDescriptor("description"));
	    admin.createTable(tableDesc);
	    logger.info("create table ok .");
	}

    }

    public static void deleteRowByKey(String tableName, String rowKey)
	    throws IOException {
	HTable hTable = new HTable(HBASE_CONFIGURATION, tableName);
	List list = new ArrayList();
	Delete d1 = new Delete(rowKey.getBytes());
	list.add(d1);
	hTable.delete(list);
	System.out.println("删除行成功!");
    }

    public static void dropTable(String tableName) throws IOException {
	HBaseAdmin admin = new HBaseAdmin(HBASE_CONFIGURATION);
	if (admin.tableExists(tableName)) {
	    admin.disableTable(tableName);
	    admin.deleteTable(tableName);
	    logger.info("drop table ok .");
	} else {
	    logger.info("table doesn't  Exists!!!");

	}
    }

    /**
     * 添加一条数据
     * 
     * <pre>
     * 3)关于入库
     *    官方建议
     *  table.setAutoFlush(false); //数据入库之前先设置此项为false
     *  table.setflushCommits();//入库完成后，手动刷入数据
     * 注意：
     *   在入库过程中，put.setWriteToWAL(true/flase);
     *   关于这一项如果不希望大量数据在存储过程中丢失，建议设置为true,如果仅是在测试演练阶段，为了节省入库时间建议设置为false
     *   
     *   4)关于获取表实例
     * HTablePool pool = new HTablePool(HBASE_CONFIGURATION, Integer.MAX_VALUE);
     * HTable table = (HTable) pool.getTable(tableName);
     * 建议用表连接池的方式获取表，具体池有什么作用，我想用过数据库连接池的同学都知道，我就不再重复
     * 不建议使用new HTable(HBASE_CONFIGURATION,tableName);的方式获取表
     * </pre>
     */
    public static void addData(String tableName) throws Exception {
	HTable table = new HTable(HBASE_CONFIGURATION, tableName);
	table.setAutoFlush(false);// not flush out until buffer is overflow
	// BatchUpdate update = new BatchUpdate("Huangyi");
	// update.put("name:java", "http://www.javabloger.com".getBytes());
	List<Row> actions = new ArrayList<>();
	int i = 0;
	logger.info("add data started .");
	int r = 2;
	while (++i < 50000) {
	    Put put = new Put(("r" + r).getBytes());
	    put.add("description".getBytes(), ("c" + i).getBytes(),
		    ("http://www.javabloger.com/" + i).getBytes());
	    actions.add(put);
	    if (i % 100 == 0) {
		table.batch(actions);
		// table.put(put);
		logger.info("commit_" + i);
		r++;
	    }
	    table.flushCommits();
	}
	logger.info("add data end.");
	table.close();
    }

    /**
     * 根据 rowkey删除一条记录
     * 
     * @param tablename
     * @param rowkey
     */
    public static void deleteRow(String tablename, String rowkey) {
	try {
	    HTable table = new HTable(HBASE_CONFIGURATION, tablename);
	    List list = new ArrayList();
	    Delete d1 = new Delete(rowkey.getBytes());
	    list.add(d1);

	    table.delete(list);
	    System.out.println("删除行成功!");

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * 组合条件删除
     * 
     * @param tablename
     * @param rowkey
     */
    public static void deleteByCondition(String tablename, String rowkey) {
	// 目前还没有发现有效的API能够实现 根据非rowkey的条件删除 这个功能能，还有清空表全部数据的API操作

    }

    /**
     * 查询所有数据
     * 
     * @param tableName
     */
    public static void QueryAll(String tableName) {
	HTablePool pool = new HTablePool(HBASE_CONFIGURATION, 1000);
	HTable table = (HTable) pool.getTable(tableName);
	try {
	    ResultScanner rs = table.getScanner(new Scan());
	    for (Result r : rs) {
		System.out.println("获得到rowkey:" + new String(r.getRow()));
		for (KeyValue keyValue : r.raw()) {
		    System.out.println("列：" + new String(keyValue.getFamily())
			    + "====值:" + new String(keyValue.getValue()));
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * 单条件查询,根据rowkey查询唯一一条记录
     * 
     * @param tableName
     */
    public static void queryByKeyRow(String tableName, String rowKey) {

	HTablePool pool = new HTablePool(HBASE_CONFIGURATION, 1000);
	HTableInterface table = pool.getTable(tableName);
	try {
	    Get scan = new Get(rowKey.getBytes());// 根据rowkey查询
	    Result r = table.get(scan);
	    if (r.getRow() != null)
	    {
		System.out.println("Find row: " + new String(r.getRow()));
		for (KeyValue keyValue : r.raw()) {
		    System.out.println("  Colum=" +new String(keyValue.getFamily())+":"+ new String(keyValue.getQualifier())
			    + ", Value:" + new String(keyValue.getValue()));
		}
	    } else{
		logger.error("Failed to find the row with rowkey="+rowKey);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * 单条件按查询，查询多条记录
     * 
     * @param tableName
     */
    public static void QueryByCondition2(String tableName) {

	try {
	    HTablePool pool = new HTablePool(HBASE_CONFIGURATION, 1000);
	    HTable table = (HTable) pool.getTable(tableName);
	    Filter filter = new SingleColumnValueFilter(
		    Bytes.toBytes("column1"), null, CompareOp.EQUAL,
		    Bytes.toBytes("aaa")); // 当列column1的值为aaa时进行查询
	    Scan s = new Scan();
	    s.setFilter(filter);
	    ResultScanner rs = table.getScanner(s);
	    for (Result r : rs) {
		System.out.println("获得到rowkey:" + new String(r.getRow()));
		for (KeyValue keyValue : r.raw()) {
		    System.out.println("列：" + new String(keyValue.getFamily())
			    + "====值:" + new String(keyValue.getValue()));
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * 组合条件查询
     * 
     * @param tableName
     */
    public static void QueryByCondition3(String tableName) {

	try {
	    HTablePool pool = new HTablePool(HBASE_CONFIGURATION, 1000);
	    HTable table = (HTable) pool.getTable(tableName);

	    List<Filter> filters = new ArrayList<Filter>();

	    Filter filter1 = new SingleColumnValueFilter(
		    Bytes.toBytes("column1"), null, CompareOp.EQUAL,
		    Bytes.toBytes("aaa"));
	    filters.add(filter1);

	    Filter filter2 = new SingleColumnValueFilter(
		    Bytes.toBytes("column2"), null, CompareOp.EQUAL,
		    Bytes.toBytes("bbb"));
	    filters.add(filter2);

	    Filter filter3 = new SingleColumnValueFilter(
		    Bytes.toBytes("column3"), null, CompareOp.EQUAL,
		    Bytes.toBytes("ccc"));
	    filters.add(filter3);

	    FilterList filterList1 = new FilterList(filters);

	    Scan scan = new Scan();
	    scan.setFilter(filterList1);
	    ResultScanner rs = table.getScanner(scan);
	    for (Result r : rs) {
		System.out.println("获得到rowkey:" + new String(r.getRow()));
		for (KeyValue keyValue : r.raw()) {
		    System.out.println("列：" + new String(keyValue.getFamily())
			    + "====值:" + new String(keyValue.getValue()));
		}
	    }
	    rs.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * 显示所有数据
     * 
     * <pre>
     * 
     * 5)关于查询
     *  建议每个查询语句都放入try catch语句块，并且finally中要进行关闭ResultScanner实例以及将不使用的表重新放入到HTablePool中的操作，具体做法如下
     * Java代码  
     * public static void QueryAll(String tableName) {  
     *         HTablePool pool = new HTablePool(HBASE_CONFIGURATION, Integer.MAX_VALUE);  
     *         HTable table = null;  
     *         ResultScanner rs = null;  
     *         try {  
     *             Scan scan = new Scan();  
     *             table = (HTable) pool.getTable(tableName);  
     *             rs = table.getScanner(scan);  
     *             for (Result r : rs) {  
     *                 System.out.println("获得到rowkey:" + new String(r.getRow()));  
     *                 for (KeyValue keyValue : r.raw()) {  
     *                     System.out.println("列：" + new String(keyValue.getFamily())  
     *                             + "====值:" + new String(keyValue.getValue()));  
     *                 }  
     *             }  
     *         } catch (IOException e) {  
     *             e.printStackTrace();  
     *         }finally{  
     *             rs.close();// 最后还得关闭  
     *             pool.putTable(table); //实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)  
     *         }  
     *     }  
     *  
     *  所以，以上代码有缺陷的地方，感兴趣的同学可以针对优化建议作出相应修改
     * </pre>
     */
    public static void getAllData(String tableName) throws Exception {
	HTable hTable = new HTable(HBASE_CONFIGURATION, tableName);
	Scan s = new Scan();
	s.setBatch(100);
	s.setCacheBlocks(true);
	org.apache.hadoop.hbase.client.ResultScanner rs = hTable.getScanner(s);
	try {
	    for (org.apache.hadoop.hbase.client.Result r : rs) {
		// for (KeyValue kv : r.raw()) {
		// logger.info(new String(kv.getFamily()) + ", "
		// + new String(kv.getValue()));
		// }
		for (org.apache.hadoop.hbase.Cell cell : r.rawCells()) {
		    logger.info(new String(CellUtil.cloneRow(cell)) + ","
			    + new String(CellUtil.cloneFamily(cell)) + ", "
			    + new String(CellUtil.cloneValue(cell)));
		}
	    }
	} finally {
	    rs.close();// 如果你忘记了关闭ResultScanners，会导致RegionServer出现问题
	}
	hTable.close();
    }

    public static void main(String[] agrs) {
	try {
	    String tableName = "student";
	     creatTable(tableName);
	    // HBaseTestCase.addData(tableName);
	    queryByKeyRow(tableName, "zhangsan");
//	    HBaseTestWithHTable.getAllData(tableName);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}