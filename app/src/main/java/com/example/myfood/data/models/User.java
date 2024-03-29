package com.example.myfood.data.models;

import android.util.Log;

import java.io.Serializable;

public class User implements Serializable {

    private String name, school, numberClass;
    private static final long serialVersionUID = 1L;
    private int priceBreakfast, priceTeatime, priceLunch, INK;
    private boolean isChargable;
    private int id;

    public User(int id, String name, String school, String numberClass, int priceBreakfast, int priceTeatime, int priceLunch, int INK, boolean isChargable) {
        this.name = name;
        this.school = school;
        this.numberClass = numberClass;
        this.priceBreakfast = priceBreakfast;
        this.priceTeatime = priceTeatime;
        this.priceLunch = priceLunch;
        this.INK = INK;
        this.isChargable = isChargable;
        this.id = id;
    }

    public User(UserForResponse user) {
        this.name = user.getName();
        this.school = user.getSchool();
        this.numberClass = user.getClassNum();
        this.priceBreakfast = user.getPriceBreakfast();
        this.priceTeatime = user.getPriceTeatime();
        this.priceLunch = user.getPriceLunch();
        this.INK = user.getGroupNum();
        this.isChargable = user.getChargeable();
        this.id = user.getId();

    }

    public String getName() {
        Log.d("MYTAG", this.name);
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getNumberClass() {
        return numberClass;
    }

    public int getPriceBreakfast() {
        return priceBreakfast;
    }

    public int getPriceTeatime() {
        return priceTeatime;
    }

    public int getPriceLunch() {
        return priceLunch;
    }

    public int getINK() {
        return INK;
    }

    public boolean isChargable() {
        return isChargable;
    }

    public void setPriceBreakfast(int priceBreakfast) {
        this.priceBreakfast = priceBreakfast;
    }

    public void setPriceTeatime(int priceTeatime) {
        this.priceTeatime = priceTeatime;
    }

    public void setPriceLunch(int priceLunch) {
        this.priceLunch = priceLunch;
    }

}
