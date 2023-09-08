import bean.ExcelParam;
import bean.Game;
import com.xzh.utils.file.ExcelUtils;

import java.util.List;

/**
 * @author 向振华
 * @date 2023/03/29 18:15
 */
public class ExcelParamTest {

    /**
     *
     */
    public static void main(String[] args) {
        List<ExcelParam> list = ExcelUtils.read("D:\\abc.xlsx", ExcelParam.class);
        StringBuilder sb = new StringBuilder();
        for (ExcelParam excelParam : list) {
            sb.append("/**").append("\n");
            sb.append("* ").append(excelParam.toString().replace("ExcelParam(", "").replace(")", "")).append("\n");
            sb.append("*/").append("\n");
            sb.append("@ApiModelProperty(value = \"").append(excelParam.get中文名称()).append("\")").append("\n");
            sb.append("private String ").append(excelParam.get信息域()).append(";\n");
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
