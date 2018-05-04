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
import java.util.HashMap;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.NetworkTaskHandler;
import team11.mobileiot.myooz.models.Note;

public class FragmentMyNotes extends Fragment {

    private RecyclerView recyclerView;
    private NearMeAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_note, container, false);
        final RecyclerView recyclerView=(RecyclerView) v.findViewById(R.id.recyclerview);

        final List<Note> data = new ArrayList<Note>();

        Note.GetNoteByArtist("1", new NetworkTaskHandler<List<Note>>() {
            @Override
            public void onReady(List<team11.mobileiot.myooz.models.Note> result) {
                HashMap<String, Note> map = new HashMap<>();
                for (Note note : result) {
                    if(!map.containsKey(note.artwork_id)) {
                        map.put(note.artwork_id, note);
                        data.add(note);
                        MyNoteAdapter adapter = new MyNoteAdapter(data);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        Interval interval = new Interval(4);
                        recyclerView.addItemDecoration(interval);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });
        return v;
    }

}
