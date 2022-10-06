package com.yujiangjun.util;

import com.yujiangjun.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static com.yujiangjun.constants.Constant.HBASE_HIS_MES_COL_FAMILY;
import static com.yujiangjun.constants.Constant.HBASE_HIS_MES_TABLE;
@Slf4j
public class HisMessageUtil {

    public static void saveMessage(TextMessage message){
        Connection connection = SpringContextUtil.getBean(Connection.class);
        Put put = new Put(Bytes.toBytes(message.getMsgId()));

        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("msgId"),Bytes.toBytes(message.getMsgId()));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("cat"),Bytes.toBytes(message.getCat()));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("type"),Bytes.toBytes(message.getType()));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("sendTime"),Bytes.toBytes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getSendTime())));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("messageType"),Bytes.toBytes(message.getMessageType()));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("content"),Bytes.toBytes(message.getContent()));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("sendUserId"),Bytes.toBytes(message.getSendUserId()));
        put.addColumn(Bytes.toBytes(HBASE_HIS_MES_COL_FAMILY),Bytes.toBytes("targetId"),Bytes.toBytes(message.getTargetId()));

        try {
            connection.getTable(TableName.valueOf(HBASE_HIS_MES_TABLE)).put(put);
        } catch (IOException e) {
            log.error("hbase 操作失败",e);
        }
    }
}
