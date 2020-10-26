package com.cstiweb.rcpt.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class QuestionRelation implements Serializable {
    private Integer id;

    private String name;

    @ApiModelProperty(value = "初赛或复赛")
    private Integer levels;

    @ApiModelProperty(value = "初赛或复赛")
    private Integer family;

    @ApiModelProperty(value = "是否删除 1 为删除  0 为未删除")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getFamily() {
        return family;
    }

    public void setFamily(Integer family) {
        this.family = family;
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
        sb.append(", family=").append(family);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}