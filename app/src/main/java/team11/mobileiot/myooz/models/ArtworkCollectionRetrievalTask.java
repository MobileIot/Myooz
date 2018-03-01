package team11.mobileiot.myooz.models;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ArtworkCollectionRetrievalTask extends AsyncTask<Void, Void, ArrayList<Artwork>> {
    private static final String DEFAULT_COLLECTION_URL =
            "https://raw.githubusercontent.com/MobileIot/met-data/master/met_objects_top_100.json";
    private ArtworkCollectionRetrievalTaskDelegate delegate;

    public ArtworkCollectionRetrievalTask(ArtworkCollectionRetrievalTaskDelegate delegate) {
        this.delegate = delegate;
    }

    protected ArrayList<Artwork> doInBackground(Void... voids) {
        ArrayList<Artwork> result = new ArrayList<>();
        try {
            URL url = new URL(DEFAULT_COLLECTION_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //Read JSON response and print

            JSONArray resultArray = new JSONArray(response.toString());
            return this.parseArtworks(resultArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(ArrayList<Artwork> artworks) {
        delegate.onArtworkRetrievalTaskComplete(artworks);
    }

    private ArrayList<Artwork> parseArtworks(JSONArray jsonData) {
        ArrayList<Artwork> result = new ArrayList<>();
        for (int i = 0; i < jsonData.length(); i += 1) {
            JSONObject object;
            try {
                object = jsonData.getJSONObject(i);
                String title = object.getString("title");
                String id = object.getString("id");
                String artistName = object.getString("artist_name");
                String artistBio = object.getString("artist_bio");
                String category = object.getString("dept");
                String imageUrl = object.getString("image");
                String artistInfo;
                if (artistName.length() == 0) {
                    artistInfo = "N/A";
                } else if (artistBio.length() == 0) {
                    artistInfo = artistName;
                } else {
                    artistInfo = artistName + ", " + artistBio;
                }
                Artwork artwork = new Artwork(title, id, artistInfo, category, imageUrl);
                result.add(artwork);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}