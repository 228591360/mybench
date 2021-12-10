package com.wb.bench.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.service.WbQueryLogService;
import org.springframework.stereotype.Service;

@Service
public class WbQueryLogServiceImpl extends ServiceImpl<WbQueryLogMapper, WbQueryLog> implements WbQueryLogService {

}
