package com.jd.saas.pdacheck.category.bean;

public class CheckCategoryRespItem {
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
}
