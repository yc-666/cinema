package org.ltc.cinema.service;

import org.ltc.cinema.entity.Record;
import org.ltc.cinema.entity.Schart1Data;

import java.util.List;
import org.ltc.cinema.service.exception.RecordException;

public interface RecordService {
    /**
     * 通过memberId查到所拥有的的所有cardId,然后查询到所有cardId的交易记录
     * @param memberId
     * @return
     */
    List<Record> selectRecordByMemberId(String memberId) throws RecordException;

    /**
     * 删除会员卡的消费记录
     * @param cardId
     */
    void deleteRecordByCardId(String cardId);

    /**
     * 新增消费记录
     * @param record
     */
    void insertRecord(Record record);

    /**
     * 获取所有电影票售出数量
     * @return
     */
    Long getMovieNum();

    /**
     * 获取消费额总数
     * @return
     */
    Long getConsumeCount();

    /**
     * 获取积分兑换总数
     * @return
     */
    Long getIntegralExchangeCount();

    /**
     * 获取充值总数
     * @return
     */
    Long getRechargeCount();

    /**
     * 获取统计图表所需数据
     * @return
     */
    Schart1Data getSchart1Data();
}
