package com.splan.base.result.remote;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class FrontDataResultOut implements Serializable {

    boolean activeUserCount;

    boolean orderCount;

    List<FrontDataResult> list=new ArrayList<FrontDataResult>();

    FrontDataResult yesterDay=new FrontDataResult();

    FrontDataResult beforeYesterDay=new FrontDataResult();

}
