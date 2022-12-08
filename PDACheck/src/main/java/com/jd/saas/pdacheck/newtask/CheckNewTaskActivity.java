package com.jd.saas.pdacheck.newtask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jingdong.amon.router.annotation.JDRouteUri;

import java.util.ArrayList;

/**
 * 新建盘点任务activity
 *
 * @author ext.anxinlong
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = CheckRouterPath.CHECK_MAIN, path = CheckRouterPath.CHECK_NEW_TASK_ACTIVITY_PATH)
public class CheckNewTaskActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.check_new_task_title);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return CheckNewTaskFragment.newInstance();
    }
}
