package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;
import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.ArtworkCollection;
import team11.mobileiot.myooz.models.ArtworkCollectionRetrievalTask;
import team11.mobileiot.myooz.models.ArtworkCollectionRetrievalTaskDelegate;

public class FragmentNearMe extends Fragment {

    private RecyclerView recyclerView;
    private NearMeAdapter nearMeAdapter;
    private ArrayList<Artwork> artworks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_near_me, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        artworks = getArguments().getParcelableArrayList("artworks");
        NearMeAdapter adapter = new NearMeAdapter(artworks);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Interval interval = new Interval(4);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);
        return v;
    }


}
