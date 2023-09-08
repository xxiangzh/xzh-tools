import com.xzh.utils.file.ResourceLoaderUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * @author 向振华
 * @date 2023/09/08 17:41
 */
public class ResourceLoaderTest {

    public static void main(String[] args) throws IOException {

        Resource resource = ResourceLoaderUtils.get("classpath:META-INF/1.txt");

        // 是否存在
        System.out.println(resource.exists());

        // 文件
        File file = resource.getFile();
        System.out.println(file);
    }
}
