package com.zrkworld.cinema.common.constants;

/**
 * @author TXG
 * @date 2022/8/7$
 */
public interface ResultCode {
    //成功
    Integer SUCCESS = 200;
    //失败
    Integer FAIL = 400;
    //未认证（签名错误）
    Integer UNAUTHORIZED = 401;
    //没有登录
    Integer NO_LOGIN = 402;
    //没有权限
    Integer NO_PERMISSION = 403;
    //接口不存在
    Integer NOT_FOUND = 404;
    //用户状态异常、公司状态异常、产品状态异常
    Integer STATE_ERROR = 406;
    //冲突
    Integer CONFLICT = 409;
    //访问太多
    Integer TOO_MANY_REQUEST = 429;

    //服务器内部错误
    Integer INTERNAL_SERVER_ERROR = 500;

    //参数错误
    Integer PARAMETER_ERROR = 10001;

    //账号错误
    Integer ACCOUNT_ERROR = 20001;

    //登录失败
    Integer LOGIN_FAIL_ERROR = 20002;
}
