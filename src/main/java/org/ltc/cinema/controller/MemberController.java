package org.ltc.cinema.controller;

import io.swagger.annotations.*;
import org.ltc.cinema.common.constants.ResultCode;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.Member;
import org.ltc.cinema.entity.MemberQuery;
import org.ltc.cinema.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mbz1113zzz
 * @version 1.0
 * @date 2022/8/7 18:01
 */

@CrossOrigin
@RestController
@Api(tags = "会员模块")
@RequestMapping(value = "/members")
public class MemberController {
    @Resource
    MemberService memberService;

    /**
     * //查询会员列表，参数pageIndex、pageSize、name、birtydayQuery，
     * // 返回key为list 的数组和key为pageTotal的整型，birtydayQuery:空表示无限制，0示当天过生日，7表示7天内过生日
     *
     * export const memberData = query=>{
     *     return request({
     *         url:'memberData',
     *         method:'get',
     *         params:query
     *     });
     * };
     * @param pageIndex
     * @param pageSize
     * @param name
     * @param birthdayQuery
     * @return
     */
    @ApiOperation(value = "获取所有会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "分页开始位置",
                    required = true, paramType = "body",dataType = "String"),
            @ApiImplicitParam(name = "pageSize",value = "分页大小",
                    required = true, paramType = "body",dataType = "String"),
            @ApiImplicitParam(name = "name",value = "会员姓名",
                    required = false, paramType = "body",dataType = "String"),
            @ApiImplicitParam(name = "birthdayQuery",value = "查询生日",
                    required = false, paramType = "body",dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取成功"),
            @ApiResponse(code = 400,message = "获取失败")
    })
    @GetMapping(value = "")
    public CinemaResult getMemberData(String pageIndex, String pageSize, String name, String birthdayQuery){
        MemberQuery query = new MemberQuery();
        if(StringUtils.isNotBlank(pageIndex)){
            query.setPageIndex(Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
        }
        if(StringUtils.isNotBlank(name)){
            query.setName(name);
        }
        if(StringUtils.isNotBlank(birthdayQuery)){
            query.setBirthdayQuery(birthdayQuery);
        }
        return memberService.getMemberData(query);
    }
    @ApiOperation(value = "会员注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member",value = "会员",
                    required = true, paramType = "body",dataType = "Member")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "注册成功"),
            @ApiResponse(code = 400,message = "注册失败")
    })
    @PostMapping(value = "/{name}")
    public CinemaResult memberRegister(Member member){
        return memberService.memberRegister(member);
    }
    /**
     * //会员删除，参数MemberId，返回Result
     * export const delMember = query=>{
     *     return request({
     *         url:'delMember',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @ApiOperation(value = "删除会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member",value = "会员",
                    required = true, paramType = "body",dataType = "Member")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功"),
            @ApiResponse(code = 400,message = "删除失败")
    })
    @DeleteMapping(value = "/{memberId}")
    public CinemaResult delMember(Member member){
        return memberService.delMember(member.getmemberId());
    }
    /**
     * //修改会员信息，参数Member，返回Result
     * export const modifyMember = query=>{
     *     return request({
     *         url:'modifyMember',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @ApiOperation(value = "修改会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member",value = "会员",
                    required = true, paramType = "body",dataType = "Member")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "修改成功"),
            @ApiResponse(code = 400,message = "修改失败")
    })
    @PutMapping(value = "/{memberId}")
    public CinemaResult modifyMember(Member member){
        return memberService.modifyMember(member);
    }

    /**
     * //会员登录，参数memberId，返回200
     * export const loginMember = query=>{
     *     return request({
     *         url:'loginMember',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @ApiOperation(value = "会员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member",value = "会员",
                    required = true, paramType = "body",dataType = "Member")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "登录成功"),
            @ApiResponse(code = 400,message = "登录失败")
    })
    @GetMapping(value = "/loginMember")
    public CinemaResult loginMember(Member member){
        if(memberService.loginMember(member)!=null){
            return CinemaResult.success();
        }else{
            return CinemaResult.failure(ResultCode.INTERNAL_SERVER_ERROR,"无此账号");
        }
    }

}
