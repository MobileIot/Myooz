package team11.mobileiot.myooz.models;

/**
 * Created by Zhongyi on 28/02/2018.
 * Artwork model.
 */

public class Artwork {
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
}
