package org.ltc.cinema.mapper;

import org.ltc.cinema.entity.Manager;


/**
 * @author mbz1113zzz
 * @version 1.0
 * @date 2020/8/7 20:05
 */
public interface ManagerMapper {
    /**
     * 接收managerId，返回Manager
     *
     * @param managerId
     * @return Manager
     */
    Manager selectByManagerId(String managerId);

    /**
     *
     * @param managerId
     * @param password
     * @return
     */
    Manager managerLogin(String managerId,String password);

    void updateManagerTime(String managerId);

}