package com.dafy.octopus.web.core.utils;


import com.dafy.common.exception.ExceptionCode;
import com.dafy.common.exception.ServerException;
import com.dafy.common.po.Response;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.domain.ResponseCode;

import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/1/30
 */

public class CommonUtils {

    /**
     * 判断某map是否包含具体key
     * 不包含则抛出异常
     *
     * @param map
     * @param params
     */
    public static void assertHasParams(Map map, String params) {
        if (map == null || map.isEmpty() || params == null || StringUtils.isEmpty(params)) {
            throw new ServerException(ExceptionCode.PARAM_ERROR);
        }
        String[] strs = params.split(",");
        for (String str : strs) {
            if (!map.containsKey(str.toLowerCase())) {
                throw new ServerException(ExceptionCode.PARAM_ERROR);
            }
        }
    }

    /**
     * 构建成功返回值
     *
     * @return
     */
    public static Response buildSuccessResp(Object data) {
        if (data == null) {
            throw new ServerException(ExceptionCode.PARAM_ERROR);
        }
        Response response = new Response();
        response.setData(data);
        return response;
    }

    /**
     * 构建成功返回值
     *
     * @return
     */
    public static Response buildSuccessResp() {
        return new Response();
    }

    /**
     * 根据ResponseCode构建返回值
     *
     * @param code
     * @return
     */
    public static Response buildResp(ResponseCode code) {
        return new Response(code.getCode(), code.getDesc());
    }

    /**
     * 抛出异常
     *
     * @param code
     */
    public static void throwException(ResponseCode code) {
        throwException(code.getCode(), code.getDesc());
    }

    /**
     * 抛出异常
     *
     * @param code
     */
    public static void throwException(ExceptionCode code) {
        throwException(code.getCode(), code.getDesc());
    }

    /**
     * 抛出异常
     *
     * @param params 参数集 param1,param2,param3
     */
    public static void throwIllegalParamsException(String params) {
        throwException(ExceptionCode.PARAM_ERROR.getCode(),ExceptionCode.PARAM_ERROR.getDesc()+":"+params);
    }


    /**
     * 抛出异常
     *
     * @param code
     * @param desc
     */
    public static void throwException(String code, String desc) {
        throw new ServerException(code, desc);
    }

}
