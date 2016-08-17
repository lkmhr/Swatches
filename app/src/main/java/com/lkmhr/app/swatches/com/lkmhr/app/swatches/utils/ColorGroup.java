package com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class ColorGroup implements Parcelable{

    private int id;
    private String name;
    private String description;

    public ColorGroup(){}

    public ColorGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<ColorGroup> CREATOR = new Parcelable.Creator<ColorGroup>() {
        public ColorGroup createFromParcel(Parcel in) {
            return new ColorGroup(in);
        }

        public ColorGroup[] newArray(int size) {
            return new ColorGroup[size];
        }
    };

    private ColorGroup(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }
}
