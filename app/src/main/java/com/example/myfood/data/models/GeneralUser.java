package com.example.myfood.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("priceBreakfast")
    @Expose
    private Integer priceBreakfast;
    @SerializedName("priceLunch")
    @Expose
    private Integer priceLunch;
    @SerializedName("priceTeatime")
    @Expose
    private Integer priceTeatime;
    @SerializedName("groupNum")
    @Expose
    private Integer groupNum;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("classNum")
    @Expose
    private String classNum;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("chargeable")
    @Expose
    private Boolean chargeable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPriceBreakfast() {
        return priceBreakfast;
    }

    public void setPriceBreakfast(Integer priceBreakfast) {
        this.priceBreakfast = priceBreakfast;
    }

    public Integer getPriceLunch() {
        return priceLunch;
    }

    public void setPriceLunch(Integer priceLunch) {
        this.priceLunch = priceLunch;
    }

    public Integer getPriceTeatime() {
        return priceTeatime;
    }

    public void setPriceTeatime(Integer priceTeatime) {
        this.priceTeatime = priceTeatime;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getChargeable() {
        return chargeable;
    }

    public void setChargeable(Boolean chargeable) {
        this.chargeable = chargeable;
    }

}
