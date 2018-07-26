package com.dafy.common.util;


import com.dafy.common.po.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建树形结构
 *
 * 限制树形数据结构(子类可继承Tree)
 * Created by liaoxudong
 * Date:2018/2/6
 */

public class TreeUtils {

    public static <T extends Tree> List<T> buildTree(List<T> nodes) {
        List<T> list = new ArrayList<>();
        for(T node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (node.getParentId() == null || node.getParentId() == 0) {
                node.addChildren(buildTreeChilds(node,nodes));
                list.add(node);
            }
        }
        return list;
    }

    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private static <T extends Tree> List<T> buildTreeChilds(T node,List<T> nodes){
        List<T> list = new ArrayList<>();
        List<T> childNodes = getChilds(node,nodes);
        for(T childNode : childNodes){
            //System.out.println("childNode"+childNode.getMenuName());
            List<T> childs = buildTreeChilds(childNode,nodes);
            childNode.addChildren(childs);
            list.add(childNode);
        }
        return list;
    }

    /**
     * getChilds
     * 描述:  获取子节点
     * @param parentNode
     * @return List<Resource>
     * @exception
     * @since  1.0.0
     */
    private static <T extends Tree> List<T> getChilds(T parentNode,List<T> nodes) {
        List<T> childNodes = new ArrayList<>();
        for(T node : nodes){
            //System.out.println(node.getParentId()+"-------"+parentNode.getId());
            if (node.getParentId() == parentNode.getId()) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }


    /**
     * 标准树形数据结构
     *//*
    public class Tree{

        private Long id;
        private Long parentId;
        private List<Tree> children = new ArrayList<>();

        public List<Tree> getChildren() {
            return children;
        }

        public void setChildren(List<Tree> children) {
            this.children = children;
        }

        public void addChild(Tree tree) {
            children.add(tree);
        }

        public void addChildren(List<Tree> children) {
            children.addAll(children);
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
    }*/
}

