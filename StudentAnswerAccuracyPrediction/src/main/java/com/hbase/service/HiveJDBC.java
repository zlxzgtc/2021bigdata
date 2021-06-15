package com.hbase.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

/**
 * JDBC 操作 Hive（注：JDBC 访问 Hive 前需要先启动HiveServer2）
 */
public class HiveJDBC {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://192.168.43.68:10000/default";
    private static String user = "hive";
    private static String password = "hive";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static void main(String[] args) throws Exception {
//        selectCourseBasic();
        putCourseVnum();
    }

    // 加载驱动、创建连接dummyhost
    public static void init() throws Exception {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url,user,password);
        stmt = conn.createStatement();
    }

    // 创建数据库
    @Test
    public void createDatabase() throws Exception {
        String sql = "create database hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有数据库
    @Test
    public void showDatabases() throws Exception {
        String sql = "show databases";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 创建表
    @Test
    public void createTable() throws Exception {
        String sql = "create table emp(\n" +
                "empno int,\n" +
                "ename string,\n" +
                "job string,\n" +
                "mgr int,\n" +
                "hiredate string,\n" +
                "sal double,\n" +
                "comm double,\n" +
                "deptno int\n" +
                ")\n" +
                "row format delimited fields terminated by '\\t'";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有表
    @Test
    public void showTables() throws Exception {
        String sql = "show tables";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 查看表结构
    @Test
    public void descTable() throws Exception {
        String sql = "desc emp";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2));
        }
    }

    // 加载数据
    @Test
    public void loadData() throws Exception {
        String filePath = "/home/hadoop/data/emp.txt";
        String sql = "load data local inpath '" + filePath + "' overwrite into table emp";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    //建course表，并将p_num存入
    public static void selectCourseBasic() throws Exception {
        init();
        Hbase.init();
        Hbase.createTable("course",new String[]{"info"});
        String sql = "select course_id,count(*)-1 p_num from (\n" +
                "    select course_id,itemp from (\n" +
                "        select course_id,split(item,'P_') items from course_info) sp \n" +
                "    lateral view explode(items) adTable AS itemp) sump \n" +
                "group by course_id";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        System.out.println("course_id" + "\t" + "p_num" );
        while (rs.next()) {
            String course_id = rs.getString("course_id");
            String p_num = rs.getString("p_num");
            Hbase.insertData("course",course_id,"info","p_num",p_num);
            System.out.println(course_id+"   "+p_num) ;
        }
        Hbase.close();
        destory();
    }
    public static void putCourseVnum() throws Exception {
        init();
        Hbase.init();
        String sql = "select course_id,count(*)-1 v_num from (\n" +
                "    select course_id,itemp from (\n" +
                "        select course_id,split(item,'V_') items from course_info) sp \n" +
                "    lateral view explode(items) adTable AS itemp) sump \n" +
                "group by course_id";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        System.out.println("course_id" + "\t" + "v_num" );
        while (rs.next()) {
            String course_id = rs.getString("course_id");
            String v_num = rs.getString("v_num");
            Hbase.insertData("course",course_id,"info","v_num",v_num);
            System.out.println(course_id+"   "+v_num) ;
        }
        Hbase.close();
        destory();
    }
    public static void problemBasic() throws Exception {
        init();
        Hbase.init();
        String sql = "select problem_id,concept,detail from problem_info";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String course_id = rs.getString("problem_id");
            String v_num = rs.getString("v_num");
            Hbase.insertData("course",course_id,"info","v_num",v_num);
            System.out.println(course_id+"   "+v_num) ;
        }
        Hbase.close();
        destory();
    }



    // 统计查询（会运行mapreduce作业）
    @Test
    public void countData() throws Exception {
        String sql = "select item from course_info";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getArray(1));
            System.out.println(rs.getInt(1) );
        }
    }

    // 删除数据库
    @Test
    public void dropDatabase() throws Exception {
        String sql = "drop database if exists hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 删除数据库表
    @Test
    public void deopTable() throws Exception {
        String sql = "drop table if exists emp";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }


    public static void destory() throws Exception {
        if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}