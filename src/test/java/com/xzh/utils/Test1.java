package com.xzh.utils;

import com.xzh.utils.file.FileUtils;

public class Test1 {

    public static void main(String[] args) {
        FileUtils.copyWithoutKey("E:\\B", "E:\\C", new String[]{".png", ".jpg"});
    }
}