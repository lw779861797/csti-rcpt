package com.cstiweb.rcpt.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Question implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "问题类型")
    private Integer type;

    @ApiModelProperty(value = "音频地址")
    private String vedio;

    @ApiModelProperty(value = "图片地址")
    private String img;

    @ApiModelProperty(value = "高低，研究生级别")
    private Integer levels;

    @ApiModelProperty(value = "初赛或复赛")
    private Integer family;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", vedio=").append(vedio);
        sb.append(", img=").append(img);
        sb.append(", levels=").append(levels);
        sb.append(", family=").append(family);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}