package com.dafy.common.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ip工具类
 *      获取ip的具体信息，如国家，城市、经纬度等
 * Created by liaoxudong
 * Date:2018/1/26
 */

public class IpUtils {

    /**
     * 从文件中读取批量ip，调用接口查询ip信息
     * 再存入文件 先放着，后面具体用的的时候再处理
     */
    public static void catchIpInfo() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<String> ips = Files.readAllLines(Paths.get("logs/req_ip.txt"));
            System.out.println(ips);
            StringBuilder stringBuilder = new StringBuilder();
            ips.forEach(ip -> {
                String ip_str = ip.split("：")[1];
                map.put("inputip", ip_str);
                /*if ("17.200.11.44".equals(ip_str)) {
                    System.out.println("");
                }*/
                String result = HttpUtils.doPost("http://dir.twseo.org/ip-query3.php", map);
                result = result.substring(result.lastIndexOf("IP國別:"));
                System.out.println(result);
                ip += " "+result;
                System.out.println(ip);
                stringBuilder.append(ip + "\n");
            });
//            FileUtils.writeAsString(new File("logs/req_ip_addr.txt"), stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getLocalIp(){
        try {
            Enumeration<?> enumeration= NetworkInterface.getNetworkInterfaces();
            InetAddress ip=null;
            while(enumeration.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) enumeration.nextElement();
                Enumeration<?> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (!ip.isLinkLocalAddress() && !ip.isLoopbackAddress() && ip instanceof Inet4Address){
                        return ip.getHostAddress();
                    }
                }
            }
            throw new IllegalStateException("获取ip地址异常");
        } catch (SocketException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
