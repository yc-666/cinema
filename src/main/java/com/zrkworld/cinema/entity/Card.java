package com.zrkworld.cinema.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author TXG
 * @date 2022/8/7$
 * <p>
 */
@Data
public class Card  {
    /**
     * 与数据库的cardid映射
     */

    private String cardId;
    /**
     * =与数据库的memberid映射
     */

    private String memberId;

    private Integer balance;

    private Integer integral;

    private Integer lose;

}