package com.dafy.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 配置工具测试类
 * Created by liaoxudong
 * Date:2018/4/11
 */


public class ConfigUtilsTest {

    @Test
    public void loadYmlMapTest() {
        Assert.assertEquals(ConfigUtils.YAML.getProp("key"), "value");
    }


    /**
     * 加载层叠map
     */
    @Test
    public void loadYmlMuiltiMapTest(){
        Assert.assertEquals(ConfigUtils.YAML.getProp("spring.test.key"), "value");
    }

}
