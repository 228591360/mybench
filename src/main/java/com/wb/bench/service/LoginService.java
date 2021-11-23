package com.wb.bench.service;

import com.wb.bench.request.LoginRequest;
import com.wb.bench.response.CustomerInfoResponse;

public interface LoginService {
    CustomerInfoResponse login(LoginRequest request);
}
