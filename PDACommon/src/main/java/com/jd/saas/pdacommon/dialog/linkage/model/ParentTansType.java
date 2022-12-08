package com.jd.saas.pdacommon.dialog.linkage.model;

import androidx.annotation.ColorRes;

import com.jd.saas.pdacommon.R;

import java.util.List;

public class ParentTansType {
    /*
      {"code":0,"msg":"调用成功","errorDesc":null,"data":[{"value":"REC_NORM","name":"入库","parent":"","children":[{"value":"PURCHASE_ORDER","name":"直送入库","parent":"REC_NORM","children":[]},{"value":"INITIALLY","name":"期初入库","parent":"REC_NORM","children":[]},{"value":"DISTRIBUTION","name":"配送入库","parent":"REC_NORM","children":[]},{"value":"INCOME_DIFFERENCE","name":"收货差异入","parent":"REC_NORM","children":[]},{"value":"RETURN_DC","name":"门店退仓单","parent":"REC_NORM","children":[]}]},{"value":"ADJUST","name":"调整","parent":"","children":[]},{"value":"LOT_ADJUST","name":"批次效期调整","parent":"","children":[]},{"value":"CHECK","name":"盘点","parent":"","children":[]},{"value":"OUT","name":"出库","parent":"","children":[{"value":"RTV","name":"退供出库","parent":"OUT","children":[]},{"value":"ALLOTOUT","name":"调拨出库","parent":"OUT","children":[]},{"value":"DCOUT","name":"配送中心出库单","parent":"OUT","children":[]},{"value":"RMV","name":"退仓出库","parent":"OUT","children":[]}]},{"value":"ALLOC","name":"分配","parent":"","children":[]}],"responseId":null}
     */
    private String value;
    private String name;
    private String parent;
    private List<ChildTansType> children;
    private boolean isSelected = false;

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setChildren(List<ChildTansType> children) {
        this.children = children;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public List<ChildTansType> getChildren() {
        return children;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @ColorRes
    public int getBackgroundRes() {
        if (isSelected) {
            return R.color.white;
        } else {
            return R.color.color_dialog_linkage_grey;
        }
    }
}
