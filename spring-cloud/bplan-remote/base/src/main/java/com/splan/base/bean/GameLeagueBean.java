package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * game_league
 */
@TableName(value = "game_league")
public class GameLeagueBean extends BaseBean implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    protected Integer id;
    /**
     * 
     */
    @TableField(value = "icon_url")
    private String iconUrl;

    /**
     * 联赛id
     */
    @TableField(value = "league_id")
    private Integer leagueId;

    /**
     * 详情页图片
     */
    @TableField(value = "detail_url")
    private String detailUrl;

    /**
     * 0未开始 1进行中 2已结束
     */
    @TableField(value = "status")
    private String status;

    /**
     * 赛区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 赛事名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 联赛详情
     */
    @TableField(value = "game_detail")
    private String gameDetail;

    /**
     * 赛事别名
     */
    @TableField(value = "game_alias")
    private String gameAlias;

    /**
     * 0正常 1禁用
     */
    @TableField(value = "able_status")
    private String ableStatus;

    /**
     * 游戏类别
     */
    @TableField(value = "game_id")
    private Integer gameId;

    /**
     * 0显示 1不显示
     */
    @TableField(value = "hidden")
    private String hidden;

    /**
     * 联赛的等级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 热门等级
     */
    @TableField(value = "hot")
    private Integer hot;

    /**
     * 
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 
     */
    @TableField(value = "progress_state")
    private String progressState;

    /**
     * 
     */
    @TableField(value = "place")
    private String place;

    /**
     * 
     * @return icon_url 
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 
     * @param iconUrl 
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    /**
     * 联赛id
     * @return league_id 联赛id
     */
    public Integer getLeagueId() {
        return leagueId;
    }

    /**
     * 联赛id
     * @param leagueId 联赛id
     */
    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    /**
     * 详情页图片
     * @return detail_url 详情页图片
     */
    public String getDetailUrl() {
        return detailUrl;
    }

    /**
     * 详情页图片
     * @param detailUrl 详情页图片
     */
    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
    }

    /**
     * 0未开始 1进行中 2已结束
     * @return status 0未开始 1进行中 2已结束
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0未开始 1进行中 2已结束
     * @param status 0未开始 1进行中 2已结束
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 赛区
     * @return region 赛区
     */
    public String getRegion() {
        return region;
    }

    /**
     * 赛区
     * @param region 赛区
     */
    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    /**
     * 赛事名称
     * @return name 赛事名称
     */
    public String getName() {
        return name;
    }

    /**
     * 赛事名称
     * @param name 赛事名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 联赛详情
     * @return game_detail 联赛详情
     */
    public String getGameDetail() {
        return gameDetail;
    }

    /**
     * 联赛详情
     * @param gameDetail 联赛详情
     */
    public void setGameDetail(String gameDetail) {
        this.gameDetail = gameDetail == null ? null : gameDetail.trim();
    }

    /**
     * 赛事别名
     * @return game_alias 赛事别名
     */
    public String getGameAlias() {
        return gameAlias;
    }

    /**
     * 赛事别名
     * @param gameAlias 赛事别名
     */
    public void setGameAlias(String gameAlias) {
        this.gameAlias = gameAlias == null ? null : gameAlias.trim();
    }

    /**
     * 0正常 1禁用
     * @return able_status 0正常 1禁用
     */
    public String getAbleStatus() {
        return ableStatus;
    }

    /**
     * 0正常 1禁用
     * @param ableStatus 0正常 1禁用
     */
    public void setAbleStatus(String ableStatus) {
        this.ableStatus = ableStatus == null ? null : ableStatus.trim();
    }

    /**
     * 游戏类别
     * @return game_id 游戏类别
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * 游戏类别
     * @param gameId 游戏类别
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * 0显示 1不显示
     * @return hidden 0显示 1不显示
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * 0显示 1不显示
     * @param hidden 0显示 1不显示
     */
    public void setHidden(String hidden) {
        this.hidden = hidden == null ? null : hidden.trim();
    }

    /**
     * 联赛的等级
     * @return level 联赛的等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 联赛的等级
     * @param level 联赛的等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 热门等级
     * @return hot 热门等级
     */
    public Integer getHot() {
        return hot;
    }

    /**
     * 热门等级
     * @param hot 热门等级
     */
    public void setHot(Integer hot) {
        this.hot = hot;
    }

    /**
     * 
     * @return start_time 
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime 
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return end_time 
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime 
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return progress_state 
     */
    public String getProgressState() {
        return progressState;
    }

    /**
     * 
     * @param progressState 
     */
    public void setProgressState(String progressState) {
        this.progressState = progressState == null ? null : progressState.trim();
    }

    /**
     * 
     * @return place 
     */
    public String getPlace() {
        return place;
    }

    /**
     * 
     * @param place 
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }
}