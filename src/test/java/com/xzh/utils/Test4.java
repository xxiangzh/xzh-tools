package com.xzh.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 向振华
 * @date 2020/12/14 16:41
 */
public class Test4 {
    public static void main(String[] args) {
        User user = new User(123L, "xxxzh");
        String json = JSONObject.toJSONString(user);
        System.out.println(json);
        String build = TokenUtils.build(json);
        System.out.println(build);
        User user1 = TokenUtils.get(build, User.class);
        System.out.println(user1);
    }
}
