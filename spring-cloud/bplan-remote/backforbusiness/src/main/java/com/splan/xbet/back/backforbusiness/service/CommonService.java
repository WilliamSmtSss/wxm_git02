package com.splan.xbet.back.backforbusiness.service;

import com.splan.base.http.CommonResult;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    CommonResult<String> upload(MultipartFile file);

}
