import com.xzh.utils.file.FileNameUtils;
import com.xzh.utils.file.FileUtils;
import com.xzh.utils.file.MetadataUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileNameUtilsTest {

    public static void main(String[] args) {
        List<File> fileList = FileUtils.getFileList("D:\\新建文件夹");
        Set<String> set = new HashSet<>();
        List<String> repeatList = new ArrayList<>();
        List<String> unList = new ArrayList<>();

        for (File file : fileList) {
            String dateTime = MetadataUtils.getDateTime(file, "yyyy_MM_dd_HH_mm_ss");
            if (StringUtils.isBlank(dateTime)) {
                unList.add(file.getName());
                continue;
            }
            if (set.contains(dateTime)) {
                repeatList.add(file.getName());
                continue;
            }

            FileNameUtils.rename(file, dateTime, null);
            set.add(dateTime);
        }

        System.out.println(repeatList);
        System.out.println(unList);
    }
}