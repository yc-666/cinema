package org.ltc.cinema.service.impl;

import org.ltc.cinema.mapper.ManagerMapper;
import org.ltc.cinema.entity.Manager;
import org.ltc.cinema.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Resource
    ManagerMapper managerMapper;
    @Override
    public Manager selectByManagerId(String managerId) {
       return managerMapper.selectByManagerId(managerId);
    }

    @Override
    public Manager managerLogin(String managerId, String password) {

        Manager manager = managerMapper.managerLogin(managerId,password);
        if(manager!=null){
            managerMapper.updateManagerTime(manager.getManagerId());
            return manager;
        }
        return null;
    }
    /*
今天获取数据库内的时间老是获取到2019-8-14  00:00:00而不是具体的时分秒，原来是mybatis的映射里Date类型无法转化为具体的时间。
将<result column="eventtime4" jdbcType="Date" property="eventtime" />换成
<result column="eventtime" jdbcType="TIMESTAMP" property="eventtime4" />
就可以了
实体类可以接着使用Date
     */
}
