package com.xzh.utils.object;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON工具
 *
 * @author 向振华
 * @date 2021/05/07 13:53
 */
public class JsonUtils {

    /**
     * 转换json为实体类
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parse(String text, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        Object json = JSONObject.parse(text);
        if (json instanceof JSONObject) {
            list.add(JSONObject.parseObject(json.toString(), clazz));
        } else if (json instanceof JSONArray) {
            list = JSONObject.parseArray(json.toString(), clazz);
        }
        return list;
    }
}