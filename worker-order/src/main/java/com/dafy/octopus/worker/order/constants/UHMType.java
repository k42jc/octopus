package com.dafy.octopus.worker.order.constants;

/**
 * 工单处理人更改类型
 * Created by liaoxudong
 * Date:2018/6/7
 */

public enum UHMType {

    FREE_REVIEW(0,"审核释放"),FREE_HANDLER(1,"处理释放"),DEPUTE(2,"委托"),BACK(3,"回退"),CLOSE(4,"关单"),PRESS(5,"催办");//释放、委托、回退
    private int type;
    private String desc;

    UHMType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static UHMType parse(int type) {
        for (UHMType uht : values()) {
            if (uht.type == type) {
                return uht;
            }
        }
        return FREE_REVIEW;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
