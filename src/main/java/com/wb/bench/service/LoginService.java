package com.wb.bench.service;

import com.wb.bench.exception.BaseBusinessException;
import com.wb.bench.request.LoginRequest;
import com.wb.bench.response.CustomerInfoResponse;

public interface LoginService {
    CustomerInfoResponse login(LoginRequest request) throws BaseBusinessException;
}
