package com.xzh.utils.object;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean复制工具
 *
 * @author 向振华
 * @date 2023/02/20 16:05
 * @see org.springframework.beans.BeanUtils
 */
@Slf4j
public class BeanUtils {

    public static <S, T> List<T> convert(List<S> sources, Class<T> targetClass) {
        if (CollectionUtil.isEmpty(sources)) {
            return new ArrayList<>();
        }
        List<T> results = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target;
            try {
                target = targetClass.newInstance();
            } catch (Exception e) {
                log.error("Could not new instance for '" + targetClass.getSimpleName() + "'", e);
                break;
            }
            copyProperties(source, target, true, (String[]) null);
            results.add(target);
        }
        return results;
    }

    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, true, (String[]) null);
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        copyProperties(source, target, true, ignoreProperties);
    }

    public static void copyProperties(Object source, Object target, boolean ignoreNullValue, String... ignoreProperties) {
        if (source == null || target == null) {
            log.error("Source or Target must not be null");
            return;
        }
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (ignoreNullValue && value == null) {
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Exception e) {
                            log.error("Could not copy property '" + targetPd.getName() + "' from source to target", e);
                        }
                    }
                }
            }
        }
    }
}
