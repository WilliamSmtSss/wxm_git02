package com.splan.bplan.service;

import com.splan.bplan.dto.DateDto;
import com.splan.bplan.dto.ScreenForBetOrderDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IexportService{
    void getAllBetTopicForExport(HttpServletRequest req, HttpServletResponse res, Integer gameTypeId, DateDto dateDto,String status,String hasOrder) ;

    void getAllUserForExport(HttpServletRequest req, HttpServletResponse res,String userId,String apiid) ;

    void getAllOrderForExport(HttpServletRequest req, HttpServletResponse res, String userId,String apiid, ScreenForBetOrderDto screenForBetOrderDto) ;

    void getAllUserDetailExport(HttpServletRequest req, HttpServletResponse res,Long userId, Integer gameTypeId, DateDto dateDto) ;

    void getAllBetDetailForExport(HttpServletRequest req, HttpServletResponse res,Long betId, Long userId,Long option) ;

    void getAllRevenueForExport(HttpServletRequest req, HttpServletResponse res,DateDto dateDto,String apiid);
}
