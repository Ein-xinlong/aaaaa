package com.jd.saas.pdacheck.flow;

import com.jd.saas.pdacheck.net.CheckTaskNodeEnum;

/**
 * 其它页面需要切换盘点流程页面，使用事件总线需要传递的Data
 *
 * @author majiheng
 */
public class CheckFlowSwitchEventBusBean {

    // 需要切换的tab id
    private int tab;
    // 当前节点
    private CheckTaskNodeEnum currentNode;
    // 目标节点
    private CheckTaskNodeEnum targetNode;

    public CheckTaskNodeEnum getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(CheckTaskNodeEnum currentNode) {
        this.currentNode = currentNode;
    }

    public CheckTaskNodeEnum getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(CheckTaskNodeEnum targetNode) {
        this.targetNode = targetNode;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }
}
