package org.ltc.cinema.service;

import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.Member;
import org.ltc.cinema.entity.MemberQuery;


/**
 * @author mbz1113zzz
 * @version 1.0
 * @date 2020/8/7 20:05
 */
public interface MemberService {


    /**
     * 接收前端请求条件，返回条件查询结果，并封装返回
     * @param query
     * @return 返回前端所需数据
     */
    CinemaResult getMemberData(MemberQuery query);

    /**
     * 会员注册
     * @param member
     * @return
     */
    CinemaResult memberRegister(Member member);

    /**
     * 删除一个会员
     * @param memberId
     * @return
     */
    CinemaResult delMember(String memberId);

    /**
     * 修改会员信息
     * @param member
     * @return
     */
    CinemaResult modifyMember(Member member);

    /**
     * 查询已注册会员数量
     * @return
     */
    Long getMemberNum();

    /**
     * 会员登录验证
     * @param member
     * @return
     */
    Member loginMember(Member member);

}
