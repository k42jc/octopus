package com.dafy.octopus.auth.aop;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liaoxudong
 * Date:2018/5/21
 */

public class Spider {

    public static void spider(String filePath){
        String link = "http://www.43sese.com/view/index%d.html";
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for(int i=1630;i<33555;i++) {
            final String url = String.format(link, i);
            executorService.submit(() -> {
                try {
                    URL connect = new URL(url);
                    URLConnection urlConnection = connect.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,Charset.forName("GBK")));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
//                        if (line.contains("<title>") || line.contains("<vedio")) {
                            System.out.println(line);
                            line = "下载线程：【"+Thread.currentThread().getName() + "】"+url + "："+line + "\r\n";
//                            FileUtils.writeByteToFile(line.getBytes("UTF-8"), filePath,true);
//                            continue;
//                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
//                    FileUtils.writeByteToFile((url + ":" + e.getMessage()).getBytes(), filePath,true);
                }
            });
        }

    }

    public static void main(String[] args) {
        spider("E:/zzz.txt");
    }
}
