package team11.mobileiot.myooz.views;

import android.graphics.BitmapFactory;
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

import java.io.InputStream;
import java.util.ArrayList;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artiest;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.ArtworkCollectionRetrievalTaskDelegate;
import team11.mobileiot.myooz.models.LocationChangeDelegate;
import team11.mobileiot.myooz.models.ArtworkCollection;

/**
 * Created by flora on 5/2/18.
 */

public class ActivistyLists extends AppCompatActivity {

    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private ArtworkCollection artworkCollection;
    public static boolean needBeaconUpdate = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            needBeaconUpdate = false;
            switch (item.getItemId()) {
                case R.id.navigation_place:
                    needBeaconUpdate = true;
                    Bundle bundle = new Bundle();
                    ArrayList<Artwork> artworks = artworkCollection.getArtworks(0, 10);
                    bundle.putParcelableArrayList("artworks", artworks);
                    fragment = new FragmentNearMe();
                    fragment.setArguments(bundle);
                    break;
                case R.id.navigation_popular:
                    fragment = new FragmentSearch();
                    break;
                case R.id.navigation_thought:
                    fragment = new FragmentMyThought();
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
        RecyclerView recyclerView = findViewById(R.id.lists_recyclerview);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        ArrayList<Artiest> artiests = new ArrayList<>();
        InputStream is=getResources().openRawResource(R.raw.rambrandt);
        Artiest artiests1= new Artiest("Rambrant", BitmapFactory.decodeStream(is));
        is=getResources().openRawResource(R.raw.amedeo_modigliani);
        Artiest artiests2= new Artiest("Amedeo Modigliani", BitmapFactory.decodeStream(is));
        is=getResources().openRawResource(R.raw.edouard_manet);
        Artiest artiests3= new Artiest("Edouard Manet", BitmapFactory.decodeStream(is));
        is=getResources().openRawResource(R.raw.edward_steichen_brancusi);
        Artiest artiests4= new Artiest("Edward Steichen", BitmapFactory.decodeStream(is));
        is=getResources().openRawResource(R.raw.raphael);
        Artiest artiests5= new Artiest("Raphael", BitmapFactory.decodeStream(is));
        is=getResources().openRawResource(R.raw.van_goah);
        Artiest artiests6= new Artiest("Van Goah", BitmapFactory.decodeStream(is));
        artiests.add(artiests1);
        artiests.add(artiests2);
        artiests.add(artiests3);
        artiests.add(artiests4);
        artiests.add(artiests5);
        artiests.add(artiests6);
        artiests.add(artiests1);
        artiests.add(artiests2);
        artiests.add(artiests3);
        artiests.add(artiests4);
        artiests.add(artiests5);
        artiests.add(artiests6);
        artiests.add(artiests1);
        artiests.add(artiests2);
        artiests.add(artiests3);
        artiests.add(artiests4);
        artiests.add(artiests5);
        artiests.add(artiests6);
        ListAdapter adapter = new ListAdapter(artiests);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        Interval interval = new Interval(0);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);

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
