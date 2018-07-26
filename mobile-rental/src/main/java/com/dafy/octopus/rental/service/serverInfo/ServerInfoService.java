package com.dafy.octopus.rental.service.serverInfo;

import com.dafy.octopus.rental.dto.DO.serverInfo.ServerInfoDO;
import com.dafy.octopus.rental.dto.VO.serverInfo.ServerInfoVO;

import java.util.List;

public interface ServerInfoService {

    Long addServerInfo(ServerInfoDO serverInfoDO);

    List<ServerInfoVO> getServerInfoList(String userId);

    ServerInfoVO getServerInfoDetail(Long id);
}
