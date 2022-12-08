package com.jd.saas.pdacommon.router;

import android.app.Application;

import com.jingdong.amon.router.JDRouter;

/**
 * JDRouter管理类
 *
 * @author majiheng
 */
public class JDRouterManager {

    public static void init(Application application) {
        JDRouter.init(application);
    }
}
