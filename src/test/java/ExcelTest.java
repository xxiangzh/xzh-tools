import bean.Game;
import com.xzh.utils.file.ExcelUtils;

import java.util.List;

/**
 * @author 向振华
 * @date 2023/03/29 18:15
 */
public class ExcelTest {

    public static void main(String[] args) {
        List<Game> list = ExcelUtils.read("E:\\123\\1_20230330152210.xlsx", Game.class);

        ExcelUtils.write("E:\\123\\", null, list, Game.class);
    }
}
