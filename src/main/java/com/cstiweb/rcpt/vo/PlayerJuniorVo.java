package com.cstiweb.rcpt.vo;

import java.math.BigDecimal;

public class PlayerJuniorVo extends PlayerVo {
    //俄汉双向翻译成绩
    private BigDecimal translation;

    //回答连贯问题成绩
    private BigDecimal constant;

    public BigDecimal getTranslation() {
        return translation;
    }

    public void setTranslation(BigDecimal translation) {
        this.translation = translation;
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }
}
