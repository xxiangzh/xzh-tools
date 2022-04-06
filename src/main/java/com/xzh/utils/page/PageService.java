package com.xzh.utils.page;


import java.util.List;

/**
 * @author 向振华
 * @date 2021/12/30 11:30
 */
public interface PageService<T> {

    /**
     * 业务处理
     *
     * @param records
     */
    void business(List<T> records);
}
