package com.jd.saas.pdacommon.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T1, T2> ArrayList<T2> map(ArrayList<T1> list, MapTo<T1, T2> mapTo) {
        ArrayList<T2> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        for (T1 t1 : list) {
            result.add(mapTo.map(t1));
        }
        return result;
    }

    public static <T1, T2> List<T2> map(List<T1> list, MapTo<T1, T2> mapTo) {
        List<T2> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        for (T1 t1 : list) {
            result.add(mapTo.map(t1));
        }
        return result;
    }

    public interface MapTo<T1, T2> {
        T2 map(T1 from);
    }

    public static <E> List<E> filter(List<E> list, Predicate<E> predicate) {
        List<E> result = new ArrayList<>();
        for (E e : list) {
            if (predicate.predicate(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public interface Predicate<E> {
        boolean predicate(E item);
    }

}
