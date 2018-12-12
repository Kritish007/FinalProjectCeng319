package com.example.n01202172.myapplication;

public class Database {
    public String temp;
    public String bat;
    public String volt;
    public String charge;
    public String fruitname;
    public String timestamp;
    public String rgb;
    public Database() {

    }

    public Database(String temp,String bat,String volt,String charge,String timestamp,String rgb, String fruitname){

        this.temp = temp;
        this.bat = bat;
        this.volt = volt;
        this.charge = charge;
        this.timestamp = timestamp;
        this.rgb = rgb;
        this.fruitname = fruitname;

    }
    public String getFruitname() {
        return fruitname;
    }

    public void setFruitname(String fruitname){
        this.fruitname = fruitname;
    }
    public String getRgb() {

        return rgb;

    }

    public void setRgb(String rgb){

        this.rgb = rgb;

    }

    public String getCharge() {

        return charge;

    }

    public void setCharge(String charge){

        this.charge = charge;

    }

    public String getVolt() {

        return volt;

    }

    public void setVolt(String volt){

        this.volt = volt;

    }

    public String getBat() {

        return bat;

    }

    public void setBat(String bat){

        this.bat = bat;

    }
    public String getTemp() {

        return temp;

    }

    public void setTemp(String temp) {

        this.temp = temp;

    }
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
