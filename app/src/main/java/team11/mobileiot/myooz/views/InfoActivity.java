package team11.mobileiot.myooz.views;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;

public class InfoActivity extends AppCompatActivity {
    private Fragment fragment;
    private Artwork artwork;
    private Bundle arguments;

    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        artwork = getIntent().getParcelableExtra("artwork");

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
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Discussion"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = (String) tab.getText();
                if (tabText.equals("Info")) {
                    fragment = new FragmentArtInfo();
                } else if (tabText.equals("Discussion")) {
                    fragment = new FragmentCommentDiscussion();
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


        fragment = new FragmentArtInfo();
        arguments = new Bundle();
        arguments.putParcelable("artwork", artwork);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, fragment).commit();
    }
}