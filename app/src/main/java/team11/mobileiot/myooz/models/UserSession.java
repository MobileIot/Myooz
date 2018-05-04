package team11.mobileiot.myooz.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserSession {
    public static String avatar;

    public static void Register(String username, String password, final NetworkTaskHandler<Boolean> nto) {
        JSONObject json = null;
        JSONArray jsonArray = null;
        try {
            json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            jsonArray = new JSONArray();
            jsonArray.put(json);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        new NetworkTask(new NetworkTaskHandler<JSONObject>() {
            @Override
            public void onReady(JSONObject result) {
                try {
                    UserSession.avatar = result.getString("avatar");
                    nto.onReady(true);
                } catch (Exception e) {
                    nto.onReady(false);
                }
            }
        }).execute("POST", "/register", jsonArray.toString());
    }

    public static void Login(String username, String password, final NetworkTaskHandler<Boolean> nto) {
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        new NetworkTask(new NetworkTaskHandler<JSONObject>() {
            @Override
            public void onReady(JSONObject result) {
                try {
                    UserSession.avatar = result.getString("avatar");
                    nto.onReady(true);
                } catch (Exception e) {
                    nto.onReady(false);
                }
            }
        }).execute("POST", "/login", json.toString());
    }
}
