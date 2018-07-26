package com.dafy.dal.page;

import com.dafy.dal.page.po.Page;

/**
 * 构建分页sql抽象类
 * Created by liaoxudong
 * Date:2018/4/10
 */

public abstract class PageStrategy {

    /**
     * 是否支持分页
     *
     * @return
     */
    protected abstract boolean support();

    /**
     * 获取查询总条目数
     *
     * @param sql
     * @return
     */
    public String getCountSql(String sql) {
        if(!support()){
            throw new IllegalStateException("current datasource not support page sql");
        }
        return new StringBuffer("SELECT COUNT(1) AS ROW_COUNT FROM ( ").append(sql).append(" ) AS COUNT_QUERY").toString();
    }

    /**
     * 获取不同数据库的分页sql
     *
     * @param sql
     * @param page
     * @return
     */
    public abstract String getPageSql(String sql, Page page);
}
