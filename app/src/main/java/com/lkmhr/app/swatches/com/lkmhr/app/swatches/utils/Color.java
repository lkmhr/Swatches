package com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils;

public class Color {
    private int id;
    private String hexCode;
    private String name;
    private int groupId;
    private int r;
    private int g;
    private int b;

    public Color(){}
    public Color(String name, String hex){
        this.name = name;
        this.hexCode = hex;
    }
    public Color(String name, String hex,int gId){
        this.name = name;
        this.hexCode = hex;
        this.groupId = gId;
    }
    public Color(String name, int r, int g, int b){
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getId() { return id; }

    public void setId(int idd) { this.id = id; }

    public int getGroupId() { return groupId; }

    public void setGroupId(int groupId) { this.groupId = groupId; }
}
