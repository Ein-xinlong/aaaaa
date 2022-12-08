package com.jd.saas.pdacheck.record.bean;


import android.view.View;

/**
 * 盘存记录bean
 *
 * @author ext.mengmeng
 */
public class CheckRecordBean {
    private String title;//标题
    private String number;//商品条码
    private String specification;//单位
    private String storage_location;//库位
    private String storage_location_number;//实盘数量

    public CheckRecordBean(String title, String number, String specification, String storage_location, String storage_location_number) {
        this.title = title;
        this.number = number;
        this.specification = specification;
        this.storage_location = storage_location;
        this.storage_location_number = storage_location_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getStorage_location() {
        return storage_location;
    }

    public void setStorage_location(String storage_location) {
        this.storage_location = storage_location;
    }

    public String getStorage_location_number() {
        return storage_location_number;
    }

    public void setStorage_location_number(String storage_location_number) {
        this.storage_location_number = storage_location_number;
    }



    public void delete(View view){
//        View dialogView= LayoutInflater.from(view.getContext()).inflate(R.layout.inventory_adjustment_detail_dialog,null);
//        TextView cancel = dialogView.findViewById(R.id.inventory_adjustment_dialog_cancel);
//        TextView sure = dialogView.findViewById(R.id.inventory_adjustment_dialog_sure);
//        AlertDialog alertDialog =  new AlertDialog.Builder(view.getContext())
//                .setView(dialogView)
//                .create();
//
//        Window window = alertDialog.getWindow();
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//        sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                alertDialog.dismiss();
//            }
//        });
    }

}
