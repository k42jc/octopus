package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.SysConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysConfigMapper {
    int deleteByPrimaryKey(Long id);

    @Select("select * from T_SYS_CONFIG order by type,`order` ")
    List<SysConfig> selectAll();

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    /**
     * 根据分类获取到配置项
     * @param type
     * @return
     */
    @Select("select * from T_SYS_CONFIG where TYPE=#{type} order by `order`")
    List<SysConfig> selectByType(@Param("type") String type);

    /**
     * 获取某配置的下级配置 过滤的
     * @param pid 父级id
     * @return
     */
    @Select("select * from T_SYS_CONFIG where PID=#{pid} and status='0' order by `order`")
    List<SysConfig> selectAvalibleChildrenByPId(@Param("pid") Long pid);

    /**
     * 获取所有 不过滤禁用项
     * @param pid
     * @return
     */
    @Select("select * from T_SYS_CONFIG where PID=#{pid} order by `order`")
    List<SysConfig> selectChildrenByPId(@Param("pid") Long pid);

    @Update("update T_SYS_CONFIG set `ORDER`=#{oldOrder} where TYPE=#{type} and `ORDER`=#{order}")
    void updateOrderExists(@Param("type") String type, @Param("oldOrder") String oldOrder, @Param("order") String order);

    @Update("update T_SYS_CONFIG set `ORDER`=-1 where id=#{id}")
    void updateOrderToZero(@Param("id") long id);
}