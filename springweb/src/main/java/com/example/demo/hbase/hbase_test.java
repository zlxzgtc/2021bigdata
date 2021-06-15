package com.example.demo.hbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class hbase_test {
    public static Connection connection=null;
    public static Admin admin=null;
    static {
        try {
            //1、获取配置信息
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.rootdir", "hdfs://192.168.124.116:9000/HBase");
            configuration.set("hbase.zookeeper.quorum","Master,Slave1,Slave2");
            //2、创建连接对象
            connection= ConnectionFactory.createConnection(configuration);
            //3、创建Admin对象
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断表是否存在
    public static boolean isTableExiat(String tableName) throws IOException {
        boolean exists = admin.tableExists(TableName.valueOf(tableName));
        return exists;
    }

    public static void close(){
        if (admin!=null){
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println(isTableExiat("student"));
        //关闭资源
        close();
    }
}
