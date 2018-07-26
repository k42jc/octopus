package com.dafy.octopus.web.core.domain;


import com.dafy.common.util.JsonUtils;
import com.dafy.common.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * spring MVC请求对象封装
 * Created by liaoxudong
 * Date:2018/1/30
 */

public class Request extends LinkedHashMap<String,Object>{

    public Request() {
        super();
    }

    public Request(int i, float v) {
        super(i, v);
    }

    public Request(int i) {
        super(i);
    }

    /**
     * 要求key转换为小写
     * @param map
     */
    public Request(Map<? extends String, ?> map) {
        super();
        if(map != null)
            map.forEach((k,v) -> this.put(k.toLowerCase(), v));
    }

    public static Request parse(Map<? extends String, ?> map){
        return new Request(map);
    }

    public Request(int i, float v, boolean b) {
        super(i, v, b);
    }

    public String getString(String key) {
        Object o = this.get(key);
        if(o == null) return null;
        else return o.toString();
    }

    public int getInt(String key) {
        Object value = this.get(key);
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object get(Object key) {
        if(key instanceof String){// 忽略大小写
            return super.get(((String) key).toLowerCase());
        }
        return super.get(key);
    }

    public boolean getBoolean(String key) {
        String value = getString(key);
        return Boolean.valueOf(value);
    }

    public <T> T get(String key,Class<T> clazz) {
        Object value = this.get(key);
        try {
            return (T) value;
        } catch (Exception e) {
            return null;
        }
    }

    public long getLong(String key) {
        String s = getString(key);
        return Long.parseLong(StringUtils.isEmpty(s)?"0":s);
    }

    public List getList(String key) {
        return get(key, List.class);
    }

    @Override
    public Object put(String  o, Object o2) {// 忽略大小写
        if (StringUtils.isEmpty(o)) {
            return "";
        }
        return super.put(o.toLowerCase(), o2);
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

    /**
     * 移除指定key 多个以","分隔  key1,key2,key3...
     * @param keys
     */
    public void removes(String keys) {
        String[] keySplits = keys.split(",");
        for (String key : keySplits) {
            this.remove(key);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if(key instanceof String){// 忽略大小写
            return super.containsKey(((String) key).toLowerCase());
        }
        return super.containsKey(key);
    }
}
