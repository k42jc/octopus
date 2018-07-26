package com.dafy.common.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoxudong
 * Date:2018/2/6
 */

public class Tree<T> implements Serializable{

    private Long id;
    private Long parentId;
    private List<T> children = new ArrayList<>();

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public void addChild(T tree) {
        children.add(tree);
    }

    public void addChildren(List<T> children) {
        this.children.addAll(children);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
