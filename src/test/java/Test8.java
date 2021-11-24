import com.xzh.utils.file.FileUtils;

import java.io.File;
import java.util.List;

/**
 * @author 向振华
 * @date 2021/11/22 16:43
 */
public class Test8 {

    public static void main(String[] args) {
        String sourceFolderDirectory = "E:\\新建文件夹";
        List<File> fileList1 = FileUtils.getDirectoryList(sourceFolderDirectory, true);
        for (File file : fileList1) {
            String absolutePath = file.getAbsolutePath();
            System.out.println(absolutePath);
            if (absolutePath.endsWith(".git")
                    || absolutePath.endsWith(".idea")
                    || absolutePath.endsWith("target")) {
                FileUtils.deleteDirectory(file);
            }
        }

        List<File> fileList2 = FileUtils.getFileList(sourceFolderDirectory, true);
        for (File file : fileList2) {
            String absolutePath = file.getAbsolutePath();
            System.out.println(absolutePath);
            if (absolutePath.endsWith(".flattened-pom.xml")
                    || absolutePath.endsWith(".iml")) {
                file.delete();
            }
        }
    }
}
