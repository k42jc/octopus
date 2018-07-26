package com.dafy.octopus.rental.impl.serverInfo;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.octopus.rental.impl.BaseService;
import com.dafy.octopus.rental.dto.DO.serverInfo.ServerInfoDO;
import com.dafy.octopus.rental.dto.DO.serverInfo.ServerInfoGoodsTypeDO;
import com.dafy.octopus.rental.dto.VO.serverInfo.ServerInfoVO;
import com.dafy.octopus.rental.dto.VO.systemConfig.GoodsTypeVO;
import com.dafy.octopus.rental.mapper.serverInfo.ServerInfoGoodsTypeMapper;
import com.dafy.octopus.rental.mapper.serverInfo.ServerInfoMapper;
import com.dafy.octopus.rental.mapper.systemConfig.GoodsTypeMapper;
import com.dafy.octopus.rental.service.serverInfo.ServerInfoService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServerInfoServiceImpl extends BaseService implements ServerInfoService {
    @Resource
    private ServerInfoMapper serverInfoMapper;
    @Resource
    private ServerInfoGoodsTypeMapper serverInfoGoodsTypeMapper;
    @Resource
    private GoodsTypeMapper goodsTypeMapper;


    @Override
    @Transactional
    public Long addServerInfo(ServerInfoDO serverInfoDO) {
        serverInfoDO.setServerCode(getServerCode());
        add(serverInfoMapper, serverInfoDO);
        addServerInfoGoodsType(serverInfoDO.getId(), serverInfoDO.getGoodsTypeName(), serverInfoDO.getGoodsTypeId());
        return serverInfoDO.getId();
    }

    private String getServerCode(){
        StringBuffer result = new StringBuffer("DFPR");

        String dateStr = new DateTime().toString("yyyyMMdd");
        result.append(dateStr);

        //自增id
        Long incrID = CacheFactory.incr("OCTOPUS_SERVER_INFO_CODE_" + dateStr);
        //为了保证流水号长度固定，自增数值需要减少一位，目前减少最高位
        result.append(String.format("%06d", incrID));

        return result.toString();
    }

    private void addServerInfoGoodsType(Long serverInfoId, String goodsTypeName,  Long goodsTypeId){

        List<ServerInfoGoodsTypeDO> list = new ArrayList<>(5);

        while (true){
            GoodsTypeVO goodsType = goodsTypeMapper.get(goodsTypeId);
            list.add(new ServerInfoGoodsTypeDO(goodsTypeId, goodsTypeName,  serverInfoId));
            if (goodsType.getParentId() == 0L) break;
            goodsTypeId = goodsType.getParentId();
        }

        int index = 1;
        for (int i = list.size() - 1; i >= 0; i--){
            ServerInfoGoodsTypeDO serverInfoGoodsType = list.get(i);
            add(serverInfoGoodsTypeMapper, new ServerInfoGoodsTypeDO(serverInfoGoodsType.getGoodsId(), serverInfoGoodsType.getName(), index, serverInfoGoodsType.getServerInfoId()));
            index++;
        }
    }

    @Override
    public List<ServerInfoVO> getServerInfoList(String userId) {

        return serverInfoMapper.getByUserId(userId);
    }

    @Override
    public ServerInfoVO getServerInfoDetail(Long id) {
        ServerInfoVO serverInfo = serverInfoMapper.get(id);
        serverInfo.setGoodsTypeList(serverInfoGoodsTypeMapper.getByServerInfoId(id));
        return serverInfo;
    }
}
