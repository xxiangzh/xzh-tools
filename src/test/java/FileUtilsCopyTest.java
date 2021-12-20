import com.xzh.utils.file.FileUtils;

public class FileUtilsCopyTest {

    public static void main(String[] args) {
        FileUtils.copyWithoutKey("E:\\B", "E:\\C", ".png", "jpg");
    }
}