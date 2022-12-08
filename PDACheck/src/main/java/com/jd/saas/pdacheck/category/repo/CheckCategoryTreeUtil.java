package com.jd.saas.pdacheck.category.repo;

import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.category.bean.CheckSelectState;
import com.jingdong.jdma.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CheckCategoryTreeUtil {
    public static final int mockLevel = 3;
    public static final int mockCntPerLevel = 50;
    /**
     * 根节点ID
     */
    private static final String rootId = "0";
    /**
     * 本地创建的根节点的父节点ID
     */
    private static final String rootLocalParentId = "-1";

    /**
     * 构建类目树
     * 将返回的扁平结构转换为树形结构
     * <p>
     * 为bean中的parent和children赋值，建立关联关系
     * 原始数组会遍历3遍
     *
     * @param originList 原始数据列表
     * @return 类目树的根结点
     */
    public static CheckCategoryNode buildTree(List<CheckCategoryNode> originList) {
        HashMap<String, List<CheckCategoryNode>> cache = new HashMap<>();
        //层级深度
        int deep = 0;
        //根结点
        CheckCategoryNode root = null;
        //第一次遍历 建立缓存
        for (CheckCategoryNode categoryNode : originList) {
            //统计最终类目树的深度
            if (deep < categoryNode.getLevel()) {
                deep = categoryNode.getLevel();
            }
            if (rootId.equals(categoryNode.getCategoryId()) && rootLocalParentId.equals(categoryNode.getParentId())) {
                root = categoryNode;
            }
            //建立cache缓存 缓存全部 父id与子node的关系
            String parentId = categoryNode.getParentId();
            if (cache.containsKey(parentId)) {
                List<CheckCategoryNode> childList = cache.get(parentId);
                if (childList != null) {
                    childList.add(categoryNode);
                }
            } else {
                List<CheckCategoryNode> childList = new ArrayList<>();
                childList.add(categoryNode);
                cache.put(parentId, childList);
            }
        }
        //第二次遍历 从缓存中取出子节点列表 并关联父子层级的关系
        for (CheckCategoryNode categoryNode : originList) {
            List<CheckCategoryNode> children = cache.get(categoryNode.getCategoryId());
            if (children != null) {
                for (CheckCategoryNode child : children) {
                    child.setParent(categoryNode);
                }
            }
            categoryNode.setChildren(children);
        }
        //三次遍历 移除异常的节点
        for (CheckCategoryNode categoryNode : originList) {
            if (categoryNode.getLevel() < deep) {
                List<CheckCategoryNode> children = categoryNode.getChildren();
                if (children == null || children.isEmpty()) {
                    //非叶节点 子节点为空 不是一个可用的节点
                    removeNode(categoryNode, cache.get(rootId));
                }
            }
        }
        if (root == null) {
            root = createRoot(cache.get(rootId));
        }
        //根节点修正 默认值不为空
        if (root.getChildren() == null) {
            root.setChildren(new ArrayList<>());
        }
        return root;

    }

    /**
     * 创建一个level为0的根节点
     *
     * @param children 根节点的子节点 第一级的类目列表
     * @return 类目树的根节点
     */
    private static CheckCategoryNode createRoot(List<CheckCategoryNode> children) {
        CheckCategoryNode root = new CheckCategoryNode();
        root.setCategoryId(rootId);
        root.setName("类目根");
        root.setLevel(0);
        root.setParentId(rootLocalParentId);
        if (children != null) {
            //关联父子层级关系
            for (CheckCategoryNode child : children) {
                child.setParent(root);
            }
            root.setChildren(children);
        } else {
            root.setChildren(new ArrayList<>());
        }
        return root;
    }

    /**
     * 移除节点,当移除后父节点为空时继续移除父节点
     *
     * @param categoryNode 移除的节点
     * @param topNode      顶级节点
     */
    private static void removeNode(CheckCategoryNode categoryNode, List<CheckCategoryNode> topNode) {
        if (categoryNode == null) {
            return;
        }
        CheckCategoryNode parent = categoryNode.getParent();
        if (parent == null) {
            topNode.remove(categoryNode);
            return;
        }
        LinkedList<CheckCategoryNode> pList = new LinkedList<>();
        pList.push(parent);
        while (!pList.isEmpty()) {
            CheckCategoryNode pop = pList.pop();
            List<CheckCategoryNode> brothers = pop.getChildren();
            if (brothers != null) {
                brothers.remove(categoryNode);
                if (brothers.isEmpty()) {
                    CheckCategoryNode popParent = pop.getParent();
                    if (popParent != null) {
                        pList.push(popParent);
                    } else {
                        topNode.remove(pop);
                    }
                }
            }
        }
    }

    public interface Consumer {
        /**
         * 遍历到某个节点时
         *
         * @param node   节点
         * @param isLeaf 是否是末级
         */
        void accept(CheckCategoryNode node, boolean isLeaf);
    }

    /**
     * 遍历一个以输入的节点为根结点的树
     *
     * @param root     任意节点
     * @param consumer 遍历到某节点时到回调
     */
    public static void traversal(CheckCategoryNode root, Consumer consumer) {
        LinkedList<CheckCategoryNode> tempStack = new LinkedList<>();
        LinkedList<CheckCategoryNode> tempStack2 = new LinkedList<>();
        tempStack.add(root);
        while (!tempStack.isEmpty()) {
            CheckCategoryNode node = tempStack.pop();
            List<CheckCategoryNode> children = node.getChildren();
            if (children != null && !children.isEmpty()) {
                tempStack.addAll(children);
                tempStack2.push(node);
            } else {
                //遍历到的节点
                consumer.accept(node, true);
            }
        }
        while (!tempStack2.isEmpty()) {
            CheckCategoryNode node = tempStack2.pop();
            //遍历到的节点
            consumer.accept(node, false);
        }
    }

    /**
     * 初始化选中状态
     * <p>
     * 遍历一遍类目树 设置全部节点的选中状态
     *
     * @param treeNode  根结点
     * @param selectIds 选中的类目ID 必须为末级节点
     */
    public static void initSelectState(CheckCategoryNode treeNode, Collection<String> selectIds) {
        LogUtil.d("categoryTree", "批量初始化选中状态");
        traversal(treeNode, (node, isLeaf) -> {
            if (isLeaf) {
                if (selectIds.contains(node.getCategoryId())) {
                    LogUtil.d("categoryTree", "选中末级：" + node.getCategoryId());
                    node.setSelectSate(CheckSelectState.ALL);
                } else {
                    node.setSelectSate(CheckSelectState.NONE);
                }
            } else {
                updateNodeByChildren(node);
                if (node.getSelectSate() != CheckSelectState.NONE) {
                    LogUtil.d("categoryTree", "非末级：" + node.getCategoryId() + " 状态" + node.getSelectSate());
                }
            }
        });
    }

    /**
     * 选中一个节点
     *
     * @param categoryNode 某个类目节点
     * @param selectIds    选中的类目ID 必须为末级节点
     */
    public static void selectNode(CheckCategoryNode categoryNode, Collection<String> selectIds) {
        //更新自己全部子节点的选中状态
        traversal(categoryNode, (node, isLeaf) -> {
            node.setSelectSate(CheckSelectState.ALL);
            if (isLeaf) {
                selectIds.add(node.getCategoryId());
            }
        });
        updateParents(categoryNode);
    }

    /**
     * 更新自己全部父级节点的选中状态
     *
     * @param node 当前节点
     */
    private static void updateParents(CheckCategoryNode node) {
        CheckCategoryNode p = node.getParent();
        while (p != null) {
            updateNodeByChildren(p);
            p = p.getParent();
        }
    }

    /**
     * 通过子级的状态更新自己的选中状态
     * 仅对非末级节点有效 末级节点的状态由外部决定
     *
     * @param node 任意非末级节点
     */
    private static void updateNodeByChildren(CheckCategoryNode node) {
        List<CheckCategoryNode> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            int selectAllCnt = 0;
            for (CheckCategoryNode child : children) {
                if (child.getSelectSate() == CheckSelectState.PART) {
                    selectAllCnt = -1;
                    break;
                }
                if (child.getSelectSate() == CheckSelectState.ALL) {
                    selectAllCnt++;
                }
            }
            if (selectAllCnt == 0) {
                node.setSelectSate(CheckSelectState.NONE);
            } else if (selectAllCnt == children.size()) {
                node.setSelectSate(CheckSelectState.ALL);
            } else {
                node.setSelectSate(CheckSelectState.PART);
            }
        }
    }

    /**
     * 取消一个节点
     *
     * @param categoryNode 某个类目节点
     * @param selectIds    选中的类目ID 必须为末级节点
     */
    public static void cancelNode(CheckCategoryNode categoryNode, Collection<String> selectIds) {
        //更新自己全部子节点的选中状态
        traversal(categoryNode, (node, isLeaf) -> {
            node.setSelectSate(CheckSelectState.NONE);
            if (isLeaf) {
                selectIds.remove(node.getCategoryId());
            }
        });
        updateParents(categoryNode);
    }

    /**
     * 生成测试数据
     *
     * @param level       类目层级
     * @param cntPerLevel 1~99 每级类目的子类目数量
     * @return 测试数据
     */
    public static List<CheckCategoryNode> generateResp(int level, int cntPerLevel) {
        List<CheckCategoryNode> originList = new ArrayList<>();
        CheckCategoryNode root = new CheckCategoryNode();
        root.setCategoryId(rootId);
        root.setName("类目");
        root.setParentId(rootLocalParentId);
        originList.add(root);
        List<CheckCategoryNode> nextLevelList = originList;
        while (level-- > 0) {
            nextLevelList = getNextLevel(nextLevelList, cntPerLevel);
            originList.addAll(nextLevelList);
        }
        originList.remove(0);
        return originList;
    }

    private static List<CheckCategoryNode> getNextLevel(List<CheckCategoryNode> levelList, int cntPerLevel) {
        List<CheckCategoryNode> temp = new ArrayList<>();
        //添加下一级类目
        for (CheckCategoryNode categoryNode : levelList) {
            for (int i = 0; i < cntPerLevel; i++) {
                String parentId = categoryNode.getCategoryId();
                long longId = Long.parseLong(parentId);
                long childId = rootId.equals(parentId) ? (100 + i) : (longId * 100 + i);
                CheckCategoryNode e = new CheckCategoryNode();
                e.setCategoryId(childId + "");
                e.setName(categoryNode.getName() + "-" + i);
                e.setParentId(parentId);
                temp.add(e);
            }
        }
        return temp;
    }

    /**
     * 生成测试数据中默认选中的类目 只能选中第一个次末级类目中的末级
     *
     * @param level 类目层级
     * @param cnt   选中数量
     * @return 选中的类目ID
     */
    public static ArrayList<Integer> getSelectLeaf(int level, int cnt) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            result.add((int) (Math.pow(10, level * 2) + i));
        }
        return result;
    }

    /**
     * 获取某类目的层级索引
     *
     * @param node 类目节点 可以是任何层级的
     * @return 层级路径 [n级，n-1级...1级]
     */
    public static List<CheckCategoryNode> getParents(CheckCategoryNode node) {
        ArrayList<CheckCategoryNode> result = new ArrayList<>();
        CheckCategoryNode p = node;
        while (p != null) {
            result.add(p);
            p = p.getParent();
        }
        return result;
    }
}
