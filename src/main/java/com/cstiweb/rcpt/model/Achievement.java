package com.cstiweb.rcpt.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Achievement implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "关系表ID")
    private Integer playerRelationId;

    @ApiModelProperty(value = "问题成绩")
    private BigDecimal grade;

    @ApiModelProperty(value = "0为未删除 1为删除")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayerRelationId() {
        return playerRelationId;
    }

    public void setPlayerRelationId(Integer playerRelationId) {
        this.playerRelationId = playerRelationId;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", playerRelationId=").append(playerRelationId);
        sb.append(", grade=").append(grade);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}