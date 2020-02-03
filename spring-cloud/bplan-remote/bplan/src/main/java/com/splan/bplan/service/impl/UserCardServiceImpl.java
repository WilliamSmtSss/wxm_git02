package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.CommonBankBean;
import com.splan.base.bean.UserCardBean;
import com.splan.bplan.dto.UserCardDto;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.Status;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.UserCardBeanMapper;
import com.splan.bplan.service.IBankService;
import com.splan.bplan.service.IUserCardService;
import com.splan.bplan.utils.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyn on 2019/1/18.
 */
@Service
public class UserCardServiceImpl extends ServiceImpl<UserCardBeanMapper, UserCardBean> implements IUserCardService {

    @Autowired
    private UserCardBeanMapper userCardBeanMapper;

    @Autowired
    private IBankService bankService;

    @Override
    public CommonResult<String> addCard(UserCardDto userCardDto) {
        UserCardBean userCardBean = new UserCardBean();
        BeanUtils.copyProperties(userCardDto, userCardBean);
        CommonBankBean bank = bankService.getById(userCardDto.getBankId());
        userCardBean.setBankCode(bank.getBankCode());
        userCardBean.setBankName(bank.getBankName());
        userCardBean.setBankIcon(bank.getBankIcon());
        if (hasCard(userCardBean)) {
            return ResultUtil.returnError(ResultStatus.USER_CARD_IS_EXISTS);
        }
        userCardBean.setStatus(Status.ENABLE);
        save(userCardBean);
        return ResultUtil.returnSuccess(null);
    }

    @Override
    public List<UserCardBean> getList(Long userId) {
        QueryWrapper<UserCardBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    private boolean hasCard(UserCardBean userCardBean) {
        int count = userCardBeanMapper.selectCountByUserIdAndCreditCard(userCardBean.getUserId(), userCardBean.getCreditCard());
        return count > 0;
    }
}
