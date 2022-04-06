package com.xzh.utils.page;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.util.CollectionUtils;

/**
 * @author 向振华
 * @date 2021/12/30 1:41
 */
public class PageUtils {

    public static <T> void pageTurner(LambdaQueryChainWrapper<T> lambdaQueryChainWrapper, PageService<T> pageService) {
        // 当前页
        long current = 1;
        // 分页大小，可以修改
        long size = 200;
        for (; ; ) {
            // 分页
            Page<T> page = new Page<>(current, size);
            // 执行
            lambdaQueryChainWrapper.page(page);
            // 结果为空，直接返回
            if (CollectionUtils.isEmpty(page.getRecords())) {
                return;
            }
            // 业务处理
            pageService.business(page.getRecords());
            // 说明没有下一页，直接返回
            if (current * size >= page.getTotal()) {
                return;
            }
            // 下一页
            current++;
        }
    }

    public static <T> void pageTurner(IService<T> iService, Wrapper<T> queryWrapper, PageService<T> pageService) {
        // 当前页
        long current = 1;
        // 分页大小，可以修改
        long size = 200;
        for (; ; ) {
            // 分页
            Page<T> page = new Page<>(current, size);
            // 执行
            iService.page(page, queryWrapper);
            // 结果为空，直接返回
            if (CollectionUtils.isEmpty(page.getRecords())) {
                return;
            }
            // 业务处理
            pageService.business(page.getRecords());
            // 说明没有下一页，直接返回
            if (current * size >= page.getTotal()) {
                return;
            }
            // 下一页
            current++;
        }
    }
}