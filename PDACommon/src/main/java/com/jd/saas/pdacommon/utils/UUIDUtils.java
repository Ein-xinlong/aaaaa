package com.jd.saas.pdacommon.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String v4() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
