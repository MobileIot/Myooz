package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhongyi on 28/02/2018.
 * Artwork model.
 */

public class Artwork implements Parcelable {
    public String imageUrl;
    public String id;
    public String artistInfo;
    public String title;
    public String category;

    public Artwork(String title, String id, String artistInfo, String category, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.artistInfo = artistInfo;
        this.title = title;
        this.category = category;
    }

    protected Artwork(Parcel in) {
        imageUrl = in.readString();
        id = in.readString();
        artistInfo = in.readString();
        title = in.readString();
        category = in.readString();
    }

    public static final Creator<Artwork> CREATOR = new Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUrl);
        parcel.writeString(id);
        parcel.writeString(artistInfo);
        parcel.writeString(title);
        parcel.writeString(category);
    }
}
