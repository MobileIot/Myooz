package team11.mobileiot.myooz.models;

import java.util.ArrayList;

/**
 * Created by Zhongyi on 28/02/2018.
 * Artwork collection.
 */

public class ArtworkCollection {

    private ArrayList<Artwork> artworks;

    public void setArtworks(ArrayList<Artwork> artworks) {
        this.artworks = artworks;
    }

    public ArrayList<Artwork> getArtworks(int pageIndex, int itemsInPage) {
        int minIndex = Math.min(pageIndex * itemsInPage, this.artworks.size());
        int maxIndex = Math.min(minIndex + itemsInPage, this.artworks.size());
        return new ArrayList<Artwork>(this.artworks.subList(minIndex, maxIndex));
    }
}
