package com.jd.saas.pdadelivery.detail;

import android.view.View;

public interface DetailFragmentContainer {
    void showFinishBtn(View.OnClickListener onFinishBtnClickListener);

    void hideFinishBtn();

    void showPrintBtn(View.OnClickListener onFinishBtnClickListener);

}



