package com.dafy.dal.datasource.strategy;
import javax.sql.DataSource;

/**
 * @Description: 负载均衡策略
 */
public interface Strategy {
    String select(java.util.List<DataSource> Slaves, DataSource master);
}

