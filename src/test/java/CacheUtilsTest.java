import com.xzh.utils.object.CacheUtils;

/**
 * @author 向振华
 * @date 2021/02/26 15:55
 */
public class CacheUtilsTest {

    public static void main(String[] args) throws Exception {
        System.out.println(CacheUtils.get("x"));
        CacheUtils.put("x", "aaa");
        CacheUtils.put("x", "bbb");
        System.out.println(CacheUtils.get("x"));
        Thread.sleep(5000L);

        System.out.println("-------");
        System.out.println(CacheUtils.get("x"));
        CacheUtils.put("x", "aaa");
        System.out.println(CacheUtils.get("x"));
    }
}
