package com.dafy.dal.tx;

import com.dafy.dal.datasource.DynamicDataSource;
import com.dafy.dal.datasource.DynamicDataSourceGlobal;
import com.dafy.dal.datasource.DynamicDataSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @Description: 事务处理
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceTransactionManager.class);

    public DynamicDataSourceTransactionManager(DynamicDataSource dynamicDataSource) {
        super(dynamicDataSource);
    }

    /**
     * 只读事务到读库，读写事务到写库
     * @param transaction
     * @param definition
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        logger.debug("开始动态数据源事务处理");

        //设置数据源
        boolean readOnly = definition.isReadOnly();
        if(readOnly) {
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.READ);
        } else {
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    /**
     * 清理本地线程的数据源
     * @param transaction
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        logger.debug("完成动态数据源事务处理，清空当前绑定数据源");
        DynamicDataSourceHolder.clearDataSource();
    }
}
