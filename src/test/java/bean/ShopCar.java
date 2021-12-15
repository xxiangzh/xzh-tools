package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 向振华
 * @date 2021/12/15 14:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCar {

    /**
     * 商品名
     */
    private String name;

    /**
     * 单价
     */
    private Double price;

    /**
     * 数量
     */
    private Integer count;
}