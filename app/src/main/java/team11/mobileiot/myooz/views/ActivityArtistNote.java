package team11.mobileiot.myooz.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artiest;

/**
 * Created by flora on 5/3/18.
 */

public class ActivityArtistNote extends AppCompatActivity {
    private Fragment fragment;
    private Bundle arguments;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_notes);
        ImageView image=findViewById(R.id.artist_note_image);
        Artiest artiest=getIntent().getParcelableExtra("artist");
        image.setImageBitmap(artiest.bitmap);
        TextView name=findViewById(R.id.artiest_name);
        name.setText(artiest.name);

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

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tablayout);
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
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, fragment).commit();
    }
}
