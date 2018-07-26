package com.dafy.octopus.rental.mapper;

/**
 * DAO支持类实现
 * @param <DO,VO,ID>
 */
public interface IBaseMapper<DO,VO,ID> {

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public VO get(ID id);

    /**
     * 插入数据
     * @param entity
     * @return
     */
    public int add(DO entity);

    /**
     * 更新数据
     * @param entity
     * @return
     */
    public int update(DO entity);
    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param id
     * @see public int delete(ID id)
     * @return
     */
    public int delete(ID id);
}
