package com.splan.base.http;

import com.splan.base.bean.ash.AshBean;
import lombok.Data;

import java.util.List;

@Data
public class PandoraItemResult<T extends AshBean> {

    private Integer code;

    private Integer limit;

    private Long total;

    private List<T> items;
}
