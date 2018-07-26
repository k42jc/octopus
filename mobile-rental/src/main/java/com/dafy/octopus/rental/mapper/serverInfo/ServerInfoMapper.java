package com.dafy.octopus.rental.mapper.serverInfo;

import com.dafy.octopus.rental.dto.VO.serverInfo.ServerInfoVO;
import com.dafy.octopus.rental.mapper.IBaseMapper;
import com.dafy.octopus.rental.dto.DO.serverInfo.ServerInfoDO;

import java.util.List;

public interface ServerInfoMapper extends IBaseMapper<ServerInfoDO, ServerInfoVO, Long> {
    List<ServerInfoVO> getByUserId(String userId);
}
