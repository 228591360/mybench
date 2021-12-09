package com.wb.bench.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class FreceiveRequest implements Serializable {

    String data;
}
