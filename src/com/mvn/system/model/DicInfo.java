package com.mvn.system.model;

import java.util.Date;

public class DicInfo {
    private Integer id;

    private String code;

    private String name;

    private String type;

    private String url;

    private String target;

    private Integer parId;

    private Integer orderNo;

    private String description;

    private String isavaliable;

    private String ispermissions;

    private String icon;

    private String isLevel;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public Integer getParId() {
        return parId;
    }

    public void setParId(Integer parId) {
        this.parId = parId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIsavaliable() {
        return isavaliable;
    }

    public void setIsavaliable(String isavaliable) {
        this.isavaliable = isavaliable == null ? null : isavaliable.trim();
    }

    public String getIspermissions() {
        return ispermissions;
    }

    public void setIspermissions(String ispermissions) {
        this.ispermissions = ispermissions == null ? null : ispermissions.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getIsLevel() {
        return isLevel;
    }

    public void setIsLevel(String isLevel) {
        this.isLevel = isLevel == null ? null : isLevel.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}