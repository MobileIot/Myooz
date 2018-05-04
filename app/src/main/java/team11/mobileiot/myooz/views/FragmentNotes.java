package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.*;

/**
 * Created by flora on 5/3/18.
 */

public class FragmentNotes  extends Fragment {
    public static FragmentNotes newInstance() {
        FragmentNotes fragment = new FragmentNotes();
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_note, container, false);

        String type=getArguments().getString("type");
        String id=getArguments().getString(type);

        Note.GetNotesByType(type, id, new NetworkTaskHandler<List<Note>>() {
            @Override
            public void onReady(List<Note> result) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                NoteAdapter adapter = new NoteAdapter(result);
                recyclerView.setAdapter(adapter);
            }
        });
        return  v;
    }


}
