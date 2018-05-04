package team11.mobileiot.myooz.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends AsyncTask<String, Void, Object> {
    final private static String BACKEND_URL = "http://ec2-34-227-148-107.compute-1.amazonaws.com:8080";
    private NetworkTaskHandler handler;

    public NetworkTask(NetworkTaskHandler handler) {
        this.handler = handler;
    }

    @Override
    protected Object doInBackground(String... strings) {
        String className = handler.className;
        Class<?> clazz = null;
        try{
            clazz = Class.forName(className);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (clazz == null) return null;
        if (clazz == JSONObject.class || clazz == JSONArray.class) {
            return ApiJSON(clazz, strings);
        } else if (clazz == Bitmap.class) {
            return downloadBitmap(strings);
        }
        else
            return null;
    }

    private Object ApiJSON(Class<?> clazz, String[] strings){
        Object result = null;
        if (strings.length < 2) return result;
        String method = strings[0];
        String reqUrl = strings[1];
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL(BACKEND_URL + reqUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        switch (method.toUpperCase()) {
            case "GET":
            case "DELETE":
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    result = clazz.getConstructor(String.class).newInstance(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "POST":
            case "PUT":
                if (strings.length < 3) return result;
                try {
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                    osw.write(strings[2]);
                    osw.flush();
                    osw.close();
                    String message = "{\"message\": \"\"}";
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                        message = "{\"message\": \"Failed: " + con.getResponseMessage() + "\"}";
                    result = clazz.getConstructor(String.class).newInstance(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return result;
    }

    private Object downloadBitmap(String[] strings){
        Object result = null;
        if (strings.length < 1) return result;
        String reqUrl = strings[0];
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            result = BitmapFactory.decodeStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Object object) {
        if (handler == null) return;
        if (object != null)
            handler.onReady(object);
    }
}
