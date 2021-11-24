package com.wb.bench.exception;

import com.wb.bench.util.CommonErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一异常处理
 * Created by jinwei on 31/3/2017.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SbcRuntimeException extends RuntimeException {

    private String errorCode = "";

    private Object data;

    private String result = "fail";

    private Object[] params;

    /**
     * 默认构造，展示系统异常
     */
    public SbcRuntimeException() {
        super();
        this.errorCode = CommonErrorCode.SUCCESSFUL;
    }

    /**
     * 默认errorCode为 空字符串
     *
     * @param cause
     */
    public SbcRuntimeException(Throwable cause) {
        this("", cause);
    }

    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode 错误码
     * @param result    出错信息
     */
    public SbcRuntimeException(String errorCode, String result) {
        super();
        this.result = result;
        this.errorCode = errorCode;
    }


    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode 异常码 异常码的错误信息会被messageSource读取
     */
    public SbcRuntimeException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }


    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode
     * @param params
     */
    public SbcRuntimeException(String errorCode, Object[] params) {
        super();
        this.errorCode = errorCode;
        this.params = params;
    }

    /**
     * 错误码 + 上级异常
     *
     * @param errorCode
     * @param cause
     */
    public SbcRuntimeException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * 返回数据 + 异常码
     *
     * @param data
     * @param errorCode
     */
    public SbcRuntimeException(Object data, String errorCode) {
        this.data = data;
        this.errorCode = errorCode;
    }

    /**
     * 返回值 + 异常链
     *
     * @param data
     * @param cause
     */
    public SbcRuntimeException(Object data, Throwable cause) {
        super(cause);
        this.data = data;
    }

    public static SbcRuntimeExceptionBuilder builder() {
        return new SbcRuntimeExceptionBuilder();
    }

    public static class SbcRuntimeExceptionBuilder {
        private String errorCode;
        private Object data;
        private String result;
        private Object[] params;

        SbcRuntimeExceptionBuilder() {
        }

        public SbcRuntimeExceptionBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public SbcRuntimeExceptionBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public SbcRuntimeExceptionBuilder result(String result) {
            this.result = result;
            return this;
        }

        public SbcRuntimeExceptionBuilder params(Object[] params) {
            this.params = params;
            return this;
        }

        public SbcRuntimeException build() {
            return new SbcRuntimeException(this.errorCode, this.data, this.result, this.params);
        }

        @Override
        public String toString() {
            return "SbcRuntimeException.SbcRuntimeExceptionBuilder(errorCode=" + this.errorCode + ", data=" + this.data +
                    ", result=" + this.result + ", params=" + this.params + ")";
        }
    }
}
