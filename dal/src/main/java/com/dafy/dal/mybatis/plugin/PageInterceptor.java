package com.dafy.dal.mybatis.plugin;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.util.ReflectionUtils;
import com.dafy.common.util.StringUtils;
import com.dafy.dal.page.PageStrategy;
import com.dafy.dal.page.dialect.MySQLPageStrategy;
import com.dafy.dal.page.po.Page;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 通过拦截prepareStatement和Executor.query()实现
 * 前者获取分页的总条目数以及组装分页sql
 * 后者获取查询结果集放入分页参数Page对象
 * 配置：
 * <p/>
 * <pre>
 *  <plugins>
 *    <plugin interceptor="PageInterceptor">
 *      <property name="pageStrategyClass" value="MySQLDialect"/>
 *    </plugin>
 *  </plugins>
 * </pre>
 * Created by liaoxudong
 * Date:2017/10/26
 */


@Intercepts({
        @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})
        , @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PageInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
    //数据方言类
    private String pageStrategyClass = MySQLPageStrategy.class.getName();
    //默认数据方言
    private PageStrategy pageStrategy = new MySQLPageStrategy();

    /**
     * 拦截后要执行的方法
     * 拦截查询方法参数为Page对象的，如selectByName(Page page)
     */
    public Object intercept(Invocation invocation) throws Throwable {

        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        if (target instanceof RoutingStatementHandler) { //与查询，设置分页sql

            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            ParameterHandler parameterHandler = handler.getParameterHandler();
            Page page = ReflectionUtils.findMemberByType(parameterHandler.getParameterObject(), Page.class);
            if (null != page) {

                Connection connection = (Connection) args[0];
                BoundSql boundSql = handler.getBoundSql();
                // 如果指定了分页统计sql，使用确定的统计sql，一是效率较高，二是关联表时分页查询统计更精确
                Long totalSize = count(connection, boundSql.getSql(),page.getCountSql(), parameterHandler);
                page.setCountSql("");// 避免查询结果中还有分页sql信息，一不小心返回到前端
                page.setTotalSize(totalSize);
                String pageSql = pageStrategy.getPageSql(boundSql.getSql(), page);
                ReflectionUtils.setFieldValue(boundSql, "sql", pageSql);
            }
            return invocation.proceed();

        } else if (target instanceof Executor) { //查询操作拦截
            Object result = loadPageCache(target,args);// 获取分页结果缓存
            if(result == null) {
                result = invocation.proceed();
            }
            return setPageData(target,args,result);

        }
        return invocation.proceed();
    }

    /**
     * 分两种情况，缓存结果 or 获取缓存
     * @param target
     * @param args
     * @param result
     * @return
     */
    private Object setPageData(Object target,Object[] args, Object result) {
        boolean needCache = true;
        if(result instanceof Page)//如果传进来的参数直接是page 则表示当前操作是获取缓存结果
            needCache = false;
        for (Object object : args) {
            Page page = ReflectionUtils.findMemberByType(object, Page.class);
            if (null != page) {
                if(needCache){
                    page.setData((java.util.List) result);
                    cachePageData(target,args,page);// 分页查询结果缓存
                }else{
                    Page p = (Page)result;
                    page.setData(p.getData());
                    page.setTotalSize(p.getTotalSize());
                    result = p.getData();
                }
                page.clearParams();// 查询完毕清除查询参数
                break;
            }
        }
        return result;
    }

    /**
     * 分页查询结果缓存
     * @param target
     * @param args
     * @param result
     */
    private void cachePageData(Object target, Object[] args, Object result) {
        // 非分页查询 或者未指定缓存分页结果 则不做任何操作
        if(args.length != 4 || !isPageQuery(args) || !((Page) args[1]).getCache())
            return;
        MappedStatement ms = (MappedStatement)args[0];
        String cacheKey = createCacheKey((Executor)target,ms,args);
        String majorKey = "mybatis:cache:" + ms.getId();
        logger.info("page data cache major key:【{}】",majorKey);
        // 设置缓存十分钟
        CacheFactory.putHKey(majorKey,cacheKey,result,10 * 60);
    }

    /**
     * 使用Executor创建缓存key
     * @param executor
     * @param ms
     * @param args
     * @return
     */
    private String createCacheKey(Executor executor,MappedStatement ms, Object[] args) {
        Page page = (Page) args[1];
        // 修复第二页后无法获取数据的情况，因为修改rowBounds后myBatis过滤结果集会过滤掉
        //@see DefaultResultHandler.skipRows
        RowBounds rowBounds = new RowBounds(page.getStart(), page.getPageSize());
        BoundSql boundSql = ms.getBoundSql(page);
        CacheKey cacheKey = executor.createCacheKey(ms, page, rowBounds, boundSql);
        // 清除sql中间过多的空格
        return org.apache.commons.lang3.StringUtils.deleteWhitespace(cacheKey.toString());
    }

    /**
     * 针对系统分页的缓存机制
     * @param target
     * @param args
     * @return
     */
    private Object loadPageCache(Object target, Object[] args) {
        if (!isPageQuery(args) || args.length != 4 || !((Page) args[1]).getCache()) {
            return null;
        }
        MappedStatement ms = (MappedStatement)args[0];
        String cacheKey = createCacheKey((Executor)target,ms,args);
        Object result = CacheFactory.getHValue("mybatis:cache:" + ms.getId(), cacheKey);
        return result;

    }

    /**
     * 判断是否为分页查询
     * @param args
     * @return
     */
    private boolean isPageQuery(Object[] args) {
        for (Object object : args) {
            Page page = ReflectionUtils.findMemberByType(object, Page.class);
            if (page != null) {// 非系统分页查询 返回null
                return true;
            }
        }
        return false;
    }


    /**
     * 拦截器对应的封装原始对象的方法
     */
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler || target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }

    public PageStrategy getPageStrategy() {
        return pageStrategy;
    }

    public void setPageStrategy(PageStrategy pageStrategy) {
        this.pageStrategy = pageStrategy;
    }

    public String getPageStrategyClass() {
        return pageStrategyClass;
    }

    public void setPageStrategyClass(String pageStrategyClass) {
        this.pageStrategyClass = pageStrategyClass;
        try {
            if (null != pageStrategyClass) {
                Class cls = Class.forName(pageStrategyClass);
                if (cls.isAssignableFrom(PageStrategy.class)) {
                    setPageStrategy((PageStrategy) cls.newInstance());
                } else {
                    logger.warn("参数 {} 不是 {} 的子类,使用mysql分页策略", pageStrategyClass, PageStrategy.class);
                }
            }
        } catch (Exception e) {
            logger.error(" 分页策略注入错误! {} ", e.getMessage());
        }
    }

    /**
     * 执行 count 操作
     * @param connection  数据库连接
     * @param realSql     实际查询数据的sql
     * @param countSql    专门用于统计结果条数的sql
     * @param parameterHandler  参数设置处理器
     * @return
     */
    private Long count(Connection connection, String realSql,String countSql, ParameterHandler parameterHandler) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = null;
            if (!StringUtils.isEmpty(countSql)) {// 只有用户指定了统计条数的sql时才去自定义组装
                sql = countSql + " " + realSql.substring(realSql.toLowerCase().lastIndexOf("where"));
            }else{//默认还是结合对应数据库策略使用原sql去组装分页sql
                sql = pageStrategy.getCountSql(realSql);
            }
            preparedStatement = connection.prepareStatement(sql);
            parameterHandler.setParameters(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return (Long) JdbcUtils.getResultSetValue(resultSet, 1, Long.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(preparedStatement);
        }
        return 0l;
    }


}
