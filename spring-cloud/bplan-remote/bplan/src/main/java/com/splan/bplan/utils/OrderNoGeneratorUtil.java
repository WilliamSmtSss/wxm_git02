package com.splan.bplan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.splan.bplan.dto.BetOrderDto;
import com.splan.bplan.filter.BigDecimalValueFilter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 订单号生成
 */
public class OrderNoGeneratorUtil {

    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%010d", hashCodeV);
    }


    public static String getOrderNoByTime(String pre){
        //格式化当前时间
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        //得到17位时间如：20170411094039080
        //System.out.println("时间17位：" + strDate);
        //为了防止高并发重复,再获取3个随机数
        String random = getRandom620(3);

        //最后得到20位订单编号。
        //System.out.println("订单号20位：" + strDate + random);
        return pre + strDate + random;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            System.out.println(getOrderNoByTime("BO"));
//        }

        BetOrderDto betOrderDto = new BetOrderDto();
        betOrderDto.setBetOptionId(1);
        betOrderDto.setAmount(10);
        betOrderDto.setOdd(new BigDecimal(1.22));
        betOrderDto.setTenantCustomerNo(15l);
        betOrderDto.setTenantOrderNo(OrderNoGeneratorUtil.getOrderNoByTime("STORM"));
        SerializeConfig config = new SerializeConfig();

        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String data = JSON.toJSONString(betOrderDto,config,new BigDecimalValueFilter());
        System.out.println(data);

    }

    /**
     * 获取6-10 的随机位数数字
     * @param length	想要生成的长度
     * @return result
     */
    public static String getRandom620(Integer length) {
        String result = "";
        Random rand = new Random();
        int n = 20;
        if (null != length && length > 0) {
            n = length;
        }
        int randInt = 0;
        for (int i = 0; i < n; i++) {
            randInt = rand.nextInt(10);
            result += randInt;
        }
        return result;
    }
}
