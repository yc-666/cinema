package org.ltc.cinema.exception.advice;

import org.ltc.cinema.common.constants.ResultCode;
import org.ltc.cinema.common.vo.CinemaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author TXG
 * @date 2022/8/7$
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    /**
     * @ExceptionHandler 注解 异常处理器，能够拦截指定类型的异常，拦截之后会指向该注解修饰的方法
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public CinemaResult handlerArithmeticException(Exception e){
        /**
         * 1.打印日志
         * 2.将错误信息发生给前端
         */
        log.error("逻辑层算数运算错误:" + e.getMessage(), e);
        return CinemaResult.failure(ResultCode.INTERNAL_SERVER_ERROR,"服务器错误，请联系管理员！" + e.getMessage());
    }

    /**
     * 运行时的异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public CinemaResult handlerRuntimeException(RuntimeException e){
        log.error("运行时异常：" + e.getMessage(), e);
        return CinemaResult.failure(201,"运行时异常:" + e.getMessage());
    }

}


