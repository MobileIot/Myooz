package team11.mobileiot.myooz.views;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

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

        final SimpleDraweeView simpleDraweeView = this.findViewById(R.id.item_simpleDraweeView);
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(artwork.imageUrl)).build());

        TextView title = this.findViewById(R.id.info_title);
        TextView artist = this.findViewById(R.id.info_artist);
        TextView category = this.findViewById(R.id.info_category);
        TextView content = this.findViewById(R.id.info_content);

        title.setText(artwork.title);
        artist.setText(artwork.artistInfo);
        category.setText(artwork.category);

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