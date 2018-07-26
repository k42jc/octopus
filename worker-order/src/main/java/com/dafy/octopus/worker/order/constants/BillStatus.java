package com.dafy.octopus.worker.order.constants;

/**
 * 工单状态
 *      待审核、审核中、待处理、处理中、已处理、已关闭、已超时、已退回
 * Created by liaoxudong
 * Date:2018/6/4
 */

public enum BillStatus {

    PRE_REVIEW(1L,"待审核"),
    REVIEW_ING(2L,"审核中"),
    PRE_HANDLER(3L,"待处理"),
    HANDLER_ING(4L,"处理中"),
    POST_HANDLER(5L,"已处理"),
    CLOSED(6L,"已关闭"),
    TIMEOUT(7L,"已超时"),
    BACKED(8L,"已退回");

    private Long id;
    private String desc;

    BillStatus(Long id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }


    @Override
    public String toString() {
        return this.id+":"+this.desc;
    }
}
