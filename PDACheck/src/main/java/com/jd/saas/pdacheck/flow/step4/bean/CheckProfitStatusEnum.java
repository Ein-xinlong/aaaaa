package com.jd.saas.pdacheck.flow.step4.bean;

/**
 * 查询损益单状态
 *
 * @author majiheng
 */
public enum CheckProfitStatusEnum {

    // 未生成损益
    NO_GAL(10),
    // 生成损溢失败
    GAL_FAIL(20),
    // 生成损益中
    GAL_ING(30),
    // 无损益
    GAL_NONE(35),
    // 生成损溢成功，初始化状态
    GAL_SUCCESS(40),
    // 审核中
    AUDIT_ING(50),
    // 已完成
    SUBMIT_AUDIT(60),
    // 未知错误
    ERR(0);

    private final int value;

    CheckProfitStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
