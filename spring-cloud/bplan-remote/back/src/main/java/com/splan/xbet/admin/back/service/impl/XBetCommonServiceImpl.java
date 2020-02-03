package com.splan.xbet.admin.back.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.constants.Constants;
import com.splan.xbet.admin.back.constants.Consts;
import com.splan.xbet.admin.back.enums.BusRole;
import com.splan.xbet.admin.back.mappers.*;
import com.splan.xbet.admin.back.service.XBetCommonService;
import com.splan.xbet.admin.back.utils.CommonUtil;
import com.splan.xbet.admin.back.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

@Service
@Slf4j
public class XBetCommonServiceImpl implements XBetCommonService {

    @Autowired
    private BusinessCurrencyConfigBeanMapper businessCurrencyConfigBeanMapper;

    @Autowired
    private XbetBackMapper xbetBackMapper;

    @Autowired
    private BusinessConfigBeanMapper businessConfigBeanMapper;

    @Autowired
    private GameTypeBeanMapper gameTypeBeanMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private XExportMapper xExportMapper;

    @Override
    public CommonResult<String> upload(MultipartFile file, String type) {
        try{
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String prefix=fileName.substring(fileName.lastIndexOf("."));
            if(!CommonUtil.checkImage(prefix))
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

            // 用uuid作为文件名，防止生成的临时文件重复
            final File excelFile = File.createTempFile(UUID.randomUUID().toString(),prefix);
            // MultipartFile to File
            file.transferTo(excelFile);

            BufferedImage sourceImg = ImageIO.read(new FileInputStream(excelFile));

            if(sourceImg.getWidth()!=300 || sourceImg.getHeight()!=80)
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

            String returnUrl=uploadToS3(excelFile,"");
            //程序结束时，删除临时文件
            deleteFile(excelFile);
            return ResultUtil.returnSuccess(returnUrl);
        }catch (Exception e){
            log.info("upload fail======================");
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
    }

    private String uploadToS3(File tempFile, String remoteFileName) throws IOException {
//        PropertiesUtil propertiesUtil = new PropertiesUtil("s3.properties");
        //首先创建一个s3的客户端操作对象（需要amazon提供的密钥）
        AmazonS3 s3 = new AmazonS3Client(
                new BasicAWSCredentials(Consts.getAccessKey(),
                        Consts.getSecretKey()));
//        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
//        s3.setRegion(usWest2);
        //设置bucket,key
        String suffix=tempFile.getName().substring(tempFile.getName().indexOf("."));
        String bucketName = Consts.getBucketName();
        String key ="files/"+UUID.randomUUID() + suffix;
        try {
            //验证名称为bucketName的bucket是否存在，不存在则创建
            if (!checkBucketExists(s3, bucketName)) {
                s3.createBucket(bucketName);
            }
            //上传文件
            s3.putObject(new PutObjectRequest(bucketName, key, tempFile));
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
            //获取一个request
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
                    bucketName, key);
//            Date expirationDate = null;
            try {
//                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-31");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置过期时间
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000L * 60 * 60 * 24 * 7;
            expiration.setTime(expTimeMillis);
            urlRequest.setExpiration(expiration);
            //生成公用的url
            URL url = s3.generatePresignedUrl(urlRequest);
            String urlStr="";
            urlStr=url.toString().substring(0,url.toString().lastIndexOf("?"));
//            urlStr=url.toString();
//            urlStr=((AmazonS3Client) s3).getResourceUrl(Consts.getBucketName(),Consts.getAccessKey());
            System.out.println("=========URL=================" + urlStr + "============URL=============");
            System.out.println("=========URL=================" + url.toString() + "============URL=============");
//            if (url == null) {
//                throw new OperateFailureException("can't get s3 file url!");
//            }
            return urlStr;
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            log.info("====================================AWS S3 UPLOAD ERROR START======================================");
            log.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            log.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            log.info(ase.getMessage(), ase);
            log.info("====================================AWS S3 UPLOAD ERROR END======================================");
        } catch (AmazonClientException ace) {
            log.info("====================================AWS S3 UPLOAD ERROR START======================================");
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
            log.info("====================================AWS S3 UPLOAD ERROR END======================================");
        }
        return "";
    }

    @Override
    public CommonResult<List<BusinessCurrencyConfigBean>> currencyList() {
        return ResultUtil.returnSuccess(businessCurrencyConfigBeanMapper.selectList(new QueryWrapper<>()));
    }

    @Override
    public CommonResult<List<SysUser>> assignAdmin(String apiId) {
        List<SysUser> list=xbetBackMapper.getBusAdmins(apiId);
        return ResultUtil.returnSuccess(list);
    }

    @Override
    public CommonResult<List<String>> businessList(SysUser sysUser, String queryType,String bigBusiness) {
        if(StringUtils.isBlank(queryType))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        List<String> result=new ArrayList<>();

         if("0".equals(queryType)){
             if(StringUtils.isNotBlank(sysUser.getApiid())){
                 if(sysUser.getRoleId()==BusRole.BUS.geteName())
                    result.add(sysUser.getApiid());
             }else {
                 List<BusinessConfigBean> list = xbetBackMapper.getBusConfig(null, queryType);
                 for (BusinessConfigBean x : list) {
                     result.add(x.getApiId());
                 }
             }
        }else if("1".equals(queryType)){
            if(sysUser.getRoleId()==BusRole.BUS_DOWN.geteName()){
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            }else if(sysUser.getRoleId()==BusRole.BUS.geteName()){
                BusinessConfigBean businessConfigBean=businessConfigBeanMapper.selectOne(new QueryWrapper<BusinessConfigBean>().eq("api_id",sysUser.getApiid()));
                List<BusinessConfigBean> list=xbetBackMapper.getBusConfig(businessConfigBean.getId()+"",queryType);
                for(BusinessConfigBean x:list){
                    result.add(x.getApiId());
                }
            }else{
                BusinessConfigBean businessConfigBean=null;
                if(StringUtils.isNotBlank(bigBusiness))
                    businessConfigBean=businessConfigBeanMapper.selectOne(new QueryWrapper<BusinessConfigBean>().eq("api_id",bigBusiness));
                String pid=null;
                if(null!=businessConfigBean)
                    pid=businessConfigBean.getId()+"";

                List<BusinessConfigBean> list=xbetBackMapper.getBusConfig(pid,queryType);
                for(BusinessConfigBean x:list){
                    result.add(x.getApiId());
                }
            }
        }
        return ResultUtil.returnSuccess(result);
    }

    @Override
    public CommonResult<List<GameTypeBean>> gameType() {
        return ResultUtil.returnSuccess(gameTypeBeanMapper.selectGameTypeList());
    }

    @Override
    public CommonResult getExcelTemp(SysUser sysUser) {
        String excel=(String) redisTemplate.opsForValue().get(Constants.EXCEL_TEMP+":"+sysUser.getUsername());
        return ResultUtil.returnSuccess(excel);
    }

    @Override
    public CommonResult<IPage<XExportBean>> getExportTempFiles(SysUser sysUser, Page page) {
        List<XExportBean> list=xExportMapper.getList(page,sysUser.getId());
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    public static boolean checkBucketExists (AmazonS3 s3, String bucketName) {
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket bucket : buckets) {
            if (Objects.equals(bucket.getName(), bucketName)) {
                return true;
            }
        }
        return false;
    }

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


}
