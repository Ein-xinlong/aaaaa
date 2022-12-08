package com.jd.saas.padinventory.stock;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/16
 */
public class InventoryStatusBean {

    // 条目名称
    private String name;
    // 条目值
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
