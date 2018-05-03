package team11.mobileiot.myooz.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by flora on 5/2/18.
 */

public class Artiest implements Parcelable {
    //public String imageUrl;
    public String name;
    public Bitmap bitmap;

    public Artiest(String name, Bitmap bitmap) {
        this.name = name;
        //this.imageUrl = imageUrl;
        this.bitmap = bitmap;
    }


    protected Artiest(Parcel in) {
        //imageUrl = in.readString();
        name = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());;
    }
    public static final Creator<Artiest> CREATOR = new Creator<Artiest>() {
        @Override
        public Artiest createFromParcel(Parcel in) {
            return new Artiest(in);
        }

        @Override
        public Artiest[] newArray(int size) {
            return new Artiest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeValue(bitmap);
    }
}
