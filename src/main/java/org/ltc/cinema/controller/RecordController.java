package org.ltc.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.PageResult;
import org.ltc.cinema.entity.Record;
import org.ltc.cinema.service.RecordService;
import org.ltc.cinema.service.exception.RecordException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@Api(tags = "消费记录模块")
@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordController {
    @Resource
    RecordService recordService;

    @ApiOperation(value = "查找消费记录", notes = "查询指定消费记录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "memberId", value = "会员id",
            required = true, paramType = "path", dataType = "String"),
        @ApiImplicitParam(name = "pageIndex", value = "页下标",
            required = true, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "pageSize", value = "页大小",
            required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "查找成功"),
        @ApiResponse(code = 400, message = "查找失败"),
    })
    @GetMapping("/{memberId}")
    public CinemaResult queryRecord(@PathVariable(value = "memberId") String memberId,
        String pageIndex, String pageSize){
        PageResult pageResult = new PageResult();
        PageHelper.startPage(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
        List<Record> records = recordService.selectRecordByMemberId(memberId);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.success(pageResult);
    }
}
