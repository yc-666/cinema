package org.ltc.cinema.controller;

import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.DealData;
import org.ltc.cinema.entity.StatisticData;
import org.ltc.cinema.service.CardService;
import org.ltc.cinema.service.MemberService;
import org.ltc.cinema.service.RecordService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@CrossOrigin
@RestController
public class StatisticController {
    @Resource
    MemberService memberService;
    @Resource
    CardService cardService;
    @Resource
    RecordService recordService;
/**
 * //获取统计数字数据，请求参数无
 * export const statisticData = query=>{
 *     return request({
 *         url:'getStatisticData',
 *         method:'get',
 *         params:query
 *     });
 * };
 */
    @RequestMapping("getStatisticData")
    public CinemaResult getStatisticData(){
        StatisticData s = new StatisticData();
        s.setMemberNum(memberService.getMemberNum());
        s.setCardNum(cardService.getCardNum());
        s.setMovieNum(recordService.getMovieNum());
        return CinemaResult.success(s);
    }
    /**
     *  //获取消费充值等金额数据，请求参数无
     * export const requestDealData = query=>{
     *     return request({
     *         url:'requestDealData',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @RequestMapping("requestDealData")
    public CinemaResult requestDealData(){
        DealData dealData = new DealData();
        dealData.setConsume(recordService.getConsumeCount()*-1);
        dealData.setIntegral(recordService.getIntegralExchangeCount()*-1);
        dealData.setRecharge(recordService.getRechargeCount());
        return CinemaResult.success(dealData);
    }
    /**
     *  //获取充值消费数据的图标数据，请求参数无，返回包含三个整型数组的对象
     * export const getSchart1Data = query=>{
     *     return request({
     *         url:"getSchart1Data",
     *         method:'post',
     *         params:query
     *     });
     * };
     */
    @RequestMapping("getSchart1Data")
    public CinemaResult getSchart1Data(){
        return CinemaResult.success(recordService.getSchart1Data());
    }
}
