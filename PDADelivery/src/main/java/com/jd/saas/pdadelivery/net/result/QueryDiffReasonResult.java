package com.jd.saas.pdadelivery.net.result;

import java.util.Date;

public class QueryDiffReasonResult {
    /**
     * 主键
     */
    private Long id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 业务自定义字典类型，用于自定义区分字典数据类型，可根据定义内容自行提取数据。
     */
    private String bizCode;

    /**
     * 自定义类型描述
     */
    private String bizCodeDesc;

    /**
     * 排序号码
     */
    private Integer sortNo;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 层级
     */
    private Integer dictLevel;

    /**
     * 乐观锁版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private java.util.Date createDate;

    /**
     * 创建人erp
     */
    private String createBy;

    /**
     * 更新时间
     */
    private java.util.Date updateDate;

    /**
     * 更新人erp
     */
    private String updateBy;

    /**
     * 字典项状态，1、显示、2不显示
     */
    private Integer dictStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizCodeDesc() {
        return bizCodeDesc;
    }

    public void setBizCodeDesc(String bizCodeDesc) {
        this.bizCodeDesc = bizCodeDesc;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getDictLevel() {
        return dictLevel;
    }

    public void setDictLevel(Integer dictLevel) {
        this.dictLevel = dictLevel;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDictStatus() {
        return dictStatus;
    }

    public void setDictStatus(Integer dictStatus) {
        this.dictStatus = dictStatus;
    }
}
