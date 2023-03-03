import com.xzh.utils.file.FileNameUtils;
import com.xzh.utils.file.FileUtils;
import com.xzh.utils.file.MetadataUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

public class FileNameUtilsTest {

    public static void main(String[] args) {
        List<File> fileList = FileUtils.getFileList("E:\\A");
        for (File file : fileList) {
            String dateTime = MetadataUtils.getDateTime(file, "yyyy_MM_dd_HH_mm_");
            if (StringUtils.isBlank(dateTime)) {
                continue;
            }
            FileNameUtils.rename(file, dateTime, null);
        }
    }
}