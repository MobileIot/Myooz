package team11.mobileiot.myooz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class NearMe extends AppCompatActivity {

    private RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_place:

                    return true;
                case R.id.navigation_popular:
                    return true;
                case R.id.navigation_thought:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_near_me);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TopBar topBar= (TopBar) findViewById(R.id.topbar);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {

            @Override
            public void OnLeftButtonClick() {
                Toast.makeText(getApplicationContext(),"left",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnRightButtonClick() {
                Toast.makeText(getApplicationContext(),"right",Toast.LENGTH_SHORT).show();
            }
        });

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

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerAdapter adapter=new RecyclerAdapter(data, getApplicationContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        Interval interval=new Interval(30);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);
    }

}
