package com.jd.saas.pdacheck.net;

/**
 * 盘点任务状态枚举
 *
 * @author gouhetong
 */
public enum CheckTaskNodeEnum {
    /** 创建未开始 */
    CREATE(10),
    /** 漏盘校对 */
    MISSED_CHECK(20),
    /** 差异处理 */
    DIFF_HANDLE(30),
    /** 复盘修改 */
    REVIEW(40),
    /** 损溢单申请 */
    PROFIT(50),
    /** 提交待审核 */
    COMMIT(60),
    /** 审批拒绝*/
    REJECT(70),
    /** 完成 */
    FINISH(80);
    private final int value;

    CheckTaskNodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
