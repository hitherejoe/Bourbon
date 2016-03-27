package com.hitherejoe.bourbon.common.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Shot implements Parcelable {

    public int id;
    public String title;
    public String description;
    public int width;
    public int height;
    public Image images;
    public String image;
    public String views_count;
    public String likes_count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeParcelable(this.images, flags);
        dest.writeString(this.image);
        dest.writeString(this.views_count);
        dest.writeString(this.likes_count);
    }

    public Shot() {
    }

    protected Shot(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.images = in.readParcelable(Image.class.getClassLoader());
        this.image = in.readString();
        this.views_count = in.readString();
        this.likes_count = in.readString();
    }

    public static final Parcelable.Creator<Shot> CREATOR = new Parcelable.Creator<Shot>() {
        @Override
        public Shot createFromParcel(Parcel source) {
            return new Shot(source);
        }

        @Override
        public Shot[] newArray(int size) {
            return new Shot[size];
        }
    };
}
