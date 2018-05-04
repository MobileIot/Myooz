package team11.mobileiot.myooz.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static void GetNotesByID(String id, final NetworkTaskHandler<Note> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONObject>() {
            @Override
            public void onReady(JSONObject result) {
                nto.onReady(JSONObject2Note(result));
            }
        }).execute("GET", "/notes/" + id, "JSONObject");
    }

    public static void GetNoteByMuseumAndRoom(String museum_id, String room_id, final NetworkTaskHandler<List<Note>> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONArray>() {
            @Override
            public void onReady(JSONArray result) {
                nto.onReady(JSONArray2NoteList(result));
            }
        }).execute("GET", "/notes?museum_id=" + museum_id + "&room_id=" + room_id, "JSONArray");
    }

    public static void GetNoteByArtist(String artist_id, final NetworkTaskHandler<List<Note>> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONArray>() {
            @Override
            public void onReady(JSONArray result) {
                nto.onReady(JSONArray2NoteList(result));
            }
        }).execute("GET", "/notes?artist_id=" + artist_id, "JSONArray");
    }
    public static void GetNoteSelf(final NetworkTaskHandler<List<Note>> nto) {
        new NetworkTask(new NetworkTaskHandler<JSONArray>() {
            @Override
            public void onReady(JSONArray result) {
                nto.onReady(JSONArray2NoteList(result));
            }
        }).execute("GET", "/notes?my_notes_only=1", "JSONArray");
    }

    private static Note JSONObject2Note(JSONObject json) {
        Note result = null;
        try {
            String id = json.getString("id");
            String avatar = json.getString("avatar");
            String content = json.getString("content");
            String artwork_id = json.getString("artwork_id");
            String username = json.getString("username");
            String _public = json.getString("public");
            result = new Note(id, avatar, content, artwork_id, username, _public);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }

    private static List<Note> JSONArray2NoteList(JSONArray jsons) {
        List<Note> result = new ArrayList<>();
        for (int i = 0; i < jsons.length(); i++) {
            try {
                JSONObject json = jsons.getJSONObject(i);
                String id = json.getString("id");
                String avatar = json.getString("avatar");
                String content = json.getString("content");
                String artwork_id = json.getString("artwork_id");
                String username = json.getString("username");
                String _public = json.getString("public");
                result.add(new Note(id, avatar, content, artwork_id, username, _public));
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return result;
    }
}
