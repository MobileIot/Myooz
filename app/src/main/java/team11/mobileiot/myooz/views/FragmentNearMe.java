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

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.NetworkTaskHandler;

public class FragmentNearMe extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_near_me, container, false);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        Artwork.GetArtworksByMuseumAndRoom(1, 0, new NetworkTaskHandler<List<Artwork>>() {
            @Override
            public void onReady(List<Artwork> result) {
                NearMeAdapter adapter = new NearMeAdapter(result);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                Interval interval = new Interval(4);
                recyclerView.addItemDecoration(interval);
                recyclerView.setAdapter(adapter);
            }
        });
        return v;
    }


}
