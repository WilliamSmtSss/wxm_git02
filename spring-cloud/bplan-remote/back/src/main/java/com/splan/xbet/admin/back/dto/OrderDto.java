package com.splan.xbet.admin.back.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class OrderDto implements Serializable,Comparable<OrderDto> {

    /**
     * 竞猜Id
     */
    @NotEmpty(message = "竞猜Id不能为空")
    private Integer betOptionId;

    /**
     * 下注金额
     */
    private Integer amount;

    /**
     * 下注时的赔率
     */
    @NotEmpty(message = "赔率不能为空")
    private BigDecimal odd;

    /**
     * 额外字段
     */
    private String extra;

    public OrderDto(Integer betOptionId,int amount,BigDecimal odd,String extra){
        super();
        this.betOptionId = betOptionId;
        this.amount = amount;
        this.odd = odd;
        this.extra = extra;
    }

    @Override
    public OrderDto clone(){
        OrderDto orderDto = new OrderDto(this.betOptionId,this.amount,this.odd,this.extra);
        return orderDto;
    }

    @Override
    public int compareTo(OrderDto o) {
        return this.getBetOptionId()-o.getBetOptionId();
    }

    public static void main(String[] args) {
        OrderDto orderDto = new OrderDto(110,100,null,"");
        OrderDto orderDto2 = new OrderDto(101,100,null,"");
        List<OrderDto> list = new ArrayList<>();
        list.add(orderDto);
        list.add(orderDto2);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);

    }
}
