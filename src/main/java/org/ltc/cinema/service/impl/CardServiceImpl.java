package org.ltc.cinema.service.impl;

import org.ltc.cinema.mapper.CardMapper;
import org.ltc.cinema.entity.Card;
import org.ltc.cinema.entity.Record;
import org.ltc.cinema.service.CardService;
import org.ltc.cinema.service.RecordService;
import org.ltc.cinema.service.exception.CardException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author txg
 * @version 1.0
 * @date 2022/8/7 12:01
 */
@Service
public class CardServiceImpl implements CardService {
    @Resource
    CardMapper cardMapper;
    @Resource
    RecordService recordService;

    @Override
    public List<Card> getCardData(String memberId) {
        return cardMapper.selectCardByMemberId(memberId);
    }

    @Override
    public Card getCardDataById(String cardId) {
        return cardMapper.selectCardById(cardId);
    }


    @Override
    public void registerCard(String memberId) throws CardException {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Card card = new Card();
        card.setCardId(uuid);
        card.setBalance(0);
        card.setIntegral(0);
        card.setLose(0);
        card.setMemberId(memberId);
        // 貌似可以的异常举例
        try{
            cardMapper.insertCard(card);
        }catch (RuntimeException e){
            throw new CardException("会员卡增加失败! "+e.getMessage());
        }
    }

    @Override
    public void deleteCardById(String cardId) {
        cardMapper.deleteCardById(cardId);
    }

    @Override
    public String reissueCard(String cardId) {
        Card card = this.getCardDataById(cardId);
        //由于在记录表中有外键依赖，所以必须先删除
        recordService.deleteRecordByCardId(cardId);
        deleteCardById(cardId);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        card.setCardId(uuid);
        card.setLose(0);
        cardMapper.insertCard(card);
        return uuid;
    }

    @Override
    public void loseCard(String cardId) {
        Card card = new Card();
        card.setCardId(cardId);
        card.setLose(1);
        cardMapper.updateCardById(card);
    }

    @Override
    public void cancelCard(String cardId) {
        Card card = new Card();
        card.setCardId(cardId);
        card.setLose(0);
        cardMapper.updateCardById(card);
    }

    @Override
    public void rechargeCard(String cardId, int value) {
        Card card = this.getCardDataById(cardId);
        card.setBalance(card.getBalance()+value);
        //消费记录
        Record record = new Record();
        record.setCardId(cardId);
        record.setSpendType(0);
        record.setValue(value);
        cardMapper.updateCardById(card);
        recordService.insertRecord(record);
    }

    @Override
    public void consumeCard(String cardId, int price, int integral) {
        Card card = this.getCardDataById(cardId);
            card.setBalance(card.getBalance()-price);
            card.setIntegral(card.getIntegral()+integral);;
        //消费记录
        Record record1 = new Record();
        record1.setCardId(cardId);
        record1.setSpendType(0);
        record1.setValue(price*-1);
        Record record2 = new Record();
        record2.setCardId(cardId);
        record2.setSpendType(1);
        record2.setValue(integral);
        cardMapper.updateCardById(card);
        recordService.insertRecord(record1);
        recordService.insertRecord(record2);
    }

    @Override
    public void exchangeIntegral(String memberId, int consumeIntegral) {
        //1.通过memberId查到所有卡号和对应积分
        List<Card> cards = cardMapper.selectCardByMemberId(memberId);
        for (Card card:
             cards) {
        //2.如果消耗积分比当前卡中积分要多，将消耗积分减去当前卡积分，将卡中积分置0，并设置消费记录
            if(card.getIntegral()<=consumeIntegral){
                consumeIntegral-=card.getIntegral();
                Record record2 = new Record();
                record2.setCardId(card.getCardId());
                record2.setSpendType(1);
                record2.setValue(card.getIntegral()*-1);
                card.setIntegral(0);
                cardMapper.updateCardById(card);
                recordService.insertRecord(record2);
            }else{
                //消耗积分比当前卡中少，卡中积分减去需消耗积分

                Record record = new Record();
                record.setCardId(card.getCardId());
                record.setSpendType(1);
                record.setValue(consumeIntegral*-1);
                card.setIntegral(card.getIntegral()-consumeIntegral);
                cardMapper.updateCardById(card);
                recordService.insertRecord(record);
                break;
            }
        }


    }

    @Override
    public Long getCardNum() {
        return cardMapper.selectCardCount();
    }

    @Override
    public List<String> getCardIdByFuzzyQuery(String memberId,String cardId) {
        List<String> cardList  = cardMapper.selectCardIdByFuzzyQuery(memberId,cardId);
        return cardList;
    }

    @Override
    public Card getCardByCardId(String cardId) {
        return cardMapper.selectCardById(cardId);
    }


}
