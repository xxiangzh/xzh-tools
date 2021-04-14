package com.xzh.utils.classs;

import lombok.Data;

import java.util.List;

/**
 * @author: 向振华
 * @date: 2019/10/09 10:41
 */
@Data
public class Tree {

    private Long id;

    private Long pid;

    private List<? extends Tree> children;

    public Tree(Long id, Long pid) {
        this.id = id;
        this.pid = pid;
    }

    public Tree(Long pid) {
        this.pid = pid;
    }

    public Tree() {
    }
}
