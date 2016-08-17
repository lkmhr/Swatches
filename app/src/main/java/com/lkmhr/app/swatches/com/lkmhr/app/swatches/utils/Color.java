package com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils;

import android.os.Parcel;
import android.os.Parcelable;


public class Color implements Parcelable{
    private int id;
    private String hexCode;
    private String name;
    private int groupId;

    public Color(){}

    public Color(String name, String hex,int gId){
        this.name = name;
        this.hexCode = hex;
        this.groupId = gId;
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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getGroupId() { return groupId; }

    public void setGroupId(int groupId) { this.groupId = groupId; }

    public static String makeRGBtoHex(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }
    public static int makeHexToRGB(String hex) {
        return android.graphics.Color.parseColor(hex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.hexCode);
        dest.writeInt(this.groupId);
    }

    public static final Parcelable.Creator<Color> CREATOR = new Parcelable.Creator<Color>() {
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }

        public Color[] newArray(int size) {
            return new Color[size];
        }
    };

    private Color(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.hexCode = in.readString();
        this.groupId = in.readInt();
    }
}
