package com.jd.saas.pdadelivery.util;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class ScannerUtil {
    public interface SearchListener {
        void invoke(String str);
    }

    /**
     * 监听软键盘的回车或者搜索事件，注意只支持软键盘，注册此监听方法，不会启动扫码枪矫正模式
     */
    public static void registerEditorListener(EditText editText, SearchListener searchListener) {
        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_SEARCH
                    || (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()
                    && KeyEvent.ACTION_DOWN == keyEvent.getAction())) {
                if (searchListener != null) {
                    searchListener.invoke(editText.getText().toString());
                }
                return true;
            }
            return false;
        });
    }

    public static void registerScannerEditText(EditText editText, SearchListener searchListener) {
        StringBuilder builder = new StringBuilder();
        editText.setOnKeyListener((v, keyCode, event) -> {
            boolean result = false;
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                    builder.append(keyCode - KeyEvent.KEYCODE_0);
                    result = true;
                } else if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
                    builder.append(event.getDisplayLabel());
                    result = true;
                } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String data = builder.toString();
                    editText.setText(data);
                    searchListener.invoke(data);
                    builder.delete(0, builder.length());
                    result = true;
                }
            }
            return result;
        });
    }
}
