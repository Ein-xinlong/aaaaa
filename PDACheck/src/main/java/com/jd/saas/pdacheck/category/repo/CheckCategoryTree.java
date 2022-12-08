package com.jd.saas.pdacheck.category.repo;

import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jingdong.jdma.common.utils.LogUtil;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * 类目树操作的封装
 */
public class CheckCategoryTree {
    private final CheckCategoryNode root;
    private final Collection<String> selectIds;

    public CheckCategoryNode getRoot() {
        return root;
    }

    public CheckCategoryTree(List<CheckCategoryNode> originList, Collection<String> selectIds) {
        long startTime = System.currentTimeMillis();
        root = CheckCategoryTreeUtil.buildTree(originList);
        long end = System.currentTimeMillis();
        LogUtil.d("categoryTree", "buildTree耗时" + (end - startTime));
//        CategoryTreeUtil.traversal(root, (node, isLeaf) -> LogUtil.d("categoryTree", node.getName() + " id:" + node.getCategoryId()));
        this.selectIds = new TreeSet<>(selectIds);
        CheckCategoryTreeUtil.initSelectState(root, selectIds);
    }

    /**
     * 获取某类目的子类目
     *
     * @param node 类目节点 可以是任何层级的
     * @return 子类目列表 当前为末级类目时返回null
     */
    public List<CheckCategoryNode> getChildList(CheckCategoryNode node) {
        return node.getChildren();
    }


    /**
     * 选中一个节点
     *
     * @param categoryNode 某个类目节点
     */
    public void selectNode(CheckCategoryNode categoryNode) {
        CheckCategoryTreeUtil.selectNode(categoryNode, selectIds);
    }

    /**
     * 取消一个节点
     *
     * @param categoryNode 某个类目节点
     */
    public void cancelNode(CheckCategoryNode categoryNode) {
        CheckCategoryTreeUtil.cancelNode(categoryNode, selectIds);
    }

    /**
     * 获取已选中的末级类目信息
     *
     * @return 已选中的类目id 不重复的
     */
    public Collection<String> getSelectIds() {
        return selectIds;
    }
}
