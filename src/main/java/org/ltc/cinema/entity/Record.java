package org.ltc.cinema.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;


/**
 * @author PpxiA
 *
 * 消费记录
 */
@Data
@ApiModel(description = "消费记录")
public class Record implements Serializable {

    @ApiModelProperty(value = "消费记录唯一id", required = true, dataType = "Integer")
    private Integer id;

    @ApiModelProperty(value = "消费使用的会员卡id", required = true, dataType = "String")
    private String cardId;

    @ApiModelProperty(value = "消费金额", required = true, dataType = "Integer")
    private Integer value;

    @ApiModelProperty(value = "消费时间", required = true, dataType = "String")
    private String time;

    @ApiModelProperty(value = "消费类型", required = true, notes = "0是人民币，1是积分",dataType = "Integer")
    private Integer spendType;

    private static final long serialVersionUID = 1L;
}