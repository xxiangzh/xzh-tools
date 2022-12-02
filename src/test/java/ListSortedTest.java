import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 向振华
 * @date 2022/12/01 18:18
 */
public class ListSortedTest {

    @Data
    @AllArgsConstructor
    public static class Shop {
        private String name;
        private Double price;
        private Integer count;
    }

    public static void main(String[] args) {
        // 创建参数
        List<Shop> list = Lists.newArrayList(
                new Shop("1", 3.3, 10),
                new Shop("2", null, 30),
                new Shop("3", 4.4, 20),
                new Shop("4", 6.6, null),
                new Shop("5", 8.8, 20),
                new Shop("6", 8.8, 20),
                new Shop("7", 7.7, 60),
                new Shop("8", 7.7, 60)
        );

        // 多排序条件
        List<Shop> sortedShopList = list.stream().sorted(Comparator
                // 先按数量降序（由于是降序，nullsFirst()方法会将null值放在后面）
                .comparing(Shop::getCount, Comparator.nullsFirst(Integer::compareTo).reversed())
                // 然后按价格升序（由于是升序，nullsFirst()方法会将null值放在前面）
                .thenComparing(Shop::getPrice, Comparator.nullsFirst(Double::compareTo))
                // 然后按名称降序（如果不设置null值排序规则，字段为null会报错）
                .thenComparing(Shop::getName, Comparator.reverseOrder())
        ).collect(Collectors.toList());

        // 排序结果
        sortedShopList.forEach(System.out::println);
    }
}