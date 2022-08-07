package com.zrkworld.cinema.service.impl;

import com.zrkworld.cinema.mapper.MemberMapper;
import com.zrkworld.cinema.common.vo.CinemaResult;
import com.zrkworld.cinema.entity.Member;
import com.zrkworld.cinema.entity.MemberQuery;
import com.zrkworld.cinema.entity.PageResult;
import com.zrkworld.cinema.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;


/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberMapper memberMapper;
    @Override
    public CinemaResult getMemberData(MemberQuery query) {
        PageResult<Member> pageResult = new PageResult<>();
        pageResult.setList(memberMapper.selectByOrder(query));

        /**
         * 对于分页取数据，要注意要排序，对于limit和count函数并用，需要用子查询，因为
         * limit是在最后执行的
         */
        pageResult.setPageTotal(memberMapper.selectCountByOrder(query));
        return CinemaResult.success(pageResult);
    }

    @Override
    public CinemaResult memberRegister(Member member) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        member.setmemberId(uuid);
        memberMapper.insertMember(member);
        return CinemaResult.success(member);
    }

    @Override
    public CinemaResult delMember(String memberId) {
        memberMapper.deleteMember(memberId);
        return CinemaResult.success();
    }

    @Override
    public CinemaResult modifyMember(Member member) {
        memberMapper.updateMember(member);
        return CinemaResult.success();
    }

    @Override
    public Long getMemberNum() {
         return memberMapper.selectCountByOrder(null);
    }

    @Override
    public Member loginMember(Member member) {
        return memberMapper.selectMemberByLogin(member);
    }
}
