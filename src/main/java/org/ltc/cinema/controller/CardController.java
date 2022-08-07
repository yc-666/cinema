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
@RequestMapping("/members/{memberId}/cards")
public class CardController {
    @Resource
    CardService cardService;

    /**
     * 获取某一成员的会员卡信息
     *
     * @param memberId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取某一成员的所有会员卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "分页开始位置",
                    required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小",
                    required = true, paramType = "query", dataType = "String")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "获取失败"),
    })
    @GetMapping("")
    public CinemaResult getCardData(@PathVariable("memberId") String memberId, String pageIndex, String pageSize) throws CardException {
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
    @ApiOperation(value = "为一个成员新增一张会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    required = true, paramType = "path", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 400, message = "添加失败"),
    })
    @PostMapping("/reg")
    public CinemaResult registerCard(@PathVariable("memberId") String memberId) throws CardException {
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
    @ApiOperation(value = "补办一张会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "carId", value = "旧会员卡id",
                    required = true, paramType = "path", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "补办成功"),
            @ApiResponse(code = 400, message = "补办失败"),
    })
    @PostMapping("/reissue/{carId}")
    public CinemaResult reissueCard(@PathVariable("carId") String cardId) throws CardException {

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
    @ApiOperation(value = "挂失会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "carId", value = "会员卡id",
                    required = true, paramType = "path", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "挂失成功"),
            @ApiResponse(code = 400, message = "挂失失败"),
    })
    @PutMapping("/lose/{carId}")
    public CinemaResult loseCard(@PathVariable("carId") String cardId) throws CardException {
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

    @ApiOperation(value = "解挂会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "carId", value = "会员卡id",
                    required = true, paramType = "path", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "解挂成功"),
            @ApiResponse(code = 400, message = "解挂失败"),
    })
    @PutMapping("cancel/{cardId}")
    public CinemaResult cancelCard(@PathVariable("cardId") String cardId) throws CardException {
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
    @ApiOperation(value = "充值会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "carId", value = "会员卡id",
                    required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "value", value = "充值金额",
                    required = true, paramType = "body", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "充值成功"),
            @ApiResponse(code = 400, message = "充值失败"),
    })
    @PostMapping("/{cardId}/recharge")
    public CinemaResult rechargeCard(@PathVariable("cardId") String cardId, String value) throws CardException {
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
    @ApiOperation(value = "会员卡消费")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "carId", value = "会员卡id",
                    required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "price", value = "消费金额",
                    required = true, paramType = "body", dataType = "String"),
            @ApiImplicitParam(name = "integral", value = "积分数量",
                    required = true, paramType = "body", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "消费成功"),
            @ApiResponse(code = 400, message = "消费失败"),
    })
    @PostMapping("/{cardId}/consume")
    public CinemaResult consumeCard(@PathVariable("cardId") String cardId, String price, String integral) throws CardException {
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
    @ApiOperation(value = "积分兑换")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "integral", value = "积分数量",
                    required = true, paramType = "body", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "兑换成功"),
            @ApiResponse(code = 400, message = "兑换失败"),
    })
    @PostMapping("/integral/exchange")
    public CinemaResult exchangeIntegral(@PathVariable("memberId") String memberId, String integral) throws CardException {
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
    @ApiOperation(value = "模糊查询会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "会员卡id",
                    required = true, paramType = "path", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "查询失败"),
    })
    @GetMapping("{cardId}/like")
    public CinemaResult getCardIdByFuzzyQuery(@PathVariable("memberId") String memberId, @PathVariable("cardId") String cardId) throws CardException {
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
    @ApiOperation(value = "查询会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "成员id",
                    paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "会员卡id",
                    required = true, paramType = "path", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "查询失败"),
    })
    @GetMapping("{cardId}")
    public CinemaResult getCardByCardId(@PathVariable("cardId") String cardId) throws CardException {

        return CinemaResult.success(cardService.getCardByCardId(cardId));
    }
}
