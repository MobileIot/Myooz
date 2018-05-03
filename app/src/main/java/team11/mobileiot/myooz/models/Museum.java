package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Museum implements Parcelable {
    public String id;
    public String name;
    public String avatar;

    public Museum(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    protected Museum(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Museum> CREATOR = new Creator<Museum>() {
        @Override
        public Museum createFromParcel(Parcel in) {
            return new Museum(in);
        }

        @Override
        public Museum[] newArray(int size) {
            return new Museum[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(avatar);
    }
}
