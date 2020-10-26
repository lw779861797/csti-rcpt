package com.cstiweb.rcpt.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Player implements Serializable {
    @ApiModelProperty(value = "选手编号")
    private String id;

    @ApiModelProperty(value = "选手姓名")
    private String name;

    @ApiModelProperty(value = "选手等级")
    private Integer levels;

    @ApiModelProperty(value = "初赛成绩")
    private BigDecimal preContest;

    @ApiModelProperty(value = "0为未删除 1为删除")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public BigDecimal getPreContest() {
        return preContest;
    }

    public void setPreContest(BigDecimal preContest) {
        this.preContest = preContest;
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
        sb.append(", name=").append(name);
        sb.append(", levels=").append(levels);
        sb.append(", preContest=").append(preContest);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}