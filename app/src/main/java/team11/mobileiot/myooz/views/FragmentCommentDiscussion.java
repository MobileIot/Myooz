package team11.mobileiot.myooz.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team11.mobileiot.myooz.R;

public class FragmentCommentDiscussion extends Fragment {
    private FloatingActionButton floatingActionButton;

    public static FragmentCommentDiscussion newInstance() {
        FragmentCommentDiscussion fragment = new FragmentCommentDiscussion();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comment_disscussion, container, false);
        floatingActionButton = v.findViewById(R.id.floatingbtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewCommentActivity.class);
                getContext().startActivity(intent);
            }
        });

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);

        List<Comment> data = new ArrayList<Comment>();
        data.add(new Comment("https://images.metmuseum.org/CRDImages/cl/web-large/DP102839.jpg", "Masterpiece", "Although some suggest that The Old Guitarist is a depiction of pain and isolation in the extreme, I would argue that it ...", "02/28/2018", 299));
        data.add(new Comment("https://images.metmuseum.org/CRDImages/ma/web-large/DT1432.jpg", "Artwork", "No other painting can equal the immediate and incomprehensible adhesion to her beauty and …", "02/28/2018", 139));
        data.add(new Comment("https://images.metmuseum.org/CRDImages/ci/web-large/DT436.jpg", "No.3/No.13", "When I look at ‘No.3/No.13,’ I feel two things; peacefulness and fear. The black and green rectangles in the bottom …", "02/28/2018", 84));
        data.add(new Comment("https://images.metmuseum.org/CRDImages/ao/web-large/DT1276.jpg", "Isolation", "Although some suggest that The Old Guitarist is a depiction of pain and isolation in the extreme, I would argue that it ...", "02/28/2018", 66));
        data.add(new Comment("https://images.metmuseum.org/CRDImages/aa/web-large/37.131.4_002Sept2014.jpg", "Illusion", "Manet tried to release his viewers from the illusion created by academia surrounding nudity and art in general …", "02/28/2018", 15));

        DiscussionAdapter adapter = new DiscussionAdapter(data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        Interval interval = new Interval(4);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
