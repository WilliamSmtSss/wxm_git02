package com.splan.bplan.utils;

import com.splan.base.bean.BetOrderBean;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionTools {

    public static <T> LinkedHashMap<String, List<T>> classify(List<T> list, String keyName)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        LinkedHashMap<String, List<T>> target = new LinkedHashMap();
        for (Object obj : list) {
            // 取得bean需要归类的属性（keyName）的值，不做类型转换
            Object oKeyValue = PropertyUtils.getProperty(obj, keyName);
            String keyValue = String.valueOf(oKeyValue);
            if (!target.containsKey(keyValue)) {
                // 如果map中没有归类key值，则添加key值和相应的list
                ArrayList keyList = new ArrayList();
                keyList.add(obj);
                target.put(keyValue, keyList);
            } else {
                // 如果有归类key值，则在相应的list中添加这个bean
                ArrayList keyList = (ArrayList) target.get(keyValue);
                keyList.add(obj);
            }
        }
        return target;
    }

    private static LinkedHashMap<String, Map> classify(Map<String, List> mocl, int index,
                                                       String... keyNames) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        // 单步理解：target是函数参数Map<String, List> mocl再次归类成的LinkedHashMap<String,Map>
        // 递归到最后这个是最终归类好的map
        LinkedHashMap<String, Map> target = new LinkedHashMap();
        // 控制递归条件，起始的index应该总是1。
        if (index < keyNames.length) {
            // swap用来保存参数index的值，这是最容易出错的一个地方
            // 用它保证：在参数Map<String, List> mocl层面循环时用相同的index参数值。
            int swap = index;
            for (Map.Entry<String, List> entry : mocl.entrySet()) {
                String moclKey = entry.getKey();
                List moclList = entry.getValue();
                // 将List<bean>再次归类
                LinkedHashMap<String, List> mocl1 = classify(moclList, keyNames[index]);
                // 如果index达到了数组的最后一个，一定是List<bean>转map，递归结束
                if (index == keyNames.length - 1) {
                    target.put(moclKey, mocl1);
                } else {
                    // 将List<bean>转map得到的_mocl，再次归类
                    // _mocm 为map of classified map的简称
                    LinkedHashMap<String, Map> mocm = classify(mocl1, ++index, keyNames);
                    target.put(moclKey, mocm);
                }
                index = swap;
            }
        }
        return target;
    }


    public static <T> LinkedHashMap<String,List<T>> classifyList(List<T> list, String... keyNames)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (keyNames == null || keyNames.length == 0) {
            return null;
        }
        //if (keyNames.length == 1) {
            return classify(list, keyNames[0]);
        /*}/*else{
            return classify(classify(list, keyNames[0]), 1, keyNames);
        }*/
    }

    public static void main(String[] args) {
        List<BetOrderBean> list = new ArrayList<>();
        BetOrderBean betOrderBean = new BetOrderBean();
        betOrderBean.setApiId("1111");
        betOrderBean.setId(1l);
        BetOrderBean betOrderBean2 = new BetOrderBean();
        betOrderBean2.setApiId("1111");
        betOrderBean2.setId(2l);
        BetOrderBean betOrderBean3 = new BetOrderBean();
        betOrderBean3.setApiId("22");
        betOrderBean3.setId(3l);
        BetOrderBean betOrderBean4 = new BetOrderBean();
        betOrderBean4.setApiId("123");
        betOrderBean4.setId(4l);
        list.add(betOrderBean);
        list.add(betOrderBean2);
        list.add(betOrderBean3);
        list.add(betOrderBean4);


        try {
            Map<String,List<BetOrderBean>> map = classify(list,"apiId");
            System.out.println(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


}
