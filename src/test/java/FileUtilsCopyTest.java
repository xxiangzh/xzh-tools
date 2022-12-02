import com.xzh.utils.file.FileUtils;

import java.util.List;

public class FileUtilsCopyTest {

    public static void main(String[] args) {
        FileUtils.copyWithoutKey("E:\\迅雷下载\\", "E:\\迅雷下载\\HS", ".torrent", ".bt.xltd");

        System.out.println("success");
    }
}