package com.jd.saas.pdacheck.category;

import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.category.bean.CheckCategoryRespItem;

public class CheckCategoryConvertor {
    public static CheckCategoryNode covert(CheckCategoryRespItem item) {
        CheckCategoryNode node = new CheckCategoryNode();
        node.setCategoryId(item.getCategoryId());
        node.setName(item.getName());
        node.setParentId(item.getParentId());
        return node;
    }
}
