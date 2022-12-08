package com.jd.saas.pdacheck.category.bean;

import android.graphics.drawable.Drawable;

import com.jd.saas.pdacheck.R;

import java.util.List;

/**
 * 页面中的类目信息
 */
public class CheckCategoryNode {
    /**
     * 类目id
     */
    private String categoryId;
    /**
     * 类目名称
     */
    private String name;
    /**
     * 父级id
     */
    private String parentId;

    /**
     * 层级
     */
    private int level;

    /**
     * 父级类目
     */
    private CheckCategoryNode parent;
    /**
     * 子级类目
     */
    private List<CheckCategoryNode> children;
    /**
     * 选中状态
     */
    private CheckSelectState selectSate = CheckSelectState.NONE;


    public boolean nextEnable() {
        return children != null && !children.isEmpty();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CheckCategoryNode getParent() {
        return parent;
    }

    public void setParent(CheckCategoryNode parent) {
        this.parent = parent;
    }

    public List<CheckCategoryNode> getChildren() {
        return children;
    }

    public void setChildren(List<CheckCategoryNode> children) {
        this.children = children;
    }

    public CheckSelectState getSelectSate() {
        return selectSate;
    }

    public void setSelectSate(CheckSelectState selectSate) {
        this.selectSate = selectSate;
    }
}
