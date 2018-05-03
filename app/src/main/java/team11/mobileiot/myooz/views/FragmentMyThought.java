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

public class FragmentMyThought extends Fragment {

    private RecyclerView recyclerView;
    private NearMeAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_near_me, container, false);
        RecyclerView recyclerView=(RecyclerView) v.findViewById(R.id.recyclerview);

        List<Note> data = new ArrayList<Note>();
        data.add(new Note("https://images.metmuseum.org/CRDImages/ma/web-large/DT1432.jpg","Picture","Although some suggest that The Old Guitarist is a depiction of pain and isolation in the extreme, I would argue that it ...","02/28/2018",2));

        NoteAdapter adapter = new NoteAdapter(data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        Interval interval = new Interval(4);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);
        return v;
    }

}
