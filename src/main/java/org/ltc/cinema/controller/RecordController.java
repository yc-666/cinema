package org.ltc.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.PageResult;
import org.ltc.cinema.entity.Record;
import org.ltc.cinema.service.RecordService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@CrossOrigin
@RestController
public class RecordController {
    @Resource
    RecordService recordService;

    /**
     * //交易记录查询，请求参数会员id，pageIndex,pageSize
     * export const queryRecord = query=>{
     * return request({
     * url:"queryRecord",
     * method:'post',
     * params:query
     * })
     * }
     */
    @RequestMapping("queryRecord")
    public CinemaResult queryRecord(String memberId, String pageIndex, String pageSize) {
        PageResult pageResult = new PageResult();
        PageHelper.startPage(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
        List<Record> records = recordService.selectRecordByMemberId(memberId);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.success(pageResult);
    }
}
