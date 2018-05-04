package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhongyi on 28/02/2018.
 * Artwork model.
 */

public class Artwork implements Parcelable {
    public String id;
    public String name;
    public String artist_id;
    public String museum_id;
    public String year;
    public String description;
    public String avatar;
    public String room_id;

    public Artwork(String id, String name, String artist_id, String museum_id, String year, String description, String avatar, String room_id) {
        this.id = id;
        this.name = name;
        this.artist_id = artist_id;
        this.museum_id = museum_id;
        this.year = year;
        this.description = description;
        this.avatar = avatar;
        this.room_id = room_id;
    }

    protected Artwork(Parcel in) {
        id = in.readString();
        name = in.readString();
        artist_id = in.readString();
        museum_id = in.readString();
        year = in.readString();
        description = in.readString();
        avatar = in.readString();
        room_id = in.readString();
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
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(artist_id);
        parcel.writeString(museum_id);
        parcel.writeString(year);
        parcel.writeString(description);
        parcel.writeString(avatar);
        parcel.writeString(room_id);
    }

    public static void GetArtworkByID(String id, final NetworkTaskHandler<Artwork> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONObject>() {
            @Override
            public void onReady(JSONObject result) {
                nto.onReady(JSONObject2Artwork(result));
            }
        }).execute("GET", "/artworks/" + id);
    }

    public static void GetArtworksByMuseumAndRoom(String museum_id, String room_id, final NetworkTaskHandler<List<Artwork>> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONArray>() {
            @Override
            public void onReady(JSONArray result) {
                nto.onReady(JSONArray2ArtworkList(result));
            }
        }).execute("GET", "/artworks?museum_id=" + museum_id + "&room_id=" + room_id);
    }


    private static Artwork JSONObject2Artwork(JSONObject json) {
        Artwork result = null;
        try {
            String id = json.getString("id");
            String name = json.getString("name");
            String artist_id = json.getString("artist_id");
            String museum_id = json.getString("museum_id");
            String year = json.getString("year");
            String description = json.getString("description");
            String avatar = json.getString("avatar");
            String room_id = json.getString("room_id");
            result = new Artwork(id, name, artist_id, museum_id, year, description, avatar, room_id);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }

    private static List<Artwork> JSONArray2ArtworkList(JSONArray jsons) {
        List<Artwork> result = new ArrayList<>();
        for (int i = 0; i < jsons.length(); i++) {
            try {
                JSONObject json = jsons.getJSONObject(i);
                String id = json.getString("id");
                String name = json.getString("name");
                String artist_id = json.getString("artist_id");
                String museum_id = json.getString("museum_id");
                String year = json.getString("year");
                String description = json.getString("description");
                String avatar = json.getString("avatar");
                String room_id = json.getString("room_id");
                result.add(new Artwork(id, name, artist_id, museum_id, year, description, avatar, room_id));
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return result;
    }
}
