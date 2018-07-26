package com.dafy.octopus.rental.impl.systemConfig;

import com.dafy.common.exception.ServerException;
import com.dafy.octopus.rental.dto.VO.systemConfig.GoodsTypeVO;
import com.dafy.octopus.rental.impl.BaseService;
import com.dafy.octopus.rental.service.systemConfig.GoodsTypeService;
import com.dafy.octopus.rental.dto.DO.systemConfig.GoodsTypeDO;
import com.dafy.octopus.rental.mapper.systemConfig.GoodsTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodsTypeServiceImpl extends BaseService implements GoodsTypeService {
    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    @Transactional
    public Long addGoodsType(GoodsTypeDO goodsTypeDO) {

        HashMap<String, Object> params = new HashMap<>(3);
        params.put("parentId", goodsTypeDO.getParentId());
        params.put("curName", goodsTypeDO.getCurName());

        if (!goodsTypeMapper.query(params).isEmpty()) throw new ServerException("1025", "已存在相同的名称");

        add(goodsTypeMapper, goodsTypeDO);
        return goodsTypeDO.getId();
    }

    @Override
    @Transactional
    public void updateGoodsType(GoodsTypeDO goodsTypeDO) {
        GoodsTypeVO goodsTypeVO = goodsTypeMapper.get(goodsTypeDO.getId());
        HashMap<String, Object> params = new HashMap<>(3);
        params.put("parentId", goodsTypeVO.getParentId());
        params.put("curName", goodsTypeDO.getCurName());

        if (!goodsTypeMapper.query(params).isEmpty()) throw new ServerException("1025", "已存在相同的名称");

        update(goodsTypeMapper, goodsTypeDO);
    }

    @Override
    @Transactional
    public void deleteGoodsType(Long id) {
        goodsTypeMapper.delete(id);
    }

    @Override
    public List<GoodsTypeVO> getGoodsType(Long parentId) {
        return goodsTypeMapper.getChildGoodsType(parentId);
    }

    /**
     * 获取所有的子目录
     * @param parentId
     * @return
     */
    @Override
    public List<GoodsTypeVO> getGoodsTypeAll(Long parentId) {

        if (parentId != 0) {
            GoodsTypeVO goodsType = goodsTypeMapper.get(parentId);
            getGoodsTypeAll(goodsType);

            List<GoodsTypeVO> result = new ArrayList<GoodsTypeVO>();
            result.add(goodsType);
            return result;
        }

        List<GoodsTypeVO> result = getGoodsType(parentId);

        if (result == null || result.size() == 0) return result;

        for (GoodsTypeVO goodsType: result
             ) {
            getGoodsTypeAll(goodsType);
        }

        return result;
    }

    public void getGoodsTypeAll(GoodsTypeVO goodsType){
        if (goodsType != null){
            List<GoodsTypeVO> childList = getGoodsType(goodsType.getId());
            if (childList != null && childList.size() > 0) {
                goodsType.setChildList(childList);

                for (GoodsTypeVO g: childList
                        ) {
                    getGoodsTypeAll(g);
                }
            }
        }
    }


}
