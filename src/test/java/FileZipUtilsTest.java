import com.xzh.utils.file.FileNameUtils;
import com.xzh.utils.file.FileUtils;
import com.xzh.utils.file.FileZipUtils;

import java.io.File;
import java.util.List;

/**
 * @author 向振华
 * @date 2021/12/21 18:03
 */
public class FileZipUtilsTest {

    private static final String SOURCE_FOLDER_DIRECTORY = "E:\\B";
    private static final String PASSWORD = "123456";
    private static final String SOURCE = ".mp4";
    private static final String TXT = ".txt";

    public static void main(String[] args) {
//        zip();
        unZip();
    }

    private static void zip() {
        List<File> fileList1 = FileUtils.getFileList(SOURCE_FOLDER_DIRECTORY);
        for (File file : fileList1) {
            FileNameUtils.rename(file, null, TXT);
        }

        List<File> fileList2 = FileUtils.getFileList(SOURCE_FOLDER_DIRECTORY);
        for (File file : fileList2) {
            FileZipUtils.compress(file, PASSWORD);
        }

        List<File> fileList4 = FileUtils.getFileList(SOURCE_FOLDER_DIRECTORY + "\\zip");
        int i = 1;
        for (File file : fileList4) {
            FileNameUtils.rename(file, String.valueOf(i++), TXT);
        }

        List<File> fileList5 = FileUtils.getCurrentFileList(SOURCE_FOLDER_DIRECTORY);
        for (File file : fileList5) {
            FileNameUtils.rename(file, null, SOURCE);
        }
    }

    private static void unZip() {
        List<File> fileList1 = FileUtils.getFileList(SOURCE_FOLDER_DIRECTORY);
        for (File file : fileList1) {
            FileNameUtils.rename(file, null, ".zip");
        }

        List<File> fileList2 = FileUtils.getFileList(SOURCE_FOLDER_DIRECTORY);
        for (File file : fileList2) {
            FileZipUtils.uncompress(file, PASSWORD);
        }

        List<File> fileList4 = FileUtils.getFileList(SOURCE_FOLDER_DIRECTORY + "\\unzip");
        for (File file : fileList4) {
            FileNameUtils.rename(file, null, SOURCE);
        }

        List<File> fileList5 = FileUtils.getCurrentFileList(SOURCE_FOLDER_DIRECTORY);
        for (File file : fileList5) {
            FileNameUtils.rename(file, null, TXT);
        }
    }
}
