package com.jd.saas.pdadelivery.manage.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.util.AsnEnumUtils;
import com.jd.saas.pdadelivery.util.Formatter;

import java.util.Date;
import java.util.Objects;

/**
 * 收货
 *
 * @author ext.mengmeng
 */
public class DeliveryBean implements Parcelable {
    private int status;//收货中，未收货，已完成
    private int asnType;//直送单,配送单,
    private String asnNo;

    /**
     * [:INTEGER]收货种类
     */
    private int rcvTypeSize;
    /**
     * [:INTEGER]已收齐
     */
    private int rcvFinished;
    /**
     * [:INTEGER]未收齐
     */
    private int rcvSome;
    /**
     * [:INTEGER]未收货
     */
    private int rcvNone;

    private String supplierName;//供货方
    private String contactName;//负责人
    private String contactTelephone;//负责人电话
    private Date updateDate;//最后操作时间
    private Date createDate;//创建时间
    private String asnRefNo;//采购单号

    public DeliveryBean() {
    }

    public String getTypeStr() {
        return AsnEnumUtils.getAsnTypeName(asnType);
    }

    public String getArrowStr() {
        if (status == AsnStatusEnum.INITIAL.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_receive);
        } else if (status == AsnStatusEnum.PART_RECEIVE.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_continue);
        } else if (status == AsnStatusEnum.RECEIVED.getValue() || status == AsnStatusEnum.DIFF_AUDIT.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_details);
        } else {
            return "";
        }
    }

    public String getRcvTypeSizeStr() {
        return Formatter.format(rcvTypeSize);
    }

    public String getRcvFinishedStr() {
        return Formatter.format(rcvFinished);
    }

    public String getRcvNoneStr() {
        return Formatter.format(rcvNone);
    }

    public String getRcvSomeStr() {
        return Formatter.format(rcvSome);
    }


    public int getTimeStrId() {
        if (status == AsnStatusEnum.INITIAL.getValue()) {
            return R.string.delivery_create_time;
        } else {
            return R.string.delivery_operation_time;
        }
    }

    public String getTimeStrValue() {
        if (status == AsnStatusEnum.INITIAL.getValue()) {
            return Formatter.format(createDate);
        } else {
            return Formatter.format(updateDate);
        }
    }

    public int getNumberInfoLayoutVisibility() {
        if (status == AsnStatusEnum.INITIAL.getValue()) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }
    }

    public String getContactStr() {
        if (contactName == null && contactTelephone == null) {
            return "";
        }
        if (contactName == null) {
            return contactTelephone;
        }
        if (contactTelephone == null) {
            return contactName;
        }
        return contactName + "    " + contactTelephone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAsnType() {
        return asnType;
    }

    public void setAsnType(int asnType) {
        this.asnType = asnType;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public int getRcvTypeSize() {
        return rcvTypeSize;
    }

    public void setRcvTypeSize(int rcvTypeSize) {
        this.rcvTypeSize = rcvTypeSize;
    }

    public int getRcvFinished() {
        return rcvFinished;
    }

    public void setRcvFinished(int rcvFinished) {
        this.rcvFinished = rcvFinished;
    }

    public int getRcvSome() {
        return rcvSome;
    }

    public void setRcvSome(int rcvSome) {
        this.rcvSome = rcvSome;
    }

    public int getRcvNone() {
        return rcvNone;
    }

    public void setRcvNone(int rcvNone) {
        this.rcvNone = rcvNone;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAsnRefNo() {
        return asnRefNo;
    }

    public void setAsnRefNo(String asnRefNo) {
        this.asnRefNo = asnRefNo;
    }


    protected DeliveryBean(Parcel in) {
        status = in.readInt();
        asnType = in.readInt();
        asnNo = in.readString();
        rcvTypeSize = in.readInt();
        rcvFinished = in.readInt();
        rcvSome = in.readInt();
        rcvNone = in.readInt();
        supplierName = in.readString();
        contactName = in.readString();
        contactTelephone = in.readString();
        asnRefNo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeInt(asnType);
        dest.writeString(asnNo);
        dest.writeInt(rcvTypeSize);
        dest.writeInt(rcvFinished);
        dest.writeInt(rcvSome);
        dest.writeInt(rcvNone);
        dest.writeString(supplierName);
        dest.writeString(contactName);
        dest.writeString(contactTelephone);
        dest.writeString(asnRefNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeliveryBean> CREATOR = new Creator<DeliveryBean>() {
        @Override
        public DeliveryBean createFromParcel(Parcel in) {
            return new DeliveryBean(in);
        }

        @Override
        public DeliveryBean[] newArray(int size) {
            return new DeliveryBean[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryBean that = (DeliveryBean) o;
        return status == that.status &&
                asnType == that.asnType &&
                rcvTypeSize == that.rcvTypeSize &&
                rcvFinished == that.rcvFinished &&
                rcvSome == that.rcvSome &&
                rcvNone == that.rcvNone &&
                Objects.equals(asnNo, that.asnNo) &&
                Objects.equals(supplierName, that.supplierName) &&
                Objects.equals(contactName, that.contactName) &&
                Objects.equals(contactTelephone, that.contactTelephone) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(asnRefNo, that.asnRefNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, asnType, asnNo, rcvTypeSize, rcvFinished, rcvSome, rcvNone, supplierName, contactName, contactTelephone, updateDate, createDate, asnRefNo);
    }
}
