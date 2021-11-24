package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程跟踪状态
 * @author wanggang
 */
@ApiEnum
public enum FlowTrackState {

    @ApiEnumProperty("否决操作")
    NO("NO"),

    @ApiEnumProperty("待操作")
    WAIT("WAIT"),

    @ApiEnumProperty("已完成操作")
    YES("YES");

    private String state;

    FlowTrackState(String state){
        this.state = state;
    }

    public static Map<String, FlowTrackState> map = new HashMap<>();

    static {
        Arrays.stream(FlowTrackState.values()).forEach(
                t -> map.put(t.getState(), t)
        );
    }

    @JsonCreator
    public static FlowTrackState forValue(String state){
        return map.get(state);
    }


    @JsonValue
    public String toValue() {
        return this.getState();
    }

    public String getState() {
        return state;
    }
}
