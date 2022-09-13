import bean.ShopCar;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * stream demo
 *
 * @author 向振华
 * @date 2018/11/14 16:47
 */
public class ListTest {

    private static List<ShopCar> build() {
        return Lists.newArrayList(
                new ShopCar("苹果", 3.3, 10),
                new ShopCar("橘子", 5.5, 30),
                new ShopCar("香蕉", 4.4, 20),
                new ShopCar("苹果", 6.6, 40),
                new ShopCar("香蕉", 8.8, 20),
                new ShopCar("苹果", 7.7, 60)
        );
    }

    public static void main(String[] args) {
        List<ShopCar> list = build();
        //过滤
        filter(list);
        //排序
        sort(list);
        //统计
        sum(list);
        //聚合
        collect(list);
    }

    private static void filter(List<ShopCar> list) {
        //移除集合中的元素
        list.removeIf(s -> s.getPrice() > 4);

        //过滤出数量大于0的，然后将名字拼接成字符串
        String names = list.stream().filter(s -> s.getCount() > 0).map(ShopCar::getName).collect(Collectors.joining("、"));

        //只能去完全相同的实体
        List<ShopCar> list1 = list.stream().distinct().collect(Collectors.toList());

        //去按指定参数去重
        List<ShopCar> list2 = list.stream().filter(distinctByKey(ShopCar::getName)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static void sort(List<ShopCar> list) {
        //按价格降序
        // 写法1，在原集合上排序
        list.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
        // 写法2，在原集合上排序
        list.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        // 写法3，在原集合上排序，reversed() 表示降序
        list.sort(Comparator.comparing(ShopCar::getPrice).reversed());
        // 写法4，原集合不变，需要返回排序后的集合
        List<ShopCar> collect1 = list.stream().sorted(Comparator.comparing(ShopCar::getPrice).reversed()).collect(Collectors.toList());

        //按价格升序
        List<ShopCar> collect2 = list.stream().sorted(Comparator.comparing(ShopCar::getPrice)).collect(Collectors.toList());

        // 空字段放最末尾写法
        list.sort(Comparator.comparing(ShopCar::getPrice, Comparator.nullsLast(Double::compareTo)).reversed());

        //多排序条件
        List<ShopCar> collect3 = list.stream()
                .sorted(Comparator
                        .comparing(ShopCar::getCount, Comparator.reverseOrder()) // 先按数量降序
                        .thenComparing(ShopCar::getPrice) // 然后按价格升序
                        .thenComparing(ShopCar::getName, Comparator.reverseOrder()) // 然后按名称降序
                ).collect(Collectors.toList());
    }

    private static void sum(List<ShopCar> list) {
        // 统计出总价格
        Double allPrice = list.stream().map(s -> s.getPrice() * s.getCount()).reduce(Double::sum).orElse(0D);

        // 统计数量和
        Integer totalCount = list.stream().map(ShopCar::getCount).reduce(Integer::sum).orElse(0);
    }

    private static void collect(List<ShopCar> list) {
        // 取出集合中的某字段封装在list
        List<String> collect1 = list.stream().map(ShopCar::getName).collect(Collectors.toList());

        // 将集合中数据聚合到map，name为key，shopCar全部放在一个list中作为value
        Map<String, List<ShopCar>> collect2 = list.stream().collect(Collectors.groupingBy(ShopCar::getName));

        // 取出集合中两个字段封装在map，注意map的key必须唯一（即：shopCarList的name唯一）
        Map<String, Integer> collect3 = list.stream().collect(Collectors.toMap(ShopCar::getName, ShopCar::getCount));

        // 将集合中数据计算后封装在map，注意map的key必须唯一（即：shopCarList的name唯一）
        Map<String, Double> collect4 = list.stream().collect(Collectors.toMap(ShopCar::getName, s -> s.getPrice() * s.getCount()));

        // 将集合中数据聚合到map，name为key，shopCar为value，注意map的key必须唯一（即：shopCarList的name唯一）
        Map<String, ShopCar> collect5 = list.stream().collect(Collectors.toMap(ShopCar::getName, Function.identity()));

        // 统计数量和
        IntSummaryStatistics collect6 = list.stream().collect(Collectors.summarizingInt(ShopCar::getCount));

        //统计价格和
        DoubleSummaryStatistics collect7 = list.stream().collect(Collectors.summarizingDouble(ShopCar::getPrice));

        // 统计操作
        DoubleSummaryStatistics collect8 = list.stream().mapToDouble(s -> s.getPrice() * s.getCount()).summaryStatistics();
        collect8.getCount();// 商品个数
        collect8.getAverage();// 平均花费
        collect8.getMax();// 最高花费
        collect8.getMin();// 最低花费
        collect8.getSum();// 总共花费
    }
}
