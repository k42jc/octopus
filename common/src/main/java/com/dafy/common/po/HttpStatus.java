package com.dafy.common.po;

/**
 * HTTP状态码
 * Created by liaoxudong
 * Date:2017/12/22
 */

public enum HttpStatus {
    //1xx消息
    //代表请求已被接收，需要继续处理。这类响应是临时响应，只包含状态行和某些可选的响应头信息，并以空行结束
    //由于HTTP/1.0协议中没有定义任何1xx状态码，所以正式情况服务器禁止向此类客户端发送1xx响应
    CONTINUE(100, "Continue", "已接收请求头，客户端应继续发送请求主体"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols","切换协议，如协议升级到Websocket"),
    PROCESSING(102, "Processing","已接收请求"),
    CHECKPOINT(103, "Checkpoint","预加载"),
    //2xx成功
    //代表请求已成功被服务器接收、理解并接受
    OK(200, "OK","请求成功"),
    CREATED(201, "Created","接受请求并已创建资源，且资源URI随Location头信息返回"),
    ACCEPTED(202, "Accepted","服务器已接收请求，但尚未处理。用于异步任务"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information","当前服务器是一个转换代理服务器，如网络加速器，返回的信息并不是当前服务器的有效确定集合"),
    NO_CONTENT(204, "No Content","无返回内容，用于跨域处理返回"),
    RESET_CONTENT(205, "Reset Content","无返回内容，且客户端重置文档视图"),
    PARTIAL_CONTENT(206, "Partial Content","已经处理部分GET请求，用于下载过程的断点续传"),
    MULTI_STATUS(207, "Multi-Status","代表之后的消息体将是一个XML消息，包含一系列独立响应代码"),
    ALREADY_REPORTED(208, "Already Reported","DAV绑定的成员已经在(多状态)响应之前的部分被列举，且未被再次包含"),
    IM_USED(226, "IM Used","服务器已经满足了对资源的请求"),
    //3xx重定向
    //代表需要客户端采取进一步的操作才能完成请求 重定向操作只能是GET或HEAD请求,返回Location头信息指定首选地址
    MULTIPLE_CHOICES(300, "Multiple Choices","存在多种可选地址"),
    MOVED_PERMANENTLY(301, "Moved Permanently","被请求资源已永久移动到新位置"),
    FOUND(302, "Found","临时性重定向"),
    SEE_OTHER(303, "See Other","本次请求需要重定向后的地址返回最终响应"),
    NOT_MODIFIED(304, "Not Modified","资源未被修改，不用再次请求，表面客户端存在缓存"),
    USE_PROXY(305,"Use Proxy","请求的资源必须通过代理才能访问，代理所在的URI信息将在Location域中指出"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect","临时重定向，与302不同的是，当重新发出原始请求时，不允许更改请求方法"),
    PERMANENT_REDIRECT(308, "Permanent Redirect","永久重定向，与301不同的是，当重新发出原始请求时，不允许更改请求方法"),
    //4xx客户端错误
    //代表客户端看起来可能发生了错误，妨碍了服务器的处理
    BAD_REQUEST(400, "Bad Request","错误请求，服务器不能或不会处理"),
    UNAUTHORIZED(401, "Unauthorized","未认证，服务器返回本状态码并搭配认证域，如：Header:'WWW-Authenticate'-'Basic realm=\"Secure Area\"'"),
    PAYMENT_REQUIRED(402, "Payment Required","本来是用作某种形式的支付的一部分，但是几乎没有被使用。Google API会在开发者超出每日调用上线后返回此状态码"),
    FORBIDDEN(403, "Forbidden","拒绝执行"),
    NOT_FOUND(404, "Not Found","找不到资源"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed","不允许的请求方法"),
    NOT_ACCEPTABLE(406, "Not Acceptable","Content-Type指定的媒体格式不匹配"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required","需要在代理服务器进行身份验证"),
    REQUEST_TIMEOUT(408, "Request Timeout","请求超时"),
    CONFLICT(409, "Conflict","请求存在冲突导致无法处理"),
    GONE(410, "Gone","资源不再可用"),
    LENGTH_REQUIRED(411, "Length Required","请求需要指定Content-Length头"),
    PRECONDITION_FAILED(412, "Precondition Failed","未满足请求先决条件"),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large","请求实体超出服务器有效载荷"),
    URI_TOO_LONG(414, "URI Too Long","请求URI过长，通常是POST请求被意外的转为GET导致拼接太长"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type","不支持的媒体格式"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable","客户端请求超出了服务器限制"),
    EXPECTATION_FAILED(417, "Expectation Failed","Expect指定的预期内容服务器无法满足"),
    I_AM_A_TEAPOT(418, "I'm a teapot","用于彩蛋"),
    ENHANCE_YOUR_CAIM(420, "Enhance Your Caim","Twitter Search与Trends API在客户端被限速的情况下返回"),
    MISDIRECTED_REQUEST(421, "Misdirected Request","服务器无法产生响应，如连接重用"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity","语义错误，无法响应"),
    LOCKED(423, "Locked","资源被锁定"),
    FAILED_DEPENDENCY(424, "Failed Dependency","由于之前的某个请求产生错误，导致当前请求失败"),
    UNODERED_CELLECTION(425, "Unodered Cellection","部分定义中存在此状态码"),
    UPGRADE_REQUIRED(426, "Upgrade Required","客户端应切换到TLS/1.0，并在HTTP/1.1 Upgrade header中给出"),
    PRECONDITION_REQUIRED(428, "Precondition Required","原服务器要求当前请求需要满足一定条件"),
    TOO_MANY_REQUESTS(429, "Too Many Requests","用户在给定时间内发送了太多请求，用于网络限速"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large","某个头部字段过长"),
    NO_RESPONSE(444, "No Response","Nginx上HTTP服务器扩展，服务器不返回任何信息并关闭连接"),
    BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS(450, "Blocked by Windows Parental Controls","由Windows家庭控制HTTP阻止的450状态码示例，用于信息和测试"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons","因法律要求而被拒绝"),
    REQUEST_HEADER_TOO_LARGE(494, "Request Header Too Large","431之前Nginx上使用的扩展状态码"),
    //5xx服务器错误
    //表示服务器在处理请求过程中有错误或异常发生，无法完成明显有效的请求
    INTERNAL_SERVER_ERROR(500, "Internal Server Error","服务器错误，没有给出具体错误信息"),
    NOT_IMPLEMENTED(501, "Not Implemented","服务器不支持当前请求"),
    BAD_GATEWAY(502, "Bad Gateway","网关无法从上游服务器接收到响应"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable","服务暂时不可用，暂时状态"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout","网关从上游服务器获取响应超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported","服务器不支持当前请求的HTTP版本"),
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates","服务器存在内部配置错误"),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage","服务器无法存储完成请求所必须的内容，暂时状态"),
    LOOP_DETECTED(508, "Loop Detected","服务器处理请求时陷入死循环"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded",""),
    NOT_EXTENDED(510, "Not Extended","不满足请求策略"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required","客户端需要进行身份验证才能获得网络访问权限，旨在限制用户群访问特定网络，如连接WIFI任店时的强制网络门户");

    HttpStatus(int code, String desc, String desc_cn) {
        this.code = code;
        this.desc = desc;
        this.desc_cn = desc_cn;
    }

    private final int code;
    private final String desc;
    private final String desc_cn;

    public int code(){
        return this.code;
    }

    public String desc(){
        return this.desc;
    }

    public String desc_cn(){
        return this.desc_cn;
    }

    /**
     * 是否信息类状态码
     * @return
     */
    public boolean is1xxInformational(){
        return Series.INFORMATIONAL.equals(series());
    }


    /**
     * 是否成功类型状态码
     * @return
     */
    public boolean is2xxSuccessful(){
        return Series.SUCCESSFUL.equals(series());
    }

    /**
     * 是否重定向类型状态码
     * @return
     */
    public boolean is3xxRedirection(){
        return Series.REDIRECTION.equals(series());
    }

    /**
     * 是否客户端类型状态
     * @return
     */
    public boolean is4xxClientError(){
        return Series.CLIENT_ERROR.equals(series());
    }

    /**
     * 是否服务器类型状态码
     * @return
     */
    public boolean is5xxServerError(){
        return Series.SERVER_ERROR.equals(series());
    }

    public static HttpStatus valueOf(int code) {
        HttpStatus[] values = values();
        for (HttpStatus value : values) {
            if (code == value.code) {
                return value;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

    public Series series(){
        return Series.valueOf(this);
    }


    public static enum Series{
        INFORMATIONAL(1),
        SUCCESSFUL(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

        public static HttpStatus.Series valueOf(int code) {
            HttpStatus.Series[] values = values();
            for (HttpStatus.Series value : values) {
                if (code == value.value) {
                    return value;
                }
            }
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }

        public static HttpStatus.Series valueOf(HttpStatus status) {
            return valueOf(status.code);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(this.code);
    }


}
