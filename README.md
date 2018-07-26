# 八爪鱼客服系统后台

使用Spring boot单应用开发模式，后台服务模块化。引入Shiro+Redis实现集中式认证中心，增加模块只需要关注业务开发即可。

开发过程前后端分离，前端使用Vue实现，就不再赘述，代码也不提供了。

本后台有提供简单页面进行认证逻辑，目的是为了开发过程方便给前端开发人员提供Swagger-ui的API接口文档。

## 项目结构

`cache、common、dal`封装缓存、公用工具类以及数据访问层代码，专门用于打成jar包为模块提供服务的。

`web-core`为完成集中式认证的公共代码，实现业务模块需要引入此模块

`octopus`实现认证、授权、组织架构、菜单等基础服务。

`mobile-rental`为特殊的业务服务模块，主要完成第三方接口对接，可以忽略

`worker-order`为工单系统，本地实现的一个完整流程的工作流

## 运行指南
>需要可用mysql、redis，需要nginx负责整合客户端请求

* 将项目clone项目到本地
* 将`octopus、mobile-rental、worker-order`下的数据结构与数据sql初始化到对应的分库，具体库查看对应模块下具体环境的的yml配置
* 在根目录执行`mvn clean compile install`，安装公共包到maven仓库
* 进入`octopus`，执行maven命令：`mvn spring-boot:run`运行认证中心(或先`mvn clean compile package -P local`使用具体profile(此处是local)将项目打包成jar，然后进入目录使用`java -jar xx.jar`运行)
* 进入`worker-order`项目，按上面步骤运行工单系统
* 配置nginx，参见[nginx配置](#nginx)，然后启动nginx服务
* 访问`http://localhost/auth-center/`，会显示不同模块的对外api接口，见[预览效果](预览效果)
* 单点登录，账号密码：test/52387d02f7f7e3ef1977d710fc05e007


## nginx配置

linux下默认在`/etc/nginx/nginx.conf`，windows下载nginx.exe同级目录的`conf/nginx.conf`
```
http {
    include       mime.types;
    default_type  application/octet-stream;
    # 解决文件上传超出大小
    client_max_body_size 20m;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;
    # 允许跨域处理
    map $http_origin $corsHost {
        default 0;
        "~http://10.9.28.43:8080" http://10.9.28.43:8080;
    }
    server {
        listen       80;
        server_name  localhost;
        index index.html;

        #八爪鱼客服管理系统
        # 认证中心
        location ~ ^/(auth-center)/ {
            add_header Access-Control-Allow-Origin $corsHost;
            add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS, PUT, DELETE';
            add_header Access-Control-Allow-Credentials true;
            add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,pagenum,pagesize';

            if ($request_method = 'OPTIONS') {
               return 204;
            }
            proxy_pass http://127.0.0.1:8888;
            index /;
            proxy_set_header Host $host;
        }
        # 手机租赁系统
        location ~ ^/(mobile-rental)/ {
            add_header Access-Control-Allow-Origin $corsHost;
            add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS, PUT, DELETE';
            add_header Access-Control-Allow-Credentials true;
            add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,pagenum,pagesize';

            if ($request_method = 'OPTIONS') {
               return 204;
            }
            proxy_pass http://127.0.0.1:8889;
            index /;
            proxy_set_header Host $host;
        }
        # 工单系统
        location ~ ^/(worker-order)/ {
            add_header Access-Control-Allow-Origin $corsHost;
            add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS, PUT, DELETE';
            add_header Access-Control-Allow-Credentials true;
            add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,pagenum,pagesize';

            if ($request_method = 'OPTIONS') {
               return 204;
            }
            proxy_pass http://127.0.0.1:8891;
            index /;
            proxy_set_header Host $host;
        }
        # 静态文件
        location ^~ /asserts/ {
            root   D:/workspace/cssys-back;
        }
    } 
}
```

## 预览效果
![后台API总览](https://github.com/k42jc/octopus/raw/master/%E5%90%8E%E5%8F%B0API%E6%96%87%E6%A1%A3.png)
![点击具体API如果未登录要求登录](https://github.com/k42jc/octopus/raw/master/%E9%9C%80%E8%A6%81%E7%99%BB%E5%BD%95.png)
![登录成功自动跳转到原请求路径](https://github.com/k42jc/octopus/raw/master/%E5%85%B7%E4%BD%93%E6%A8%A1%E5%9D%97%E7%9A%84api%E6%8E%A5%E5%8F%A3.png)

再访问其他模块api已经检测到登录，不需要再进行登录，直接可以访问
