package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.NetworkTaskHandler;

/**
 * Created by flora on 5/2/18.
 */

public class ActivityLists extends AppCompatActivity {

    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    public static boolean needBeaconUpdate = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            needBeaconUpdate = false;
            switch (item.getItemId()) {
                case R.id.navigation_place:
                    needBeaconUpdate = true;
                    fragment = new FragmentNearMe();
                    break;
                case R.id.navigation_popular:
                    fragment = new FragmentSearch();
                    break;
                case R.id.navigation_thought:
                    fragment = new FragmentMyNotes();
                    break;
                default:
                    return false;
            }
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        Artist.GetArtist(new NetworkTaskHandler<List<Artist>>(){
            @Override
            public void onReady(List<Artist> result) {
                RecyclerView recyclerView = findViewById(R.id.lists_recyclerview);
                ListAdapter adapter = new ListAdapter(result);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                Interval interval = new Interval(0);
                recyclerView.addItemDecoration(interval);
                recyclerView.setAdapter(adapter);
            }
        });



        TopBar topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                finish();
            }

            @Override
            public void OnRightButtonClick() {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
