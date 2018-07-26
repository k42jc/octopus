package com.dafy.octopus.worker.order.constants;

/**
 * 工单操作日志内容
 * Created by liaoxudong
 * Date:2018/6/4
 */

public enum BillOpLogType {
    CREATE(24L,"创建"),
    REVIEW(25L,"审批"),
    DEPUTE(26L,"委托"),
    DEAL(27L,"处理"),
    SAVE(28L,"暂存"),
    CLOSE(29L,"关闭"),
    FREE(30L,"释放"),
    BACK(31L,"回退"),
    PRESS(32L,"催办"),
    ;

    private Long id;
    private String msg;

    BillOpLogType(Long id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public Long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }


    @Override
    public String toString() {
        return this.id + ":" + this.msg;
    }
}
