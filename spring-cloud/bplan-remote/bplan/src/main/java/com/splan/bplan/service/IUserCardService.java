package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.UserCardBean;
import com.splan.bplan.dto.UserCardDto;
import com.splan.bplan.http.CommonResult;

import java.util.List;

/**
 * Created by lyn on 2019/1/18.
 */
public interface IUserCardService extends IService<UserCardBean> {
    CommonResult<String> addCard(UserCardDto userCardDto);
    List<UserCardBean> getList(Long userId);
}
