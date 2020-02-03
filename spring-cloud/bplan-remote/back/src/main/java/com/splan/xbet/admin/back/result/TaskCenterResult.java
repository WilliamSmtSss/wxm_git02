package com.splan.xbet.admin.back.result;

import com.splan.base.bean.GiftListBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TaskCenterResult implements Serializable {

    private Integer registerGift;//注册送10 0未达标 1可以领取 2已领取

    private Integer depositGift;//首充礼金  0未达标 1可以领取 2已领取

    private BigDecimal unBrokerageCoin;//未领取返水

    private BigDecimal water; //返水

    private BigDecimal nextWater; //下一级返水

    private String level;// 倔强青铜

    private String nextLevel; //

    private BigDecimal reward;//

    private BigDecimal depositCoin;//累计充值金额

    private List<GiftListBean> depositList;

    private Date deadline;//倒计时

    private Integer memberLevel;

    private Integer flag = 0;

    public void doFlag(){
        if (this.registerGift==1){
            flag = 1;
        }
        if (this.depositGift==1){
            flag = 1;
        }
        /*for (int i = 0; i < depositList.size(); i++) {
            if (depositList.get(i).getFlag()==1){
                flag = 1;
            }
        }*/
    }


}
