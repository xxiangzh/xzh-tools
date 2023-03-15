import com.xzh.utils.file.FileIOUtils;

import java.util.List;

/**
 * @author 向振华
 * @date 2023/03/15 11:07
 */
public class FileReadTest {

    public static void main(String[] args) {
        String pathname = "D:\\Z\\123.txt";
        List<String> list = FileIOUtils.readLines(pathname);
        for (String s : list) {
            System.out.println(s);
        }
    }
}









