package com.jd.saas.pdacheck.category;

import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacheck.category.bean.CheckCategoryListReqParam;
import com.jd.saas.pdacheck.category.bean.CheckCategoryNode;
import com.jd.saas.pdacheck.category.bean.CheckSelectState;
import com.jd.saas.pdacheck.category.repo.CheckCategoryTree;
import com.jd.saas.pdacheck.category.repo.CheckCategoryRepository;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseResponse;
import com.jd.saas.pdacommon.net.NetCodeConstant;
import com.jd.saas.pdacommon.net.NetError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 指定类目VM
 *
 * @author gouhetong
 */
public class CheckCategoryViewModel extends NetViewModel {
    private final CheckCategoryRepository repository;
    public final MutableLiveData<CheckCategoryNode> curNode = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRefresh = new MutableLiveData<>(true);
    public final MutableLiveData<LinkedList<String>> msgList = new MutableLiveData<>(new LinkedList<>());
    public final MutableLiveData<Integer> selectCnt = new MutableLiveData<>();
    private CheckCategoryTree tree;
    private List<String> initSelectIds;

    public void initSelectIds(List<String> selectIds) {
        this.initSelectIds = selectIds;
        selectCnt.postValue(selectIds.size());
    }

    /**
     * 取消
     */
    public MutableLiveData<Boolean> cancelEvent = new MutableLiveData<>();
    /**
     * 确定事件
     */
    public MutableLiveData<Collection<String>> ensureEvent = new MutableLiveData<>();

    public CheckCategoryViewModel(CheckCategoryRepository repository) {
        this.repository = repository;
    }


    public void refresh() {
        isRefresh.setValue(true);
        reset();
    }

    public void reset() {
        repository.getCategoryList(new CheckCategoryListReqParam())
                .map(result ->
                        {
                            BaseResponse<CheckCategoryTree> treeBaseResponse = new BaseResponse<>();
                            treeBaseResponse.setCode(result.getCode());
                            treeBaseResponse.setMsg(result.getMsg());
                            if (result.getCode() == NetCodeConstant.OK_200) {
                                List<CheckCategoryNode> data = result.getData();
                                Collection<String> selectIds = initSelectIds;
//                                if (tree != null) {
//                                    selectIds = tree.getSelectIds();
//                                }
                                if (data == null) {
                                    treeBaseResponse.setData(new CheckCategoryTree(new ArrayList<>(), selectIds));
                                } else {
                                    treeBaseResponse.setData(new CheckCategoryTree(data, selectIds));
                                }
                            }
                            return treeBaseResponse;
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new BaseObserver<CheckCategoryTree>() {
                            @Override
                            protected void onSuccess(CheckCategoryTree result) {
                                tree = result;
                                curNode.postValue(tree.getRoot());
                                selectCnt.postValue(tree.getSelectIds().size());
                            }

                            @Override
                            protected void onError(NetError error) {
                                LinkedList<String> value = msgList.getValue();
                                if (value == null) {
                                    value = new LinkedList<>();
                                }
                                value.offer(error.getMsg());
                                msgList.postValue(value);

                            }

                            @Override
                            protected void onComplete(boolean error) {
                                super.onComplete(error);
                                isRefresh.setValue(false);
                            }
                        }
                );
    }

    public void cancel() {
        cancelEvent.postValue(true);
    }

    public void ensure() {
        ensureEvent.postValue(tree.getSelectIds());
    }

    public void selectCheckCategoryNode(CheckCategoryNode bean) {
        if (bean.getSelectSate() == CheckSelectState.NONE) {
            tree.selectNode(bean);
        } else {
            tree.cancelNode(bean);
        }
        selectCnt.postValue(tree.getSelectIds().size());
    }
}
