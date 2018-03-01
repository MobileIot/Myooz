package team11.mobileiot.myooz.models;

import java.util.ArrayList;

/**
 * Created by Zhongyi on 28/02/2018.
 */

public interface ArtworkCollectionRetrievalTaskDelegate {
    public void onArtworkRetrievalTaskComplete(ArrayList<Artwork> artworks);
}
