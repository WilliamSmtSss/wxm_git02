package com.splan.bplan.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

public class BeanUtil {

    public static <T> List copyList(List<T> list,Class target) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), target);
    }

    public static Map<String, Object> copyMap(Map map) {
        return JSON.parseObject(JSON.toJSONString(map));
    }


    /**
     * @param target  目标类
     * @param source  原类
     * @param objects 对象集合
     */
    public static void copyProperties(Object target, Object source, Object... objects) {
        try {
            Class clazz1 = source.getClass();
            Class clazz2 = target.getClass();
            Field[] fields1 = clazz1.getDeclaredFields();
            Field[] fields2 = clazz2.getDeclaredFields();
            for (Field field1 : fields1) {
                String name1 = field1.getName();
                Method m = clazz1.getMethod("get" + toUpcase(name1));
                for (Field field2 : fields2) {
                    String name2 = field2.getName();
                    if (name1.equals(name2)) {
                        field2.setAccessible(true);
                        if (m.invoke(source) != null) {
                            field2.set(target, m.invoke(source));
                        }
                    }
                    //判断objects
                    if (objects.length > 0) {
                        HashMap<String, Object> paramMap = (HashMap<String, Object>) objects[0];
                        if (paramMap.get(name2) != null) {
                            field2.setAccessible(true);
                            field2.set(target, paramMap.get(name2));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param target  目标类
     * @param map  原类
     */
    public static void copyMapProperties(Object target, Map<String,Object> map) {
        try {
            Class clazz2 = target.getClass();
            Field[] fields2 = clazz2.getDeclaredFields();
            for (String key : map.keySet()) {
                String name1 = key;
                Object value =(Object) map.get(key);
                for (Field field2 : fields2) {
                    String name2 = field2.getName();
                    if (name1.toUpperCase().equals(name2.toUpperCase())) {
                        field2.setAccessible(true);
                        if (value != null) {
                            field2.set(target, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 如果某个参数为固定值，则可以加以下对象objects，如：
     * paramMap.put("userId", "aaaaaaaaaaa");
     * paramMap.put("num", 777777777);固定值
     *
     * @param list    源list
     * @param target  目标类CLASS
     * @param objects 对象集合
     * @return list
     */
    public static List<?> copyPropertiesList(List<?> list, Class target, Object... objects) {
        List<Object> result = new ArrayList();
        if (list != null) {
            for (Object o : list) {
                try {
                    Object d = target.newInstance();
                    copyProperties(d, o, objects);
                    result.add(d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /***
     * 此方法为jdbc中调用指生成结果的方法,其他类型可以继续添加....
     * @param clazz 类
     * @param rs    结果集合
     * @return object
     * @date 2015/11/19
     */
    public static Object getBeanForRs(Class clazz, ResultSet rs) {
        try {
            Object target = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                field.setAccessible(true);
                Class type = field.getType();
                try {
                    if (type.equals(String.class)) {
                        field.set(target, rs.getString(name));
                    } else if (type.equals(Integer.class)) {
                        field.set(target, rs.getInt(name));
                    } else if (type.equals(Long.class)) {
                        field.set(target, rs.getLong(name));
                    } else if (type.equals(Float.class)) {
                        field.set(target, rs.getFloat(name));
                    } else if (type.equals(BigDecimal.class)) {
                        field.set(target, rs.getBigDecimal(name));
                    } else if(type.equals(UUID.class)){
                        field.set(target, UUID.fromString(rs.getString(name)));
                    } else if(type.equals(Date.class)){
                        field.set(target,rs.getDate(name));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 首字母转大写
     *
     * @param s 字符串
     * @return stirng
     * update wmk
     */
    private static String toUpcase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
    }


    public static <E> List<E> deepCopy(List<E> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<E> dest = (List<E>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<E>();
        }
    }

}
