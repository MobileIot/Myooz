package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import team11.mobileiot.myooz.R;

public class FragmentNearMe extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_near_me, container, false);
        RecyclerView recyclerView=(RecyclerView) v.findViewById(R.id.recyclerview);

        //PicList data=new PicList();
        List<String> data = new ArrayList<>();
        data.add("https://pi.tedcdn.com/r/tedideas.files.wordpress.com/2017/05/featured_art_heal_forests.jpg");
        data.add("https://i.pinimg.com/736x/c8/45/d8/c845d8ddcbccf0c874eff927b4d754fe--vintage-nature-photography-travel-photography.jpg");
        data.add("https://images.metmuseum.org/CRDImages/cl/web-large/DP102839.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DP135156.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ci/web-large/DT436.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ao/web-large/DT1276.jpg");
        data.add("https://images.metmuseum.org/CRDImages/aa/web-large/37.131.4_002Sept2014.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ep/web-large/DP287624.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DT1432.jpg");
        RecyclerAdapter adapter = new RecyclerAdapter(data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Interval interval = new Interval(4);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
