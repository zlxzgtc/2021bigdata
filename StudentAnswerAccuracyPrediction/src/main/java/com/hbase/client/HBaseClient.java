package com.hbase.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ClusterConnection;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hbase.thirdparty.org.apache.commons.collections4.map.HashedMap;

public class HBaseClient {
	private static Configuration conf;
	private static Connection con;

	static {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.rootdir", "hdfs://192.168.43.68:9000/hbase");
		conf.set("hbase.zookeeper.quorum", "192.168.43.68,192.168.43.69,192.168.43.70");
		try {
			con = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (con == null || con.isClosed()) {
			try {
				con = ConnectionFactory.createConnection(conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}

	public static void close() {
		if (con != null) {
			try {
				con.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 创建表
	public static void createTable(String tableName, String... FamilyColumn) throws IOException {
		TableName tn = TableName.valueOf(tableName);
		Admin admin = getConnection().getAdmin();
		if (admin.tableExists(tn)) {
			System.out.println("table exists!");
			return;
		}
		HTableDescriptor htd = new HTableDescriptor(tn);
		for (String fc : FamilyColumn) {
			HColumnDescriptor hcd = new HColumnDescriptor(fc);
			htd.addFamily(hcd);
		}
		admin.createTable(htd);
		admin.close();
		System.out.println("create table success!");
	}

	// 删除表
	public static void dropTable(String tableName) throws IOException {
		TableName tn = TableName.valueOf(tableName);
		Admin admin = getConnection().getAdmin();
		if (admin.tableExists(tn)) {
			try {
				admin.disableTable(tn);
				admin.deleteTable(tn);
				admin.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Delete " + tableName + " 失败");
			}
		}
		System.out.println("Delete " + tableName + " 成功");
	}

	// 插入或更新数据
	public static void insert(String tableName, String rowKey, String family, String qualifier, String value)
			throws IOException {
		Table t = getConnection().getTable(TableName.valueOf(tableName));
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
		t.put(put);
		t.close();
		System.out.println("put'" + rowKey + "'," + family + ":" + qualifier + "','" + value + "'");
	}

	// 删除（单元格、列族、列、行）
	public static void del(String tableName, String rowKey, String family, String qualifier) throws IOException {
		Table t = getConnection().getTable(TableName.valueOf(tableName));
		Delete del = new Delete(Bytes.toBytes(rowKey));
		if (qualifier != null) {
			del.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		} else if (family != null) {
			del.addFamily(Bytes.toBytes(family));
		}
		t.delete(del);
	}

	// 读取一条记录
	public static String byGet(String tableName, String rowKey, String family, String qualifier) throws IOException {
		Table t = getConnection().getTable(TableName.valueOf(tableName));
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		Result result = t.get(get);
		System.out.println("Get: " + new String(result.getValue(family.getBytes(), qualifier.getBytes())));
		return Bytes.toString(CellUtil.cloneValue(result.listCells().get(0)));
	}

	// 取到一个列族的值
	public static Map<String, String> byGet(String tableName, String rowKey, String family) throws IOException {
		Map<String, String> result = null;
		Table t = getConnection().getTable(TableName.valueOf(tableName));
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addFamily(Bytes.toBytes(family));
		Result r = t.get(get);
		List<Cell> cs = r.listCells();
		result = cs.size() > 0 ? new HashedMap<String, String>() : result;
		for (Cell cell : cs) {
			result.put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
		}
		return result;
	}

	// 取到多个列族的值
	public static Map<String, Map<String, String>> byGet(String tableName, String rowKey) throws IOException {
		Map<String, Map<String, String>> result = null;
		Table t = getConnection().getTable(TableName.valueOf(tableName));
		Get get = new Get(Bytes.toBytes(rowKey));
		Result r = t.get(get);
		List<Cell> cs = r.listCells();
		result = cs.size() > 0 ? new HashedMap<String, Map<String, String>>() : result;
		for (Cell cell : cs) {
			String familyName = Bytes.toString(CellUtil.cloneFamily(cell));
			if (result.get(familyName) == null) {
				result.put(familyName, new HashedMap<String, String>());
			}
			result.get(familyName).put(Bytes.toString(CellUtil.cloneQualifier(cell)),
					Bytes.toString(CellUtil.cloneValue(cell)));
		}
		return result;
	}


	// 显示所有数据
	public static List<String> scan(String tableName) throws IOException {
		List<String> list = new ArrayList<String>();
		Admin admin = getConnection().getAdmin();
		TableName tn = TableName.valueOf(tableName);
		if (!admin.tableExists(tn)) {
			return list;
		}
		Table table = getConnection().getTable(TableName.valueOf(tableName));

		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			Cell[] cells = result.rawCells();
			list.add("problem:lynx:" + Bytes.toString(result.getRow()));
			for (Cell cl : cells) {
//				System.out.println(
//						String.format("row:%s,family:%s,qualifier:%s, qualifier:value:%s, timestamp:%s.",
//						Bytes.toString(result.getRow()), 
//						Bytes.toString(CellUtil.cloneFamily(cl)),
//						Bytes.toString(CellUtil.cloneQualifier(cl)), 
//						Bytes.toString(CellUtil.cloneValue(cl)),
//						cl.getTimestamp()));
				list.add(Bytes.toString(CellUtil.cloneQualifier(cl)) + ":lynx:"
						+ Bytes.toString(CellUtil.cloneValue(cl)));
			}
		}
		scanner.close();
		admin.close();
		return list;
	}

	// 根据行键范围查询数据
	public static List<String> scanScope(String tableName, String start, String stop) {
		List<String> list = new ArrayList<String>();
		Admin admin;
		try {
			admin = getConnection().getAdmin();
			TableName tn = TableName.valueOf(tableName);
			if (!admin.tableExists(tn)) {
				return list;
			}
			Table table = getConnection().getTable(TableName.valueOf(tableName));

			Scan scan = new Scan();
			scan.setStartRow(Bytes.toBytes(start));
			scan.setStopRow(Bytes.toBytes(stop));
			scan.setBatch(1000);
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				Cell[] cells = result.rawCells();
				for (Cell cl : cells) {
					list.add(Bytes.toString(CellUtil.cloneValue(cl)));
				}
			}
			scanner.close();
			admin.close();
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		createTable("apitable", "apics");
//		insert("apitable", "NewUser", "apics", "newc", "Jerry");
//		byGet("apitable", "NewUser", "apics", "newc");
//		scan("apitable");
//		dropTable("apitable");
//	}

}
