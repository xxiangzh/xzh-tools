package bean;

import lombok.Data;

/**
 * @author 向振华
 * @date 2023/09/08 11:08
 */
@Data
public class ExcelParam {

    private String 中文名称;
    private String 信息域;
    private String 字段长度;
    private String 参数说明;
    private String 是否必填;
    private String 样例;

}
