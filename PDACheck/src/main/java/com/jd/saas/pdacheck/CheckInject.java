package com.jd.saas.pdacheck;

import com.jd.saas.pdacheck.category.repo.CheckCategoryRemoteRepository;
import com.jd.saas.pdacheck.category.repo.CheckCategoryRepository;
import com.jd.saas.pdacheck.flow.step1.repo.CheckCouRemoteRepository;
import com.jd.saas.pdacheck.flow.step1.repo.CheckCouRepository;
import com.jd.saas.pdacheck.flow.step2.repo.CheckMissedRemoteRepository;
import com.jd.saas.pdacheck.flow.step2.repo.CheckMissedRepository;
import com.jd.saas.pdacheck.flow.step3.repo.CheckReviewRemoteRepository;
import com.jd.saas.pdacheck.flow.step3.repo.CheckReviewRepository;

/**
 * 依赖注入方法
 * 用来注入指定的对象
 *
 * @author gouhetong
 */
public class CheckInject {
    public static CheckViewModelFactory injectViewModelFactory() {
        return new CheckViewModelFactory();
    }

    public static CheckMissedRepository injectMissedCheckRepository() {
        return new CheckMissedRemoteRepository();
    }

    public static CheckCouRepository injectPreOrderListRepository() {
        return new CheckCouRemoteRepository();
    }

    public static CheckCategoryRepository injectCheckCategoryRepository() {
        return new CheckCategoryRemoteRepository();
    }

    public static CheckReviewRepository injectCheckReviewRepository() {
        return new CheckReviewRemoteRepository();
    }
}
