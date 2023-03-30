package bean;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 向振华
 * @date 2023/03/29 18:17
 */
@Data
public class Game {

    @ExcelProperty(value = "名称")
    private String name;

    private String type;

    private String link;

    @ExcelIgnore
    private String remark;

}
