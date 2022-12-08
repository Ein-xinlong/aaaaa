package com.jd.saas.pdacheck;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 盘点路由地址
 *
 * @author majiheng
 */
public class CheckRouterPath {

    public static final String CHECK_MAIN = "CheckModule";
    public static final String CHECK_ACTIVITY_PATH = "/CheckListPage";
    public static final String CHECK_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + CHECK_ACTIVITY_PATH;

    public static final String PERFORM_ACTIVITY_PATH = "/CheckPerformPage";
    public static final String PERFORM_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + PERFORM_ACTIVITY_PATH;

    public static final String RECORD_ACTIVITY_PATH = "/CheckRecordPage";
    public static final String RECORD_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + RECORD_ACTIVITY_PATH;

    public static final String CHECK_FLOW_ACTIVITY_PATH = "/CheckFlowPage";
    public static final String CHECK_FLOW_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + CHECK_FLOW_ACTIVITY_PATH;

    public static final String CHECK_NEW_TASK_ACTIVITY_PATH = "/CheckNewTaskPage";
    public static final String CHECK_NEW_TASK_PATH_MAIN = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + CHECK_NEW_TASK_ACTIVITY_PATH;

    /** 预盘点列表 activity path */
    public static final String CHECK_PRE_ORDER_LIST_ACTIVITY_PATH = "/CheckPreOrderList";
    /** 预盘点列表 router path */
    public static final String CHECK_PATH_PRE_ORDER_LIST = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + CHECK_PRE_ORDER_LIST_ACTIVITY_PATH;

    /** 指定分类页 activity path */
    public static final String CHECK_CATEGORY_ACTIVITY_PATH = "/CheckCategory";
    /** 指定分类页 router path */
    public static final String CHECK_PATH_CATEGORY = SCHAME + "://" + NATIVE_TYPE + CHECK_MAIN + CHECK_CATEGORY_ACTIVITY_PATH;
    /** 指定分类页 参数 */
    public static final String CHECK_CATEGORY_PARAM_SELECT = "com.jd.saas.pdacheck.category.PARAM_SELECT_IDS";
}
