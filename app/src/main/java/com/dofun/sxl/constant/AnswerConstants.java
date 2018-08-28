package com.dofun.sxl.constant;

import java.util.HashMap;
import java.util.Map;

public class AnswerConstants {
    public static Map<Object, Object> sjdMap = new HashMap<>();

    public static void setSjdAnswer(Object key, Object value) {
        sjdMap.put(key, value);
    }

    public static Map<Object, Object> lysMap = new HashMap<>();

    public static void setLysAnswer(Object key, Object value) {
        lysMap.put(key, value);
    }

    public static String workImg = "";
}
