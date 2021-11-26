package com.wb.bench.handler.aop;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


@Aspect
@Component
@Slf4j
public class ClientCallAspect {

    /**
     * 排除掉请求参数的方法名
     */
    private static final List<String> excludedMethods = Arrays.asList("uploadFile","uploadExcelFile");

    @Pointcut("execution(* com.wb.bench..*Controller.*(..))")
    public void pointcutLock() {
    }

    @AfterReturning(pointcut = "pointcutLock()", returning = "res")
    public void after(JoinPoint joinPoint, Object res) throws SbcRuntimeException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        String str = String.format("请求微服务模块 --> 类名：%s --> 方法名： %s() ",
                targetMethod.getDeclaringClass().getSimpleName(), joinPoint.getSignature().getName());

        String requestInfo = DateUtil.nowTime() + str + "请求参数：" + getMessage(joinPoint);

//        String errCode = ((BaseResponse) res).getCode();
//        String errMsg = ((BaseResponse) res).getMessage();
//        Object context = ((BaseResponse) res).getErrorData();
//        if (!CommonErrorCode.SUCCESSFUL.equals(errCode)) {
//            log.error(str + "出现异常！请求的接口信息：{}，接口返回信息：{}", requestInfo, res);
//            if (context != null) {
//                throw new SbcRuntimeException(context, errCode);
//            } else {
//                throw new SbcRuntimeException(errCode, errMsg);
//            }
//        }
        // todo 耗CPU
//        log.info(DateUtil.nowTime(), str + "返回参数：", JSONObject.toJSONString(res));
    }

    @Before("pointcutLock()")
    public void before(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method targetMethod = methodSignature.getMethod();
        String str = String.format("请求微服务模块 --> 类名：%s --> 方法名： %s() ",
                targetMethod.getDeclaringClass().getSimpleName(), point.getSignature().getName());

        String requestInfo = DateUtil.nowTime() + str + "请求参数：" + getMessage(point);
        log.info(requestInfo);
    }

    /**
     * 获取异常信息
     *
     * @param point
     * @return
     */
    private String getMessage(JoinPoint point) {
        String message = "业务特殊处理, 忽略请求参数!";
        if (!excludedMethods.contains(point.getSignature().getName())) {
            message = JSONObject.toJSONString(point.getArgs());
        }
        return message;
    }
}
