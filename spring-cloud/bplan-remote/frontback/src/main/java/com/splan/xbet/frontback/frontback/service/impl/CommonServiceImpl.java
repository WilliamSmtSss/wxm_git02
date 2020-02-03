package com.splan.xbet.frontback.frontback.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.contantes.Consts;
import com.splan.xbet.frontback.frontback.service.CommonService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Override
    public CommonResult<String> upload(MultipartFile file) {
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

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
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

}
