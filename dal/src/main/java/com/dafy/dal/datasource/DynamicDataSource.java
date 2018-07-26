package com.dafy.dal.datasource;

import com.dafy.dal.datasource.strategy.RoundRobinStrategy;
import com.dafy.dal.datasource.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 动态数据源实现读写分离
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    private DataSource masterDataSource; //写数据源
    private List<DataSource> slavesDataSource = new ArrayList<>(); //读数据源
    private Class<? extends Strategy> strategyClass = RoundRobinStrategy.class; //获取数据源的策略
    private Strategy strategy; //策略

    @Override
    public void afterPropertiesSet() {
        if (this.masterDataSource == null) {
            throw new IllegalArgumentException("Property 'masterDataSource' is required");
        }
        setDefaultTargetDataSource(masterDataSource);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), masterDataSource);
        if (slavesDataSource != null && slavesDataSource.size() > 0) {
            for (int i = 0; i < slavesDataSource.size(); i++) {
                targetDataSources.put(DynamicDataSourceGlobal.READ.name() + "_" + i, slavesDataSource.get(i));
            }
        }

        // 设置数据源
        setTargetDataSources(targetDataSources);
        logger.debug("系统可用数据源：{}",targetDataSources.keySet());
        super.afterPropertiesSet();
    }

    /**
      * @Description: 每次去连数据库的时候，spring会调用这个方法去找对应的数据源。返回值即对应的数据源的LookUpKey.
      * @return DataSource
      * @throws
      */
    @Override
    protected Object determineCurrentLookupKey() {

        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();

        if (dynamicDataSourceGlobal == null
                || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE) {
            logger.debug("本次操作使用数据源：[{}]",DynamicDataSourceGlobal.WRITE.name());
            return DynamicDataSourceGlobal.WRITE.name();
        }

        // 为读库的时候取数据源
        String slaveDataSource = getSlaveDataSource();
        logger.debug("本次操作使用数据源：[{}]",slaveDataSource);
        return slaveDataSource;
    }

    /**
     * @Description: 获取从库
     * @Title getSlaveDataSource
     * @Author  lv bin
     * @Date 2016/11/6 16:53
     * @return javax.sql.DataSource
     * @throws
     */
    public String getSlaveDataSource() {

        if( null == strategy ){
            strategy = BeanUtils.instantiate(strategyClass);
        }
        return strategy.select(slavesDataSource, masterDataSource);
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public List<DataSource> getSlavesDataSource() {
        return slavesDataSource;
    }

    public void setSlavesDataSource(List<DataSource> slavesDataSource) {
        this.slavesDataSource = slavesDataSource;
    }

    public void setReadDataSource(DataSource readDataSource){
        this.slavesDataSource.add(readDataSource);
    }

    public Class<? extends Strategy> getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(Class<? extends Strategy> strategyClass) {
        this.strategyClass = strategyClass;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * @return 当前动态数据源持有的可用数据源列表
     */
    public List<DataSource> availableDataSources(){
        List<DataSource> datasources = new ArrayList<>();
        datasources.add(getMasterDataSource());
        datasources.addAll(getSlavesDataSource());
        return datasources;
    }
}
