package com.splan.xbet.admin.back.utils.export;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.bean.XExportBean;
import com.splan.xbet.admin.back.config.Myconfig;
import com.splan.xbet.admin.back.constants.Constants;
import com.splan.xbet.admin.back.mappers.BetOrderDetailBeanMapper;
import com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper;
import com.splan.xbet.admin.back.mappers.XExportMapper;
import com.splan.xbet.admin.back.netty.utils.SpringUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 *功能描述：导出报表
 */
@Component
public class ReportExcel {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private XExportMapper xExportMapper;

    /**
     * 功能: Excel导出公共方法
     * 记录条数大于50000时 导出.xlsx文件(excel07+)  小于等于50000时导出 .xls文件(excel97-03)
     * 开发：wangkecheng
     * @param list            需要导出的列表数据
     * @param title            导出文件的标题
     * @param className        导出对象的类名
     * @param exportType    针对同一个pojo可能有多个不同的导出模板时,可以通过此属性来决定导出哪一套模板，默认第一套
     * @param response         用来获取输出流
     * @param request       针对火狐浏览器导出时文件名乱码的问题,也可以不传入此值
     * @throws IOException
     */
    public void excelExport(List list, String title, Class className, Integer exportType, HttpServletResponse response, HttpServletRequest request) {
        OutputStream out=null;
        try {
            response.reset();
            out = response.getOutputStream();
            ExcelUtil excel = new ExcelUtil();
            if(list!=null && list.size()>ExcelUtil.EXPORT_07_LEAST_SIZE){
                dealBigNumber(list, title, className, exportType, response, request, out, excel);
            }else{
                HSSFWorkbook hss = new HSSFWorkbook();
                if(exportType==null){
                    hss = excel.exportExcel(list,title,className,0);
                }else{
                    hss = excel.exportExcel(list, title, className, exportType);
                }
                String disposition = "attachment;filename=";
                if(request!=null&&request.getHeader("USER-AGENT")!=null&& StringUtils.contains(request.getHeader("USER-AGENT"), "Firefox")){
                    disposition += new String((title+".xls").getBytes(),"ISO8859-1");
                }else{
                    disposition += URLEncoder.encode(title+".xls", "UTF-8");
                }

                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-disposition", disposition);
                hss.write(out);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(out!=null)
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void excelExport2(List list, String title, Class className, Integer exportType) {
//        OutputStream out=null;
        try {
//            response.reset();
//            out = response.getOutputStream();
            ExcelUtil excel = new ExcelUtil();

                HSSFWorkbook hss = new HSSFWorkbook();
                if(exportType==null){
                    hss = excel.exportExcel(list,title,className,0);
                }else{
                    hss = excel.exportExcel(list, title, className, exportType);
                }
//            File tempFile=File.createTempFile("temp",".xls");
            String fileName=UUID.randomUUID()+".xls";
            File dir=new File(Myconfig.getProfile());
            if(!dir.exists())
                dir.mkdir();
            File tempFile=new File(Myconfig.getProfile()+fileName);
            tempFile.deleteOnExit();
            OutputStream tempout = new FileOutputStream(tempFile);
            hss.write(tempout);
            JSONObject loginInfo=(JSONObject) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
//            if (redisTemplate == null) {
//                //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
//                redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
//                RedisSerializer redisSerializer=new StringRedisSerializer();
//                redisTemplate.setStringSerializer(redisSerializer);
//            }
//            redisTemplate.opsForValue().set(Constants.EXCEL_TEMP+":"+loginInfo.getString("username"),"/excelTemp/"+fileName);
            XExportBean xExportBean=new XExportBean();
            xExportBean.setSysId(loginInfo.getInteger("userId"));
            xExportBean.setFileName(title);
            xExportBean.setUrl("/back/excelTemp/"+fileName);
            xExportMapper.insert(xExportBean);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

        }
    }

    private void dealBigNumber(List list, String title, Class className, Integer exportType,
                               HttpServletResponse response, HttpServletRequest request, OutputStream out, ExcelUtil excel)
            throws Exception{
        SXSSFWorkbook hss;
        if(exportType==null){
            hss = excel.exportExcel07S(list,title,className,0);
        }else{
            hss = excel.exportExcel07S(list, title, className, exportType);
        }

        String disposition = "attachment;filename=";
        if(request!=null && request.getHeader("USER-AGENT") != null && StringUtils.contains(request.getHeader("USER-AGENT"), "Firefox")){
            disposition += new String((title+".xlsx").getBytes(),"ISO8859-1");
        }else{
            disposition += URLEncoder.encode(title+".xlsx", "UTF-8");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setHeader("Content-disposition", disposition);
        hss.write(out);
    }


}