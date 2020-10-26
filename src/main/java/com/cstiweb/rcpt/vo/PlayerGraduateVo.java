package com.cstiweb.rcpt.vo;

import java.math.BigDecimal;

//研究生
public class PlayerGraduateVo extends PlayerVo {

    //对话口译成绩
    private BigDecimal interpret;

    //看视频用俄语讲述内容成绩
    private BigDecimal recount;

    public BigDecimal getInterpret() {
        return interpret;
    }

    public void setInterpret(BigDecimal interpret) {
        this.interpret = interpret;
    }

    public BigDecimal getRecount() {
        return recount;
    }

    public void setRecount(BigDecimal recount) {
        this.recount = recount;
    }
}
