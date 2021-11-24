package com.wb.bench.handler;

import com.wb.bench.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 业务异常处理
     * @param e
     * @return ErrorInfo
     */
    @ExceptionHandler({BaseBusinessException.class})
    public ResponseEntity<BaseBusinessException> businessExceptionHandler(HttpServletRequest request, BaseBusinessException e) throws BaseBusinessException {
        return new ResponseEntity(new BaseBusinessException(e.getCode(),e.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * 业务异常处理
     * @param e
     * @return ErrorInfo
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<BaseBusinessException> BusinessExceptionHandler(HttpServletRequest request, AccessDeniedException e) throws BaseBusinessException {
        return new ResponseEntity(new BaseBusinessException(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
