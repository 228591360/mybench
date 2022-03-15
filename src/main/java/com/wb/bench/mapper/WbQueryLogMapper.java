package com.wb.bench.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.request.LogRequest;
import com.wb.bench.request.StatisticsRequest;
import com.wb.bench.response.LogResponse;
import com.wb.bench.response.StatisticsResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:02
 */
@Mapper
public interface WbQueryLogMapper extends BaseMapper<WbQueryLog> {
    List<StatisticsResponse> queryPage(StatisticsRequest request);

    List<LogResponse> queryLog(LogRequest request);
}
