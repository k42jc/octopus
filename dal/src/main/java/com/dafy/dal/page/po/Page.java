package com.dafy.dal.page.po;

import com.dafy.common.util.JsonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 分页结构封装
 *
 */
public class Page<T> implements Serializable {

    // 查询参数 在使用时直接params.xxx即可
    private Map<String,Object> params = new HashMap<>(32);
    // 用于保存返回数据 在分页拦截器注入
    private List<T> data;
    // 总数据量
    private Long totalSize;
    // 每页数据量
    private Integer pageSize = 20;
    // 页码
    private Integer pageNumber = 1;
    // 总页数 通过数据量/每页数据量计算得到
    private Integer totalPage;
    // 是否在分页拦截器缓存分页查询 默认不缓存
    private Boolean cache = false;
    // 统计分页数量的sql 符合最快速的查询语句即可 不需要指定查询条件 在分页插件中自动获取查询条件
    private String countSql;

    public Page() {}

    public Page(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public void clearParams(){
        this.params.clear();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {

        this.totalSize = totalSize;
        this.totalPage = Double.valueOf(Math.ceil(new Double(totalSize) / pageSize)).intValue();
    }


    public Integer getStart() {
        return (pageNumber - 1) * pageSize;
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = Math.max(1, pageNumber);
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
