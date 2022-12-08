package com.jd.saas.pdacommon.router.enums;

/**
 * @author majiheng
 * Description:host由类型+模块组成
 */
public enum HostTypeEnum {
    NATIVE("native", "原生跳转"),
    WEB("web","H5页面跳转"),
//    FLUTTER("flutter","flutter跳转"),
    RN("rn","rn跳转");

    private String name;
    private String description;
    /**
     * @param name        英文编码名称
     * @param description 中文描述
     */
    HostTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * 获取枚举类型英文编码名称
     */
    public String toName() {
        return this.name == null ? this.name() : this.name;
    }

    /**
     * 获取枚举类型中文描述
     */
    public String toDescription() {
        return this.description;
    }

    /**
     * 按英文编码获取对应的枚举类型
     *
     * @param name 英文编码
     * @return 枚举类型
     */
    public static HostTypeEnum enumValueOf(String name) {
        HostTypeEnum[] values = HostTypeEnum.values();
        HostTypeEnum v = null;
        for (HostTypeEnum value : values) {
            if (value.toName().equalsIgnoreCase(name)) {
                v = value;
                break;
            }
        }
        return v;
    }
}
