package team11.mobileiot.myooz.views;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.content.Intent;
import java.io.InputStream;

import team11.mobileiot.myooz.R;

public class FragmentSearch extends Fragment{

    private RecyclerView recyclerView;
    private NearMeAdapter recyclerAdapter;
    private ImageView artist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_search, container, false);
        artist=  v.findViewById(R.id.search_artists);
        InputStream is=getResources().openRawResource(R.raw.rambrandt);
        artist.setImageBitmap(BitmapFactory.decodeStream(is));

        ImageView movement=v.findViewById(R.id.search_artmovement);
        is=getResources().openRawResource(R.raw.artmovement);
        movement.setImageBitmap(BitmapFactory.decodeStream(is));

        ImageView museum=v.findViewById(R.id.search_museums);
        is=getResources().openRawResource(R.raw.museum);
        museum.setImageBitmap(BitmapFactory.decodeStream(is));

        artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(artist.getContext(), ListsActivity.class);
                artist.getContext().startActivity(intent);
            }
        });

        return v;
    }

}
