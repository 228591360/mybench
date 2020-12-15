package com.wb.bench.entity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
public class User implements Serializable {
    /**
     * 名称
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 年龄
     */
    private Integer age;
}
