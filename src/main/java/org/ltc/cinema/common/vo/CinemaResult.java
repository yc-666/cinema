package org.ltc.cinema.common.vo;

import org.ltc.cinema.common.constants.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * @author TXG
 * @date 2022/8/7$
 * <p>
 * 用于携带返回接口请求的数据
 */

@Data
@Builder
@AllArgsConstructor
@ApiModel(description = "标准的后台响应数据")
public class CinemaResult<T> {

    /**
     * 响应业务状态
     */
    @ApiModelProperty(value = "响应状态码", required = true, dataType = "int", example = "200,400,404,501")
    private Integer status;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应状态说明", required = true, dataType = "String")
    private String msg;

    /**
     * 响应中的数据
     */
    @ApiModelProperty(value = "响应数据：json格式")
    private T data;

    /**
     * 构建其他状态的CinemaResult对象，直接用静态方法，不需要额外创建对象了
     */

    public static <T> CinemaResult<T> success(String msg, T data) {
        return new CinemaResult<T>(ResultCode.SUCCESS, msg, data);
    }

    public static <T> CinemaResult<T> success() {
        return success("ok", null);
    }

    public static <T> CinemaResult<T> success(T data) {
        return success("ok", data);
    }

    public static <T> CinemaResult<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> CinemaResult<T> failure(String msg) {
        return failure(null, "", null);
    }

    public static <T> CinemaResult<T> failure(Integer code, String msg) {
        return failure(code, msg, null);
    }

    public static <T> CinemaResult<T> failure(Integer code, String msg, T data) {
        return new CinemaResult<>(code, msg, data);
    }

}
