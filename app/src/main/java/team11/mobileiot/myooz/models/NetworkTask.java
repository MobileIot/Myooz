package team11.mobileiot.myooz.models;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        Object result = null;
        if (strings.length < 2) return result;
        String className = handler.className;
        try {
            URL url = new URL(BACKEND_URL + strings[1]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(strings[0]);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result = Class.forName(className).getConstructor(String.class).newInstance(response.toString());
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
