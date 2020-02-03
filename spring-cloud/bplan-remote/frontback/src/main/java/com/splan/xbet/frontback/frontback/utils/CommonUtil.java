package com.splan.xbet.frontback.frontback.utils;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.enums.ErrorEnum;
import com.splan.xbet.frontback.frontback.contantes.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * 返回一个info为空对象的成功消息的json
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * 返回一个返回码为100的json
     */
    public static JSONObject successJson(Object info) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", Constants.SUCCESS_CODE);
        resultJson.put("msg", Constants.SUCCESS_MSG);
        resultJson.put("info", info);
        return resultJson;
    }

    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorEnum.getErrorCode());
        resultJson.put("msg", errorEnum.getErrorMsg());
        resultJson.put("info", new JSONObject());
        return resultJson;
    }

    /**
     * 查询分页结果后的封装工具方法
     *
     * @param requestJson 请求参数json,此json在之前调用fillPageParam 方法时,已经将pageRow放入
     * @param list        查询分页对象list
     * @param totalCount  查询出记录的总条数
     */
    public static JSONObject successPage(final JSONObject requestJson, List<JSONObject> list, int totalCount) {
        int pageRow = requestJson.getIntValue("pageRow");
        int totalPage = getPageCounts(pageRow, totalCount);
        JSONObject result = successJson();
        JSONObject info = new JSONObject();
        info.put("list", list);
        info.put("totalCount", totalCount);
        info.put("totalPage", totalPage);
        result.put("info", info);
        return result;
    }

    /**
     * 查询分页结果后的封装工具方法
     *
     * @param list 查询分页对象list
     */
    public static JSONObject successPage(List<JSONObject> list) {
        JSONObject result = successJson();
        JSONObject info = new JSONObject();
        info.put("list", list);
        result.put("info", info);
        return result;
    }

    /**
     * 获取总页数
     *
     * @param pageRow   每页行数
     * @param itemCount 结果的总条数
     */
    private static int getPageCounts(int pageRow, int itemCount) {
        if (itemCount == 0) {
            return 1;
        }
        return itemCount % pageRow > 0 ?
                itemCount / pageRow + 1 :
                itemCount / pageRow;
    }

    /**
     * 将request参数值转为json
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
//        if (!StringTools.isNullOrEmpty(requiredColumns)) {
//            //验证字段非空
//            String[] columns = requiredColumns.split(",");
//            String missCol = "";
//            for (String column : columns) {
//                Object val = jsonObject.get(column.trim());
//                if (StringTools.isNullOrEmpty(val)) {
//                    missCol += column + "  ";
//                }
//            }
//            if (!StringTools.isNullOrEmpty(missCol)) {
//                jsonObject.clear();
//                jsonObject.put("code", ErrorEnum.E_90003.getErrorCode());
//                jsonObject.put("msg", "缺少必填参数:" + missCol.trim());
//                jsonObject.put("info", new JSONObject());
//                throw new CommonJsonException(jsonObject);
//            }
//        }
    }

    /**
     * 在分页查询之前,为查询条件里加上分页参数
     *
     * @param paramObject    查询条件json
     * @param defaultPageRow 默认的每页条数,即前端不传pageRow参数时的每页条数
     */
    private static void fillPageParam(final JSONObject paramObject, int defaultPageRow) {
        int pageNum = paramObject.getIntValue("pageNum");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("pageRow");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet", (pageNum - 1) * pageRow);
        paramObject.put("pageRow", pageRow);
        paramObject.put("pageNum", pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }

    /**
     * 分页查询之前的处理参数
     * 没有传pageRow参数时,默认每页10条.
     */
    public static void fillPageParam(final JSONObject paramObject) {
        fillPageParam(paramObject, 10);
    }

    /**
     *将未传值的属性均设置为null
     */
    public  static Object setNoParamToNull(Object obj){
       Class cla=obj.getClass();
       Field[] fields=cla.getDeclaredFields();
       try {
           for (Field field : fields) {
               field.setAccessible(true);
               if (field.get(obj)!=null&&field.get(obj) instanceof String &&"".equals((String)field.get(obj))) {
                    field.set(obj,null);
               }
           }
           return obj;
       }catch (Exception e){
           e.printStackTrace();
           return obj;
       }
    }

    /**
     * 处理特殊字符
     * @param s
     * @return
     */
    public static String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            switch (c){
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean checklist(List<String> stringList){
        Pattern pattern=Pattern.compile("[0-9]*");
        for(String str:stringList){
            Matcher matcher=pattern.matcher(str);
            if(!matcher.matches())
                return false;
        }
        return true;
    }

    public static Object getTotle(Object param,Object result){
        List<Object> objs=null;
        if(param instanceof List){
            objs=(List)param;
        }
     try {
         Field[] fieldsresult = result.getClass().getDeclaredFields();
         Map<String, BigDecimal> map = new HashMap<>();
         for (Field field : fieldsresult) {
             map.put(field.getName(), new BigDecimal(0));
         }
         Pattern pattern = Pattern.compile("[-]*[0-9]+[\\.]?[0-9]*");
         Pattern pattern2=Pattern.compile("[\\w]*((balance)|(Balance))+[\\w]*");
         for (Object obj : objs) {
             Field[] fields = obj.getClass().getDeclaredFields();
             for (Field field : fields) {
                 String val="";
                 if(field.get(obj)!=null) val = (String) field.get(obj);
                 Matcher matcher = pattern.matcher(val);
                 if (!matcher.matches()) continue;
                 BigDecimal bigDecimal1 = new BigDecimal(val);
                 if (map.containsKey(field.getName())) {
                    map.put(field.getName(),map.get(field.getName()).add(bigDecimal1).setScale(2,BigDecimal.ROUND_HALF_UP));
                 }
             }
         }

         for (Field field : fieldsresult) {
//             排除特殊选项
             Matcher matcher2=pattern2.matcher(field.getName());
             if(matcher2.matches()){
                field.set(result,"/");
                continue;
             }
             field.set(result, map.get(field.getName())+"");
         }
     }catch (Exception e){
         e.printStackTrace();
     }
        return result;
    }

    public  static  boolean checkPhoneNum(String phoneNum){
        if(StringUtils.isBlank(phoneNum))return false;
        Pattern pattern=Pattern.compile("[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$");
        Matcher matcher=pattern.matcher(phoneNum);
        return matcher.matches();
    }

    public  static  boolean checkNum(String num){
        if(StringUtils.isBlank(num))return false;
        Pattern pattern=Pattern.compile("[0-9]+");
        Matcher matcher=pattern.matcher(num);
        return matcher.matches();
    }

    public  static boolean checkImage(String suff){
        Pattern pattern=Pattern.compile("((.gif)|(.jpeg)|(.png)|(.jpg)|(.bmp))*");
        Matcher matcher=pattern.matcher(suff);
        return matcher.matches();
    }

//    public static boolean needHide(){
//        Session session = SecurityUtils.getSubject().getSession();
//        JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
//        Collection<String> permissionList=(Collection<String>) permission.get("permissionList");
//        if(!permissionList.contains(StormContants.STORM_SHOW)){
//            return true;
//        }
//        return false;
//    }

    public  static String hide(String realNameOrmobile){
        if(StringUtils.isBlank(realNameOrmobile))return "";
        if(checkPhoneNum(realNameOrmobile))return realNameOrmobile.substring(0,3)+"****"+realNameOrmobile.substring(7);
        else return realNameOrmobile.substring(0,1)+"**";
    }

    public  static boolean checkNumForUpScore(String num){
        Pattern pattern=Pattern.compile("[-]?[0-9]+[\\.]?[0-9]{0,2}");
        Matcher matcher=pattern.matcher(num);
        Pattern pattern2=Pattern.compile("[-]?[0-9]+[\\.]?[0-9]{1,2}");
        if(matcher.matches()){
            if(num.contains(".")){
                Matcher matcher2=pattern2.matcher(num);
                return matcher2.matches();
            }else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        Pattern pattern=Pattern.compile("[0-9]+[\\.]?[0-9]*");
//        Matcher matcher=pattern.matcher("1-12343");
//        System.out.println(matcher.matches());
//        String integer="1";
//        System.out.println(Double.parseDouble(integer));

//        Map<String,BigDecimal> map=new HashMap<>();
//        map.put("zwb",new BigDecimal(0));
//        map.put("zwb", map.get("zwb").add(new BigDecimal(1)));
//        System.out.println(map.get("zwb"));
//        String num="1.1";
//        System.out.println(checkNumForUpScore(num));

        System.out.println(checkNotChinese("dioaa!"));
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public  static boolean checkNotChinese(String content){
        Pattern pattern=Pattern.compile("[^\\u4e00-\\u9fa5]*");
        Matcher matcher=pattern.matcher(content);
        return matcher.matches();
    }

    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static Object changeParamtoBean(Object obj1,Object obj2){
        Class clazz;
        List<Field> field1=new ArrayList<>();
        clazz = obj1.getClass();
        while (clazz != null){
            field1.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Map<String,Object> map=new HashMap<>();
        for(Field field:field1){
            field.setAccessible(true);
            try {
                if(field.get(obj1)==null || (field.get(obj1) instanceof String && StringUtils.isBlank(field.get(obj1).toString())))continue;
                map.put(field.getName(),field.get(obj1));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        List<Field> field2=new ArrayList<>();
        clazz = obj2.getClass();
        while (clazz != null){
            field2.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for(Field field:field2){
            if(map.containsKey(field.getName())){
                field.setAccessible(true);
                try {
                    if(field.getType().toString().contains("Date") && obj1.getClass().getName().contains("Param")){
                        try {
                            field.set(obj2,simpleDateFormat.parse(map.get(field.getName()).toString()));
                        } catch (ParseException e) {
                            field.set(obj2,new Date());
                        }
                    }else {
                        field.set(obj2, map.get(field.getName()));
                    }
                } catch (IllegalAccessException e) {

                }
            }
        }
        return obj2;
    }

    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
//             System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

    public static <T> T chgParamtoBean(Map<String, Object> requestParam, T obj){
        Class clazz;
        List<Field> field2=new ArrayList<>();
        clazz = obj.getClass();
        while (clazz != null){
            field2.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        for(Field f:field2){
            if(requestParam.containsKey(f.getName())){
                f.setAccessible(true);
//                if(f.getType().getName().contains("String")){
                try {
                    f.set(obj,requestParam.get(f.getName()));
                }catch (Exception e){

                }
//                }
            }
        }
        return obj;
    }

}
