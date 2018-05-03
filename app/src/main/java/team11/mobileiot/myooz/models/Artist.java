package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Artist implements Parcelable {
    public int id;
    public String name;
    public String avatar;

    public Artist(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    protected Artist(Parcel in) {
        id = in.readInt();
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(avatar);
    }

    public static void GetArtistByID(int id, final NetworkTaskHandler<Artist> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONObject>() {
            @Override
            public void onReady(JSONObject result) {
                nto.onReady(JSONObject2Artist(result));
            }
        }).execute("GET", "/artists/" + id, "JSONObject");
    }

    public static void GetArtist( final NetworkTaskHandler<List<Artist>> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONArray>() {
            @Override
            public void onReady(JSONArray result) {
                nto.onReady(JSONArray2ArtistList(result));
            }
        }).execute("GET", "/artists", "JSONArray");
    }

    private static Artist JSONObject2Artist(JSONObject json) {
        Artist result = null;
        try {
            int id = json.getInt("id");
            String name = json.getString("name");
            String avatar = json.getString("avatar");
            result = new Artist(id, name, avatar);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }

    private static List<Artist> JSONArray2ArtistList(JSONArray jsons) {
        List<Artist> result = new ArrayList<>();
        for (int i = 0; i < jsons.length(); i++) {
            try {
                JSONObject json = jsons.getJSONObject(i);
                int id = json.getInt("id");
                String name = json.getString("name");
                String avatar = json.getString("avatar");
                result.add(new Artist(id, name, avatar));
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return result;
    }
}
