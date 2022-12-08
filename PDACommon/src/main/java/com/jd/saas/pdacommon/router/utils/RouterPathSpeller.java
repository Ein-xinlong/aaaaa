package com.jd.saas.pdacommon.router.utils;


import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author majiheng
 */
public class RouterPathSpeller {

    //host=type+model，中间间隔符为点号
    private static final String ROUTER_DOT = ".";

    private String mScheme;

    private String mType;

    private String mHost;

    private String mPath;

    private HashMap<String, String> mArrayMap;

    public RouterPathSpeller(String scheme, String type, String host, String path, HashMap<String, String> arrayMap) {

        this.mScheme = scheme;
        this.mType = type;
        this.mHost = host;
        this.mPath = path;
        this.mArrayMap = arrayMap;
    }

    public static RouterPathBuilder builder() {
        return new RouterPathBuilder();
    }

    private String createPath() {
        if (TextUtils.isEmpty(mScheme)) {
            throw new RuntimeException("协议缺少Scheme!");
        }
        if (TextUtils.isEmpty(mType)) {
            throw new RuntimeException("协议缺少Type!");
        }
        if (TextUtils.isEmpty(mHost)) {
            throw new RuntimeException("协议缺少Host!");
        }
        if (TextUtils.isEmpty(mPath)) {
            throw new RuntimeException("协议缺少Path!");
        }
        if (!mPath.startsWith("/")) {
            return mScheme + "://" + mType + ROUTER_DOT + mHost + "/" + mPath + spellParamPath(mArrayMap);
        } else {
            return mScheme + "://" + mType + ROUTER_DOT + mHost + mPath + spellParamPath(mArrayMap);
        }
    }

    public String spellParamPath(HashMap<String, String> params) {

        if (params == null || params.size() == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder("?");

        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            stringBuilder.append(key).append("=").append(val);
            if (iter.hasNext()) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }

    public static class RouterPathBuilder {

        private String mScheme;

        private String mType;

        private String mHost;

        private String mPath;

        private HashMap<String, String> mArrayMap;

        RouterPathBuilder() {

        }

        public RouterPathBuilder addScheme(String scheme) {
            this.mScheme = scheme;
            return this;
        }

        public RouterPathBuilder addHost(String host) {
            this.mHost = host;
            return this;
        }

        public RouterPathBuilder addType(String type) {
            this.mType = type;
            return this;
        }

        public RouterPathBuilder addPath(String path) {
            this.mPath = path;
            return this;
        }

        public RouterPathBuilder addParam(HashMap<String, String> map) {
            this.mArrayMap = map;
            return this;
        }

        public String buildPath() {
            return new RouterPathSpeller(mScheme, mType, mHost, mPath, mArrayMap).createPath();
        }
    }
}
