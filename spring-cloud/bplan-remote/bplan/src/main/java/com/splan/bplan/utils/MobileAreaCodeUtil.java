//package com.splan.bplan.utils;
//
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.splan.base.bean.CommonMobileAreaBean;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.Set;
//
//public class MobileAreaCodeUtil {
//    public static List<CommonMobileAreaBean> getAreaCode() {
//        Set<String> regions =  PhoneNumberUtil.getInstance().getSupportedRegions();
//        List<CommonMobileAreaBean> list = new ArrayList<>();
//        regions.forEach(region -> {
//            CommonMobileAreaBean area = new CommonMobileAreaBean();
//            Locale locale = new Locale("", region);
//            area.setAreaName(locale.getDisplayCountry());
//            area.setAreaCode(Integer.toString(PhoneNumberUtil.getInstance().getCountryCodeForRegion(region)));
//            if (StringUtils.equals(area.getAreaCode(), "86")) {
//                area.setAreaOrder(1);
//            } else {
//                area.setAreaOrder(2);
//            }
//            list.add(area);
//        });
//        return list;
//    }
//
//    public static void main(String[] args) {
//        getAreaCode();
//    }
//}
