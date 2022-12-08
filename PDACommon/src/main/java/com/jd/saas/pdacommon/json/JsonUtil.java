package com.jd.saas.pdacommon.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * json操作工具类
 *
 * @author majiheng
 */
public class JsonUtil {

    public static String ObjectToString(Object src) {
        return new Gson().toJson(src);
    }

    public static <T> T StringToObject(String json, Class<T> cls) {
        try {
            return new Gson().fromJson(json, cls);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T[] stringToArray(String json, Class<T[]> cls) {
        try {
            return new Gson().fromJson(json, cls);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T stringToList(String json, TypeToken<T> token) {
        try {
            return new Gson().fromJson(json, token.getType());
        } catch (Exception e) {
            return null;
        }
    }
}
