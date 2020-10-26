package com.cstiweb.rcpt.vo;

import com.cstiweb.rcpt.model.Player;

import java.math.BigDecimal;

public class PlayerVo extends Player {
    //名次
    private Integer ranking;

    //演讲成绩
    private BigDecimal speech;

    //口语表述成绩
    private BigDecimal spoken;

    //总分
    private BigDecimal sum;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public BigDecimal getSpeech() {
        return speech;
    }

    public void setSpeech(BigDecimal speech) {
        this.speech = speech;
    }

    public BigDecimal getSpoken() {
        return spoken;
    }

    public void setSpoken(BigDecimal spoken) {
        this.spoken = spoken;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
