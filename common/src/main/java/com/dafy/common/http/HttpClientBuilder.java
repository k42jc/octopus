package com.dafy.common.http;

import com.dafy.common.util.StringUtils;
import com.dafy.common.constant.Constants;
import com.dafy.common.util.ConfigUtils;
import com.dafy.common.util.JsonUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 带HTTP 连接池
 * 可配置
 * Created by liaoxudong
 * Date:2017/11/20
 */

public final class HttpClientBuilder {

    // 优先加载配置属性，否则使用默认值
    private static final int HTTPCLIENT_POOL_MAXTOTAL = ConfigUtils.get("config.httpClient.poolMaxTotal",Integer.class,500);
    private static final int HTTPCLIENT_DEFAULT_PRE_ROUTES = ConfigUtils.get("config.httpClient.defaultPreRoutes",Integer.class,250);


    private static final int HTTPCLIENT_REQUEST_TIMEOUT = ConfigUtils.get("config.httpClient.requestTimeOut",Integer.class,30000);
    private static final int HTTPCLIENT_CONNECT_TIMEOUT = ConfigUtils.get("config.httpClient.connectTimeOut",Integer.class,30000);
    private static final int HTTPCLIENT_SOCKET_TIMEOUT = ConfigUtils.get("config.httpClient.socketTimeOut",Integer.class,30000);;

    private static final Logger logger = LoggerFactory.getLogger(HttpClientBuilder.class);

    private static final RequestConfig HTTPCONFIG = RequestConfig.custom()
            .setConnectionRequestTimeout(HTTPCLIENT_REQUEST_TIMEOUT)
            .setConnectTimeout(HTTPCLIENT_CONNECT_TIMEOUT)
            .setSocketTimeout(HTTPCLIENT_SOCKET_TIMEOUT)
            .build();

    public enum HttpDataType{
        // 表单 json_raw xml 文件类型
        NORMAL,FILE
    }

    private static final Map<String, CloseableHttpClient> HTTPCLIENT_CACHE = new ConcurrentHashMap<>();
    private static final String DEFAULT_HTTP_CLIENT_KEY = "Http_ignoreHttps";
    public static final String KEY_STORE_TYPE = "KEY_STORE_TYPE";
    public static final String KEY_STORE_PWD = "KEY_STORE_PWD";
    public static final String KEY_STORE_PATH = "KEY_STORE_PATH";


    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL(){
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSLv3");

            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sc.init(null, new TrustManager[] { trustManager }, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sc;
    }
    /**
     * 请求HTTP接口数据
     * @param url 请求url
     * @param params 请求参数
     * @param clazz
     * @param httpMethods
     * @param dataType
     * @param header
     * @param <T>
     * @return
     */
    public static <T> T request(String url, Object params, Class<T> clazz, HttpMethods httpMethods, HttpDataType dataType, Map<String,Object> header) {
        CloseableHttpResponse response = null;
        try {
            logger.info("HTTP REQ URL:{} PARAMS: {}",url,params);
            // 默认为GET
            if(httpMethods == null || httpMethods.equals(HttpMethods.GET)) {
                StringBuilder builder = new StringBuilder(url);
                if (params != null && !((Map) params).isEmpty()) {
                    builder.append("?");
                    for (Object key : ((Map) params).keySet()) {
                        Object value = ((Map) params).get(key);
                        builder.append(key).append("=").append(value).append("&");
                    }
                    builder.deleteCharAt(builder.length() - 1);
                }
                HttpGet httpGet = new HttpGet(builder.toString());
                httpGet.setConfig(HTTPCONFIG);
                logger.info("HTTP GET REQ URL：{}",url);

                //添加请求头
                if(null != header) {
                    addHeaders(header, httpGet,httpMethods);
                }

                response = getHttpClient(header).execute(httpGet);
            } else if (httpMethods.equals(HttpMethods.POST)) {// POST
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(HTTPCONFIG);
                if (dataType.equals(HttpDataType.FILE)) {// 请求二进制数据
                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                    if(params instanceof Map)
                        for (Object key : ((Map)params).keySet()) {
                            Object value = ((Map)params).get(key);
                            if (value instanceof File) {
                                multipartEntityBuilder.addPart(key.toString(), new FileBody((File) value));
                            } else {
                                multipartEntityBuilder.addPart(key.toString(), new StringBody(value.toString(), ContentType.TEXT_PLAIN));
                            }
                        }
                    httpPost.setEntity(multipartEntityBuilder.build());
                }else{
                    // 创建参数队列
                    List<NameValuePair> formparams = new ArrayList<>();
                    if (params instanceof Map) {
                        for (Object key : ((Map) params).keySet()) {
                            Object value = ((Map) params).get(key);
                            if (value instanceof String) {
                                formparams.add(new BasicNameValuePair(key.toString(), value.toString()));
                            } else {
                                formparams.add(new BasicNameValuePair(key.toString(), JsonUtils.toJson(value)));
                            }
                        }
                        StringEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

                        httpPost.setEntity(uefEntity);
                    }else{
                        if (null != params) {
                            String paramInfo = params.toString();
                            httpPost.setEntity(new StringEntity(paramInfo, Constants.DEFAULT_CHARSET));
                        }
                    }
                }
                //添加请求头
                if(null != header) {
                    addHeaders(header, httpPost,httpMethods);
                    logger.info("OP_HTTP_REQ_HEADER"+header.toString());
                }
                response = getHttpClient(header).execute(httpPost);
            } else{
                logger.warn("请求：{} 暂未实现",httpMethods.getName());
            }
            return buildResp(url, response,clazz, header);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        } finally{
            if(null != response) {
                try {
                    response.close();
                } catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
            }
        }
    }

    /**
     * 构建返回参数
     * @param url
     * @param response
     * @param clazz 返回数据类型 byte[] 或 string
     * @param header
     * @param <T> 返回二进制数据或字符串
     * @return
     */
    private static <T> T buildResp(String url, CloseableHttpResponse response,Class<T> clazz,Map<String,Object> header) throws IOException {
//        StringBuilder result = new StringBuilder();
//        BufferedReader reader = null;
        Object result = null;
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.error("api调用返回状态：{}",response.getStatusLine());
                throw new IllegalStateException("HTTP响应："+response.getStatusLine().getStatusCode());
            }
            dealResponseHeader(response, header);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new IllegalStateException("httpEntity is null");
            }
            if(entity.getContentType() != null){
                String respContentType = entity.getContentType().getValue();
                logger.debug("响应内容数据类型：{}", respContentType);
            }
            /*reader = new BufferedReader(new InputStreamReader(entity.getContent(), Constants.DEFAULT_CHARSET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            logger.debug("OP_HTTP_RESULT:{}, URL:{}",result,url);
            if(clazz.equals(String.class)) {
                return (T)result.toString();
            }*/
            if (clazz.equals(byte[].class)) {// 返回二进制数据
                return (T) EntityUtils.toByteArray(entity);
            } else {// 返回字符串结果
                String res = EntityUtils.toString(entity, Constants.DEFAULT_CHARSET);
                logger.debug("返回数据：{}",res);
                if(clazz.equals(String.class)) {
                    return (T)res.toString();
                }else{
                    return JsonUtils.fromJson(res, clazz);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw e;
        }

    }

    /**
     * 组件请求结果的http header
     * @param response
     * @param header
     */
    private static void dealResponseHeader(CloseableHttpResponse response,  Map<String,Object> header){
        if(null == header){
            return;
        }
        header.clear();
        Header[] headersArr = response.getAllHeaders();
        for(Header tmp: headersArr){
            header.put(tmp.getName(), tmp.getValue());
        }
        logger.debug("OP_HTTP_RESPONSE_HEADS:"+header.toString());
    }
    //绕过验证的方式处理https
    private static final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
//            .register("https",SSLConnectionSocketFactory.getSocketFactory())
            .register("https",new SSLConnectionSocketFactory(createIgnoreVerifySSL()))
            .build();

    /**
     * 根据请求头获取具体的Client
     * 支持https的SSL
     * 如果头部包含KEYSTORE信息，则读取添加生成新的httpClient返回
     * 否则返回默认绕过https验证的httpClient
     * @param header
     * @return
     */
    private static CloseableHttpClient getHttpClient(Map<String,Object> header) {
        CloseableHttpClient httpclient = null;
        // 头部不包含keystore信息，则返回默认绕过https的HTTPCLIENT
        if(null == header || StringUtils.isEmpty(header.get(KEY_STORE_PWD)) || StringUtils.isEmpty(header.get(KEY_STORE_PATH)) || StringUtils.isEmpty(header.get(KEY_STORE_TYPE))) {
            // 为了节省效率 不加锁了
            if((httpclient = HTTPCLIENT_CACHE.get(DEFAULT_HTTP_CLIENT_KEY)) == null){
                logger.debug("创建HttpClient");
                // http连接池配置
                HttpClientConnectionManager httpClientPoolManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry){
                    @Override
                    public void setMaxTotal(int max) {//支持最大连接数
                        super.setMaxTotal(HTTPCLIENT_POOL_MAXTOTAL);
                    }

                    @Override
                    public void setDefaultMaxPerRoute(int max) {//新增路线的最大连接数
                        super.setDefaultMaxPerRoute(HTTPCLIENT_DEFAULT_PRE_ROUTES);
                    }
                };
                httpclient = org.apache.http.impl.client.HttpClientBuilder.create().setConnectionManager(httpClientPoolManager).build();

            }
            //采用绕过验证的方式处理https请求
            return httpclient;
        }else {
            // FIXME 使用keystore的ssl请求
            /*KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("my.keystore"));
            try {
                trustStore.load(instream, "nopassword".toCharArray());
            } finally {
                instream.close();
            }
            return SSLContexts.custom()
                    .loadTrustMaterial(trustStore)
                    .build();*/
        }

        return httpclient;
    }

    /**
     * 根据入参给当前HTTP请求增加请求头
     * @param header
     * @param httpRequest
     * @param httpMethods
     */
    private static void addHeaders(Map<String,Object> header, HttpRequestBase httpRequest, HttpMethods httpMethods) {
        //post 请求修改默认content type
        if(httpMethods!=null&& !header.isEmpty()&&header.get(Constants.CONTENT_TYPE)!=null){
            String contentType=header.get(Constants.CONTENT_TYPE).toString().trim();
            if(httpMethods.equals(HttpMethods.POST)){
                HttpPost httpPost=(HttpPost)httpRequest;
                StringEntity entity=(StringEntity) httpPost.getEntity();
                entity.setContentType(contentType);
                header.remove(Constants.CONTENT_TYPE);
            }
        }
        for (Iterator<Map.Entry<String, Object>> it = header.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> entry = it.next();
            httpRequest.setHeader(entry.getKey(), entry.getValue().toString());
        }
    }


    private HttpClientBuilder(){}
}
