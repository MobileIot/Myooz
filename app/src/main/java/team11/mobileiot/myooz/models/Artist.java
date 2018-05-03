package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
    public String id;
    public String name;
    public String avatar;

    public Artist(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    protected Artist(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
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
