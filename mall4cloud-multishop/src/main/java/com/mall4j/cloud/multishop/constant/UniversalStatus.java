package com.mall4j.cloud.multishop.constant;

public enum UniversalStatus {
    /**
     * 使用,设为默认地址
     */
    USE(1),
    /**
     * 未使用,不设为默认地址
     */
    UNUSED(0)
    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    UniversalStatus(Integer value) {
        this.value = value;
    }
}
