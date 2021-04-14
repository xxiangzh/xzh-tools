package com.xzh.utils.classs;

import com.alibaba.fastjson.JSONArray;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class工具
 *
 * @author 向振华
 * @date 2018/11/17 13:52
 */
public class ClassUtils {

    /**
     * 获取指定包下面所有的class文件
     *
     * @param packagePath 包路径
     * @return
     */
    public static File[] getResources(String packagePath) {
        try {
            URL resource = ClassUtils.class.getClassLoader().getResource(packagePath.replace(".", "/"));
            if (resource == null) {
                return null;
            }
            File file = new File(resource.toURI());
            return file.listFiles(pathName -> pathName.getName().endsWith(".class"));
        } catch (URISyntaxException e) {
            return null;
        }
    }

    /**
     * 实体对象转成Map
     *
     * @param objs 实体对象
     * @return
     */
    public static Map toMap(Object... objs) {
        Map map = new HashMap();
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(obj));
                } catch (Exception e) {
                }
            }
        }
        return map;
    }

    /**
     * 根据字段名设置属性
     *
     * @param object
     * @param name
     * @param value
     */
    public static void setValue(Object object, String name, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, object.getClass());
            Method method = pd.getWriteMethod();
            method.invoke(object, value);
        } catch (Exception e) {
        }
    }

    /**
     * 根据字段名获取属性
     *
     * @param object
     * @param name
     * @return
     */
    public static Object getValue(Object object, String name) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, object.getClass());
            Method method = pd.getReadMethod();
            Object value = method.invoke(object);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据字段名获取属性
     *
     * @param object
     * @param name
     * @return
     */
    public static Object getValueByName(Object object, String name) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + name.substring(1);
        try {
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密/解密
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T decode(T object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                Sign annotation = field.getAnnotation(Sign.class);
                Class fieldClass = field.getType();
                String name = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(name, object.getClass());
                Object value = pd.getReadMethod().invoke(object);
                if (value == null) {
                    continue;
                }
                if (annotation != null) {
                    pd.getWriteMethod().invoke(object, "【" + value + "】");
                } else if (fieldClass == List.class) {
                    Type listType = field.getGenericType();
                    ParameterizedType pt = (ParameterizedType) listType;
                    Class<?> listClass = (Class<?>) pt.getActualTypeArguments()[0];
                    List list = JSONArray.parseArray(JSONArray.toJSONString(value), listClass);
                    for (Object o : list) {
                        decode(o);
                    }
                    pd.getWriteMethod().invoke(object, list);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return object;
    }
}
