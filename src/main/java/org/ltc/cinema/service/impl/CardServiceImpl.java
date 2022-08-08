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
    public List<Card> getCardData(String memberId) throws CardException {
        List<Card> list = null;
        try {
            list = cardMapper.selectCardByMemberId(memberId);
        }catch (RuntimeException e){
            throw new CardException("获取卡信息失败 "+ e.getMessage());
        }
        return list;
    }

    @Override
    public Card getCardDataById(String cardId) throws CardException {
        Card card = null;
        try {
            card = cardMapper.selectCardById(cardId);
        }catch (RuntimeException e){
            throw new CardException("无该用户信息",e);
        }
        return card;
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
            throw new CardException("会员卡增加失败! ",e);
        }
    }

    @Override
    public void deleteCardById(String cardId) throws CardException {
        try {
            cardMapper.deleteCardById(cardId);
        }catch (RuntimeException e){
            throw new CardException("删除失败，卡号不对",e);
        }
    }

    @Override
    public String reissueCard(String cardId) throws CardException {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            Card card = this.getCardDataById(cardId);
            //由于在记录表中有外键依赖，所以必须先删除
            recordService.deleteRecordByCardId(cardId);
            deleteCardById(cardId);
            card.setCardId(uuid);
            card.setLose(0);
            cardMapper.insertCard(card);
        }catch (RuntimeException e){
            throw new CardException("解除挂失失败",e);
        }
        return uuid;
    }

    @Override
    public void loseCard(String cardId) throws CardException {
        Card card = new Card();
        card.setCardId(cardId);
        card.setLose(1);
        try {
            cardMapper.updateCardById(card);
        }catch (RuntimeException e){
            throw new CardException("挂失失败",e);
        }
    }

    @Override
    public void cancelCard(String cardId) throws CardException {
        Card card = new Card();
        card.setCardId(cardId);
        card.setLose(0);
        try {
            cardMapper.updateCardById(card);
        }catch (RuntimeException e){
            throw new CardException("消除会员卡失败",e);
        }
    }

    @Override
    public void rechargeCard(String cardId, int value) throws CardException {
        try{
            Card card = this.getCardDataById(cardId);
            card.setBalance(card.getBalance()+value);
            //消费记录
            Record record = new Record();
            record.setcardId(cardId);
            record.setspendType(0);
            record.setValue(value);
            cardMapper.updateCardById(card);
            recordService.insertRecord(record);
        }catch (RuntimeException e){
            throw new CardException("消费失败",e);
        }
    }

    @Override
    public void consumeCard(String cardId, int price, int integral) throws CardException {
        try {
            Card card = this.getCardDataById(cardId);
            card.setBalance(card.getBalance()-price);
            card.setIntegral(card.getIntegral()+integral);;
            //消费记录
            Record record1 = new Record();
            record1.setcardId(cardId);
            record1.setspendType(0);
            record1.setValue(price*-1);
            Record record2 = new Record();
            record2.setcardId(cardId);
            record2.setspendType(1);
            record2.setValue(integral);
            cardMapper.updateCardById(card);
            recordService.insertRecord(record1);
            recordService.insertRecord(record2);
        }catch (RuntimeException e){
            throw new CardException("消费失败",e);
        }

    }

    @Override
    public void exchangeIntegral(String memberId, int consumeIntegral) throws CardException {
        try {
            //1.通过memberId查到所有卡号和对应积分
            List<Card> cards = cardMapper.selectCardByMemberId(memberId);
            for (Card card:
                    cards) {
                //2.如果消耗积分比当前卡中积分要多，将消耗积分减去当前卡积分，将卡中积分置0，并设置消费记录
                if(card.getIntegral()<=consumeIntegral){
                    consumeIntegral-=card.getIntegral();
                    Record record2 = new Record();
                    record2.setcardId(card.getCardId());
                    record2.setspendType(1);
                    record2.setValue(card.getIntegral()*-1);
                    card.setIntegral(0);
                    cardMapper.updateCardById(card);
                    recordService.insertRecord(record2);
                }else{
                    //消耗积分比当前卡中少，卡中积分减去需消耗积分
                    Record record = new Record();
                    record.setcardId(card.getCardId());
                    record.setspendType(1);
                    record.setValue(consumeIntegral*-1);
                    card.setIntegral(card.getIntegral()-consumeIntegral);
                    cardMapper.updateCardById(card);
                    recordService.insertRecord(record);
                    break;
                }
            }
        }catch (RuntimeException e){
            throw new CardException("积分不足",e);
        }
    }

    @Override
    public Long getCardNum() throws CardException {
        Long num = 0L;
        try {
            num = cardMapper.selectCardCount();
        }catch (RuntimeException e){
            throw new CardException("获取卡数量失败",e);
        }
        return num;
    }

    @Override
    public List<String> getCardIdByFuzzyQuery(String memberId,String cardId) throws CardException {
        List<String> cardList  = null;
        try {
            cardList = cardMapper.selectCardIdByFuzzyQuery(memberId,cardId);
        }catch (RuntimeException e){
            throw new CardException("获取会员卡id失败",e);
        }
        return cardList;
    }

    @Override
    public Card getCardByCardId(String cardId) throws CardException {
        Card card = null;
        try {
            card = cardMapper.selectCardById(cardId);
        }catch (RuntimeException e){
            throw new CardException("会员卡id不存在",e);
        }
        return card;
    }


}
