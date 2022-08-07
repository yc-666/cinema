package com.zrkworld.cinema.controller;

import com.zrkworld.cinema.common.constants.ResultCode;
import com.zrkworld.cinema.common.vo.CinemaResult;
import com.zrkworld.cinema.service.ManagerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@CrossOrigin
@RestController
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
    @RequestMapping("getManagerData")
    public CinemaResult getManagerData(String managerId){
        return CinemaResult.success(managerService.selectByManagerId(managerId));
    }

    @RequestMapping("managerLogin")
    public CinemaResult managerLogin(String managerId, String password){
        if (managerService.managerLogin(managerId, password) == null) {
            return CinemaResult.failure(ResultCode.INTERNAL_SERVER_ERROR,"error");
        } else {
            return CinemaResult.success();
        }
    }
}
