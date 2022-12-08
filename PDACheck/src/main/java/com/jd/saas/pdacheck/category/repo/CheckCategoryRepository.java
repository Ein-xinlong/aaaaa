package com.jd.saas.pdacheck.category.repo;

import com.jd.saas.pdacheck.category.bean.CheckCategoryListReqParam;
import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.net.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * 类目的数据仓库 包括选中状态的维护
 */
public abstract class CheckCategoryRepository extends BaseRepository {


    /**
     * 分类列表
     */
    public abstract Observable<BaseResponse<List<CheckCategoryNode>>> getCategoryList(CheckCategoryListReqParam param);


}

