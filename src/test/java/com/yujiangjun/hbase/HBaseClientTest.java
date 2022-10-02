package com.yujiangjun.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HBaseClientTest {
    private static Admin admin;
    private static final String COLUMN_FAMILY_1="cf1";
    private static final String COLUMN_FAMILY_2="cf2";

    public static Connection initHBase() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","hbase");
        config.set("hbase.zookeeper.property.clientPort","2181");
//        config.set("hbase.master","127.0.0.1:60000");
        return ConnectionFactory.createConnection(config);
    }


    public static void createTable(TableName tableName,String[] colFamily) throws IOException {
        admin = initHBase().getAdmin();
        if (admin.tableExists(tableName)){
            System.out.println("Table Already Exists!!!!");
        }else {
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            for (String col : colFamily) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                tableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(tableDescriptor);
            System.out.println("table Create Successful");
        }
    }

    @Test
    public void createTableTest() throws IOException {
        String[] cols = new String[2];
        cols[0]="name";
        cols[1]="age";
        createTable(getTbName("stu"),cols);
    }



    public static TableName getTbName(String tableName){
        return TableName.valueOf(tableName);
    }

    public static void deleteTb(TableName tableName) throws IOException {
        admin = initHBase().getAdmin();
        if (admin.tableExists(tableName)){
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("Table Delete Successful");
        }else {
            System.out.println("Table does not exist");
        }
    }
    @Test
    void deleteTbTest() throws IOException {
        deleteTb(getTbName("stu"));
    }

    public static void insertData(TableName tableName,Student student) throws IOException {
        Put put = new Put(Bytes.toBytes(student.getId()));
        put.addColumn(Bytes.toBytes("name"),Bytes.toBytes("name"),Bytes.toBytes(student.getName()));
        put.addColumn(Bytes.toBytes("age"),Bytes.toBytes("age"),Bytes.toBytes(student.getAge()));
        initHBase().getTable(tableName).put(put);
        System.out.println("Data insert success");
    }

    public static ResultScanner getScanner(TableName tableName) throws IOException {
        Table table = initHBase().getTable(tableName);
        Scan scan = new Scan();
        return table.getScanner(scan);
    }
    @Test
    void scannerTest() throws IOException {
        ResultScanner stu = getScanner(getTbName("stu"));
        stu.forEach(result -> System.out.println(Bytes.toString(result.getRow()) + "->" + Bytes
                .toString(result.getValue(Bytes.toBytes("name"), Bytes.toBytes("name")))));
        stu.close();
    }
    @Test
    void insertDataTest() throws IOException {
        Student s1 = new Student(1, "张三", 20);
        Student s2 = new Student(2, "李四", 21);
        insertData(getTbName("stu"),s1);
        insertData(getTbName("stu"),s2);
    }

    public static void deleteData(TableName tableName,String rowKey) throws IOException {
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        initHBase().getTable(tableName).delete(delete);
    }
}
