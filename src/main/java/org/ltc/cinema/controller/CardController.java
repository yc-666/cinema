package org.ltc.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.ltc.cinema.entity.Card;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.PageResult;
import org.ltc.cinema.service.CardService;
import org.ltc.cinema.service.exception.CardException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author txg
 * @version 1.0
 * @date 2022/8/7 11:45
 */
@Api(tags = "会员卡模块")
@CrossOrigin
@RestController
//@RequestMapping("/card") 需要改前端url
public class CardController {
    @Resource
    CardService cardService;

    /**
     * 获取某一成员的会员卡信息
     * @param memberId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取某一成员的所有会员卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    required = true, paramType = "body", dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "分页开始位置",
                    required = true, paramType = "body", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小",
                    required = true, paramType = "body", dataType = "String")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "获取失败"),
    })
    @PostMapping(value = "getCardData")
    public CinemaResult getCardData(String memberId, String pageIndex, String pageSize) {
        //这里使用分页插件pagehelper
        PageResult pageResult = new PageResult();
        //打辅助
        PageHelper.startPage(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
        //从数据库获取会员卡的数据
        //判断是否出现问题
        List lists = cardService.getCardData(memberId);
        //将从数据库获取的数据列表封装在PageInfo中
        PageInfo<Card> pageInfo = new PageInfo<>(lists);
        //再放入pageResult
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.success(pageResult);
    }

    /**
     * //注册会员卡，请求参数无，返回状态
     * export const registerCard = query=>{
     * return request({
     * url:"registerCard",
     * method:'get',
     * params:query
     * });
     * };
     */
    @RequestMapping("registerCard")
    public CinemaResult registerCard(String memberId) throws CardException {

        cardService.registerCard(memberId);
        return CinemaResult.success();
    }

    /**
     * //补卡，请求参数卡号id，返回新卡id号，并提示会员
     * export const reissueCard = query=>{
     * return request({
     * url:"reissueCard",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("reissueCard")
    public CinemaResult reissueCard(String cardId) {

        cardId = cardService.reissueCard(cardId);
        return CinemaResult.success("ok", cardId);
    }

    /**
     * //挂失会员卡，请求参数id，返回状态
     * export const loseCard = query=>{
     * return request({
     * url:"loseCard",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("loseCard")
    public CinemaResult loseCard(String cardId) {

        cardService.loseCard(cardId);
        return CinemaResult.success();
    }

    /**
     * //解挂会员卡，请求参数卡号，返回状态
     * export const cancelCard = query=>{
     * return request({
     * url:"cancelCard",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("cancelCard")
    public CinemaResult cancelCard(String cardId) {

        cardService.cancelCard(cardId);
        return CinemaResult.success();
    }

    /**
     * //充值会员卡，请求卡号、参数金额，返回状态
     * export const rechargeCard = query=>{
     * return request({
     * url:"rechargeCard",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("rechargeCard")
    public CinemaResult rechargeCard(String cardId, String value) {

        cardService.rechargeCard(cardId, Integer.parseInt(value));
        return CinemaResult.success();
    }

    /**
     * //消费，请求参数卡号、金额、消费类型，返回状态
     * export const consumeCard = query=>{
     * return request({
     * url:"consumeCard",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("consumeCard")
    public CinemaResult consumeCard(String cardId, String price, String integral) {

        cardService.consumeCard(cardId, Integer.parseInt(price), Integer.parseInt(integral));
        return CinemaResult.success();
    }

    /**
     * //积分兑换，请求参数：会员id，积分
     * //由于是总积分兑换，所以需要将消耗积分分散到所有卡上面
     * export const exchangeIntegral = query=>{
     * return request({
     * url:"exchangeIntegral",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("exchangeIntegral")
    public CinemaResult exchangeIntegral(String memberId, String integral) {

        cardService.exchangeIntegral(memberId, Integer.parseInt(integral));
        return CinemaResult.success();
    }

    /**
     * //通过模糊条件查询到cardId，请求参数memberId,CardId，返回CardId列表
     * export const getCardIdByFuzzyQuery = query=>{
     * return request({
     * url:"getCardIdByFuzzyQuery",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("getCardIdByFuzzyQuery")
    public CinemaResult getCardIdByFuzzyQuery(String memberId, String cardId) {


        return CinemaResult.success(cardService.getCardIdByFuzzyQuery(memberId, cardId));
    }

    /**
     * //通过完整cardid来查询card数据，返回Card对象
     * export const getCardByCardId = query=>{
     * return request({
     * url:"getCardByCardId",
     * method:'post',
     * params:query
     * });
     * };
     */
    @RequestMapping("getCardByCardId")
    public CinemaResult getCardByCardId(String cardId) {

        return CinemaResult.success(cardService.getCardByCardId(cardId));
    }
}
