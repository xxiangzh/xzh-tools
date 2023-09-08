package com.xzh.utils.file;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 加载配置文件工具
 *
 * @author 向振华
 * @date 2023/09/08 17:35
 */
public class ResourceLoaderUtils {

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    /**
     * 获取取配置文件
     *
     * @param location
     * @return
     */
    public static Resource get(String location) {
        Resource resource = RESOURCE_LOADER.getResource(location);
        return resource;
    }
}
