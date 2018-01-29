package cottee.myproperty.uitils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/1/30.
 */

public class HealthMap {

    private static Map map = new ConcurrentHashMap();

    /**
     * 传值
     * @param key
     * @param obj
     */
    public static void put(String key, Object obj) {
        map.put(key, obj);
    }
    /**
     * 取值  默认删除内存引用
     * @param key
     * @return
     */
    public static Object get(String key) {
        return map.remove(key);
    }

    /**
     * 取值   自定义是否删除内存引用
     * @param key
     * @param isDelete
     * @return
     */
    public static Object get(String key, boolean isDelete) {
        if (isDelete) {
            return get(key);
        } else {
            return map.get(key);
        }
    }

}