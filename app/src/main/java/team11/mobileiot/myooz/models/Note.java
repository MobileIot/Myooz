package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    public String id;
    public String avatar;
    public String content;
    public String artwork_id;
    public String username;
    public String _public;

    public Note(String id, String avatar, String content, String artwork_id, String username, String _public) {
        this.id = id;
        this.avatar = avatar;
        this.content = content;
        this.artwork_id = artwork_id;
        this.username = username;
        this._public = _public;
    }

    protected Note(Parcel in) {
        id = in.readString();
        avatar = in.readString();
        content = in.readString();
        artwork_id = in.readString();
        username = in.readString();
        _public = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(avatar);
        parcel.writeString(content);
        parcel.writeString(artwork_id);
        parcel.writeString(username);
        parcel.writeString(_public);
    }
}
