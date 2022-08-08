package org.ltc.cinema.controller;

import io.swagger.annotations.*;
import org.ltc.cinema.common.constants.ResultCode;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.service.ManagerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mbz1113zzz
 * @version 1.0
 * @date 2022/8/7 18:01
 */
@CrossOrigin
@RestController
@Api(tags = "管理员模块")
public class ManagerController {
    @Resource
    ManagerService managerService;

    /**
     *  //获取管理员数据，请求参数managerId
     * export const managerData = query=>{
     *     return request({
     *         url:'getManagerData',
     *         method:'get',
     *         params:query
     *     });
     * };
     * @param managerId
     * @return
     */
    @ApiOperation(value = "根据管理员ID获取管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "managerId",value = "管理员id",
            required = true, paramType = "body",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取成功"),
            @ApiResponse(code = 400,message = "获取失败"),
    })
    @GetMapping(value = "/managers/{managerId}")
    public CinemaResult getManagerData(@PathVariable("managerId") String managerId){
        return CinemaResult.success(managerService.selectByManagerId(managerId));
    }

    @ApiOperation(value = "管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "managerId",value = "管理员id",
                    required = true, paramType = "body",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "管理员密码",
                    required = true, paramType = "body",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "登录成功"),
            @ApiResponse(code = 400,message = "登录失败")
    })
    @GetMapping(value = "managerLogin")
    public CinemaResult managerLogin(String managerId, String password){
        if (managerService.managerLogin(managerId, password) == null) {
            return CinemaResult.failure(ResultCode.INTERNAL_SERVER_ERROR,"error");
        } else {
            return CinemaResult.success();
        }
    }
}
