package com.example.apple.haidilao;

public class JsonObject {
    private String userName;
    private String password;
    private String level;
    int smallTableNumber;
    int middleTableNumber;
    int bigTableNumber;
    int tablechosenwaitenumber;
    String waiteNumber;
    int requesttype;
    int requesttypenumber;
    int cancleTableType;
    String cancleQueueNumber;


    JsonObject(int requesttype){
        this.requesttype = requesttype;
    }

    public int getCancleTableType() {
        return cancleTableType;
    }

    public String getCancleQueueNumber() {
        return cancleQueueNumber;
    }

    public void setCancleQueueNumber(String cancleQueueNumber) {
        this.cancleQueueNumber = cancleQueueNumber;
    }

    public void setCancleTableType(int cancleTableType) {
        this.cancleTableType = cancleTableType;
    }

    public void setRequesttypenumber(int requesttypenumber) {
        this.requesttypenumber = requesttypenumber;
    }

    public int getRequesttypenumber() {
        return requesttypenumber;
    }

    public int getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(int requesttype) {
        this.requesttype = requesttype;
    }

    public void setBigTableNumber(int bigTableNumber) {
        this.bigTableNumber = bigTableNumber;
    }

    public void setMiddleTableNumber(int middleTableNumber) {
        this.middleTableNumber = middleTableNumber;
    }

    public void setSmallTableNumber(int smallTableNumber) {
        this.smallTableNumber = smallTableNumber;
    }

    public void setTablechosenwaitenumber(int tablechosenwaitenumber) {
        this.tablechosenwaitenumber = tablechosenwaitenumber;
    }

    public int getTablechosenwaitenumber() {
        return tablechosenwaitenumber;
    }

    public String getWaiteNumber() {
        return waiteNumber;
    }

    public void setWaiteNumber(String waiteNumber) {
        this.waiteNumber = waiteNumber;
    }

    public int getbigTableNumber() {
        return bigTableNumber;
    }

    public int getMiddleTableNumber() {
        return middleTableNumber;
    }

    public int getSmallTableNumber() {
        return smallTableNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


}
