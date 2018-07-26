package com.dafy.common.util;


import com.dafy.common.http.HttpClientBuilder;
import com.dafy.common.http.HttpClientBuilder.HttpDataType;
import com.dafy.common.http.HttpMethods;

import java.util.Map;

/**
 * Http常用请求封装
 * Created by liaoxudong on 2017/10/29.
 */
public class HttpUtils {





    /**
     * Get获取json字符串
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        return HttpClientBuilder.request(url, params, String.class, HttpMethods.GET, HttpDataType.NORMAL, null);
    }

    public static <T> T doGet(String url, Map<String, Object> params,Class<T> clazz) {
        return HttpClientBuilder.request(url, params, clazz, HttpMethods.GET, HttpDataType.NORMAL, null);
    }

    /**
     * 普通get操作，获取字符串
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return HttpClientBuilder.request(url, null, String.class, HttpMethods.GET, HttpDataType.NORMAL, null);
    }

    /**
     * 待head get操作，获取字符串
     * @param url
     * @return
     */
    public static String doGetWithHead(String url,Map<String,Object> headers) {
        return HttpClientBuilder.request(url, null, String.class, HttpMethods.GET, HttpDataType.NORMAL, headers);
    }

    /**
     * 待head get操作，获取指定对象
     * @param url
     * @return
     */
    public static <T>  T doGetWithHead(String url,Map<String,Object> headers,Class<T> clazz) {
        return HttpClientBuilder.request(url, null, clazz, HttpMethods.GET, HttpDataType.NORMAL, headers);
    }

    /**
     * https操作，获取字符串
     * @param url
     * @param head 提供SSL校验所需要的参数，keystore_type,keystore_path,keystore_pwd
     * @return
     */
    public static String doSSLGet(String url,Map<String,Object> head) {
        return HttpClientBuilder.request(url, null, String.class, HttpMethods.GET, HttpDataType.NORMAL, head);
    }

    /**
     * 默认get 请求
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T doGet(String url, Class<T> clazz) {
        return HttpClientBuilder.request(url, null, clazz, HttpMethods.GET, HttpDataType.NORMAL, null);
    }

    /**
     * 下载 指定请求头
     * @param url
     * @param headers
     * @return 返回二进制
     */
    public static byte[] download(String url, Map<String,Object> headers) {
        return HttpClientBuilder.request(url, null, byte[].class, HttpMethods.GET, HttpDataType.NORMAL, headers);
    }

    /**
     * 默认Post 请求
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Map<String,Object> params, Class<T> clazz) {
//        String resultStr = HttpCliBuilder.post(HttpConfig.custom().url(url).map(params));
        return HttpClientBuilder.request(url, params, clazz, HttpMethods.POST, HttpDataType.NORMAL, null);
    }

    /*public static String doPost(String url, String sendData) {
//        String resultStr = HttpCliBuilder.post(HttpConfig.custom().url(url).json(sendData));
        String resultStr = doPost(url,params,false);
        return resultStr;
    }*/

    /**
     * 默认pos请求，不包括文件传输
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url,  Map<String,Object> params) {
        return HttpClientBuilder.request(url, params, String.class, HttpMethods.POST, HttpDataType.NORMAL, null);
    }

    public static String doPost(String url,  String jsonParam) {
        return HttpClientBuilder.request(url, jsonParam, String.class, HttpMethods.POST, HttpDataType.NORMAL, null);
    }

    public static <T> T doPost(String url,  String jsonParam,Class<T> clazz) {
        return HttpClientBuilder.request(url, jsonParam, clazz, HttpMethods.POST, HttpDataType.NORMAL, null);
    }

    public static String doPost(String url, String json, Map<String, Object> header) {
        return HttpClientBuilder.request(url, json, String.class, HttpMethods.POST, HttpDataType.NORMAL, header);
    }

    public static <T> T doPostWithHead(String url, Map<String,Object> params, Map<String, Object> header,Class<T> clazz) {
        return HttpClientBuilder.request(url, params, clazz, HttpMethods.POST, HttpDataType.NORMAL, header);
    }



    /**
     * 包含文件传输的POST
     * @param url
     * @param params
     * @return
     */
    public static String upload(String url,  Map<String,Object> params){
        return HttpClientBuilder.request(url, params, String.class, HttpMethods.POST, HttpDataType.FILE, null);
    }
    
    public static String upload(String url,  Map<String,Object> params,Map<String, Object> header){
        return HttpClientBuilder.request(url, params, String.class, HttpMethods.POST, HttpDataType.FILE, header);
    }

    public static <T> T upload(String url, Map<String, Object> params, Class<T> clazz) {
        return HttpClientBuilder.request(url, params, clazz, HttpMethods.POST, HttpDataType.FILE, null);
    }

    public static void main(String[] args) {
        /*String s = doGet("https://www.baidu.com");
        System.err.println(s);*/

        String s1 = doPost("http://10.20.11.71:8888/apply/validate", "{\"busiData\":{\"seqNo\":\"0.6708843645421741\",\"accountNo\":\"6226097803789999\",\"name\":\"韦升志\",\"busiType\":\"DFY8031\",\"mobileNo\":\"15118023314\",\"busiDesc\":\"4要素校验\",\"idNo\":\"231003198511090714\"},\"userData\":{\"chnlId\":\"dup-test\",\"orgCode\":\"dafy\",\"transDate\":\"2017-05-25  12:22:21\",\"transNo\":\"TransNo0.6668358687825849\"},\"secrityData\":{\"password\":\"dup-test123\",\"userName\":\"dup-test\"}}");
        System.out.println(s1);
    }


    /**********/

    private HttpUtils() {
    }


}
