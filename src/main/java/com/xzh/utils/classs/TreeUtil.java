package com.xzh.utils.classs;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 *
 * @author: 向振华
 * @date: 2019/10/09 10:41
 */
public class TreeUtil {

    /**
     * 转换成树形结构集合
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends Tree> List<T> build(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<T> rootList = getRootList(list);
        Map<Long, Long> map = new HashMap<>(list.size());
        rootList.forEach(t -> {
            if (!map.containsKey(t.getId())) {
                map.put(t.getId(), t.getPid());
                getChild(t, map, list);
            }
        });
        return rootList;
    }

    private static <T extends Tree> List<T> getRootList(List<T> list) {
        Set<Long> pids = list.stream().map(Tree::getPid).collect(Collectors.toSet());
        list.forEach(i -> pids.remove(i.getId()));
        return list.stream().filter(i -> pids.contains(i.getPid())).collect(Collectors.toList());
    }

    private static void getChild(Tree tree, Map<Long, Long> map, List<? extends Tree> list) {
        List<Tree> child = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tree.getChildren())) {
            child.addAll(tree.getChildren());
        }
        list.stream()
                .filter(c -> !map.containsKey(c.getId()))
                .filter(c -> c.getPid().equals(tree.getId()))
                .forEach(c -> {
                    map.put(c.getId(), c.getPid());
                    getChild(c, map, list);
                    child.add(c);
                });
        tree.setChildren(CollectionUtils.isEmpty(child) ? null : child);
    }
}
