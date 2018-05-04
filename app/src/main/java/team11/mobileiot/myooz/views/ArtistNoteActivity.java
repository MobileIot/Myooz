package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by flora on 5/3/18.
 */

public class ArtistNoteActivity extends AppCompatActivity {
    private Fragment fragment;
    private Bundle arguments;

    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_notes);
        SimpleDraweeView image = findViewById(R.id.artist_note_image);
        Artist artist = getIntent().getParcelableExtra("artist");
        image.setImageURI(artist.avatar);
        TextView name = findViewById(R.id.artist_name);
        name.setText(artist.name);

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
        arguments.putString("type", "artist");
        arguments.putString("artist", artist.id);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, fragment).commit();
    }
}
