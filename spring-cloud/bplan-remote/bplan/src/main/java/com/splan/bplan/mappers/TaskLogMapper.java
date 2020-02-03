package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.TaskLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

public interface TaskLogMapper extends SuperMapper<TaskLog> {

    @Select("call month_task()")
    void monthTask();

}