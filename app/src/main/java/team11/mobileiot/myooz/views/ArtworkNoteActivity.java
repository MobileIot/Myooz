package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.NetworkTaskHandler;

/**
 * Created by flora on 5/3/18.
 */

public class ArtworkNoteActivity extends AppCompatActivity {
    private Fragment fragment;
    private Bundle arguments;

    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_notes);
        final SimpleDraweeView image = findViewById(R.id.item_simpleDraweeView);
        Artwork artwork = getIntent().getParcelableExtra("artwork");
        image.setImageURI(artwork.avatar);
        final TextView artworkNameTextView = findViewById(R.id.info_title);
        artworkNameTextView.setText(artwork.name);
        final TextView artistNameTextView = findViewById(R.id.info_artist);
        Artist.GetArtistByID(artwork.artist_id, new NetworkTaskHandler<Artist>() {
            @Override
            public void onReady(Artist result) {
                artistNameTextView.setText(result.name);
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("TIME"));
        tabLayout.addTab(tabLayout.newTab().setText("LIKES"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = (String) tab.getText();
                if (tabText.equals("TIME")) {
                    fragment = new FragmentNotes();
                } else if (tabText.equals("LIKED")) {
                    fragment = new FragmentNotes();
                } else return;
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragment = new FragmentNotes();
        arguments = new Bundle();
        arguments.putString("type", "artwork");
        arguments.putString("artwork", artwork.id);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, fragment).commit();
    }
}
