package com.dafy.dal.page.dialect;

import com.dafy.dal.page.PageStrategy;
import com.dafy.dal.page.po.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql分页sql
 * Created by liaoxudong
 * Date:2017/10/26
 */

public class MySQLPageStrategy extends PageStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MySQLPageStrategy.class);

    @Override
    public boolean support() {
        return true;
    }

    @Override
    public String getPageSql(String sql, Page page) {
        logger.debug("组建MySQL分页sql...");
        StringBuffer querySql =
                new StringBuffer("SELECT * FROM ( ").append(sql).append(" ) AS COUNT_PAGE")
                        .append(" LIMIT ").append(page.getStart()).append(" , ").append(page.getPageSize());
        return querySql.toString();
    }
}
