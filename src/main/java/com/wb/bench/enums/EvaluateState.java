package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 评价状态
 * @author wanggang
 */
@ApiEnum
public enum EvaluateState {

    @ApiEnumProperty("否")
    NO("NO"),
    @ApiEnumProperty("是")
    YES("YES");

    private String evaluateState;

    EvaluateState(String evaluateState){
        this.evaluateState = evaluateState;
    }

    public static Map<String, EvaluateState> map = new HashMap<>();

    static {
        Arrays.stream(EvaluateState.values()).forEach(
                t -> map.put(t.getEvaluateState(), t)
        );
    }

    @JsonCreator
    public static EvaluateState forValue(String evaluateState){
        return map.get(evaluateState);
    }


    @JsonValue
    public String toValue() {
        return this.getEvaluateState();
    }

    public String getEvaluateState() {
        return evaluateState;
    }
}
