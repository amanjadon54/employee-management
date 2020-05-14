package com.ems.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformUtil {
    private static final Logger log = LoggerFactory.getLogger(TransformUtil.class);

    private static final Gson gson = new Gson();

    public static String toJson(Object obj) {
        try {
            if (obj != null) {
                return gson.toJson(obj);
            }
        } catch (JsonParseException e) {
            log.error("Error in toJson(), obj: " + obj + " ; Exception: " + e.getMessage());
        }
        return null;
    }
}
