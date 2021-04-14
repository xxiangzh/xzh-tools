package com.xzh.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

/**
 * @author 向振华
 * @date 2021/02/05 13:57
 */
public class Test6 {
    public static void main(String[] args) {
        String url = "http://localhost:7070/bop-manage/open/test";
        for (int i = 1; i <= 5; i++) {
            HttpResponse execute = HttpUtil.createGet(url + i).execute();
            HttpResponse execute2 = HttpUtil.createGet(url + i).execute();
            HttpResponse execute3 = HttpUtil.createGet(url + i).execute();
            System.out.println(i + "--->" + execute.getStatus());
            System.out.println(i + "--->" + execute2.getStatus());
            System.out.println(i + "--->" + execute3.getStatus());
        }
    }
}
