package com.lqf.xiaofangshu.data.align.constant;

/**
 * @author: 李启仿
 * @date: 2025/7/29
 * @description: 表常量类
 */

public class TableConstants {

    /**
     * 表名中的分隔符
     */
    private static final String TABLE_NAME_SEPARATE = "_";

    /**
     * 拼接表名后缀
     * @param hashKey
     * @return
     */
    public static String buildTableNameSuffix(String date, long hashKey) {
        // 拼接完整的表名
        return date + TABLE_NAME_SEPARATE + hashKey;
    }

}
