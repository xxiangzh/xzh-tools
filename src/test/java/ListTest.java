import bean.ShopCar;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 向振华
 * @date 2018/11/14 16:47
 */
public class ListTest {

    private static List<ShopCar> build() {
        return Lists.newArrayList(
                new ShopCar("苹果", 3.3, 10),
                new ShopCar("香蕉", 4.4, 20),
                new ShopCar("橘子", 5.5, 30)
        );
    }

    public static void main(String[] args) {
        List<ShopCar> shopCarList = build();
        //过滤
        filter(shopCarList);
        //排序
        sort(shopCarList);
        //统计
        sum(shopCarList);
        //聚合
        collect(shopCarList);
    }

    private static void filter(List<ShopCar> shopCarList) {
        //移除集合中的元素
        shopCarList.removeIf(s -> s.getPrice() > 4);

        //过滤出数量大于0的，然后将名字拼接成字符串
        String names = shopCarList.stream().filter(s -> s.getCount() > 0).map(ShopCar::getName).collect(Collectors.joining("、"));

        //只能去完全相同的实体
        List<ShopCar> shopCarList1 = shopCarList.stream().distinct().collect(Collectors.toList());

        //去按指定参数去重
        List<ShopCar> shopCarList2 = shopCarList.stream().filter(distinctByKey(ShopCar::getName)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static void sort(List<ShopCar> shopCarList) {
        //按价格降序
        shopCarList.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
        shopCarList.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        shopCarList.sort(Comparator.comparing(ShopCar::getPrice).reversed()); // 标准写法，reversed()表示降序

        //按价格升序
        shopCarList.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
        shopCarList.sort(Comparator.comparing(ShopCar::getPrice)); // 标准写法
        // 空字段放最末尾
        shopCarList.sort(Comparator.comparing(ShopCar::getPrice, Comparator.nullsLast(Double::compareTo)).reversed());

        //多排序条件
        List<ShopCar> collect = shopCarList.stream()
                .sorted(Comparator
                        .comparing(ShopCar::getCount, Comparator.reverseOrder()) //先按数量降序
                        .thenComparing(ShopCar::getPrice) // 然后按价格升序
                        .thenComparing(ShopCar::getName, Comparator.reverseOrder()) // 然后按名称降序
                ).collect(Collectors.toList());
    }

    private static void sum(List<ShopCar> shopCarList) {
        //统计出总价格
        Double allPrice = shopCarList.stream().map(s -> s.getPrice() * s.getCount()).reduce(Double::sum).get();

        //统计数量和
        Integer totalCount = shopCarList.stream().map(ShopCar::getCount).reduce(Integer::sum).get();
    }

    private static void collect(List<ShopCar> shopCarList) {
        //取出集合中的某字段封装在list
        List<String> collect1 = shopCarList.stream().map(ShopCar::getName).collect(Collectors.toList());

        // 将集合中数据聚合到map，name为key，shopCar全部放在一个list中作为value
        Map<String, List<ShopCar>> collect11 = shopCarList.stream().collect(Collectors.groupingBy(ShopCar::getName));

        //取出集合中两个字段封装在map，注意map的key必须唯一（即：shopCarList的name唯一）
        Map<String, Integer> collect2 = shopCarList.stream().collect(Collectors.toMap(ShopCar::getName, ShopCar::getCount));

        //将集合中数据计算后封装在map，注意map的key必须唯一（即：shopCarList的name唯一）
        Map<String, Double> collect3 = shopCarList.stream().collect(Collectors.toMap(ShopCar::getName, s -> s.getPrice() * s.getCount()));

        // 将集合中数据聚合到map，name为key，shopCar为value，注意map的key必须唯一（即：shopCarList的name唯一）
        Map<String, ShopCar> collect4 = shopCarList.stream().collect(Collectors.toMap(ShopCar::getName, Function.identity()));

        //统计数量和
        IntSummaryStatistics collect5 = shopCarList.stream().collect(Collectors.summarizingInt(ShopCar::getCount));

        //统计价格和
        DoubleSummaryStatistics collect6 = shopCarList.stream().collect(Collectors.summarizingDouble(ShopCar::getPrice));

        //统计操作
        DoubleSummaryStatistics collect7 = shopCarList.stream().mapToDouble(s -> s.getPrice() * s.getCount()).summaryStatistics();
        collect7.getCount();//商品个数
        collect7.getAverage();//平均花费
        collect7.getMax();//最高花费
        collect7.getMin();//最低花费
        collect7.getSum();//总共花费
    }
}
