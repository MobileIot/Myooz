package team11.mobileiot.myooz.views;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;

public class InfoActivity extends AppCompatActivity {
    private Fragment fragment;
    private Artwork artwork;
    private Bundle arguments;
    static final int REQUEST_IMAGE_CAPTURE = 1;

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

        final SimpleDraweeView simpleDraweeView = this.findViewById(R.id.item_simpleDraweeView);
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(artwork.imageUrl)).build());
        simpleDraweeView.getHierarchy()
                .setActualImageFocusPoint(new PointF(0, 0));

        TextView title = this.findViewById(R.id.info_title);
        TextView artist = this.findViewById(R.id.info_artist);
        TextView category = this.findViewById(R.id.info_category);
        TextView content = this.findViewById(R.id.info_content);

        title.setText(artwork.title);
        artist.setText(artwork.artistInfo);
        category.setText(artwork.category);

        List<Artwork> parts;
        // TODO: Remove this hard-coded part
        parts = new ArrayList<>();
        parts.add(artwork);
        parts.add(artwork);
        parts.add(artwork);
        parts.add(artwork);

        RecyclerView recyclerView = this.findViewById(R.id.info_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        InfoAdapter adapter = new InfoAdapter(parts);
        recyclerView.setAdapter(adapter);

        ImageView cameraTextView = this.findViewById(R.id.image_camera);
        cameraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ImageView tempImageView = this.findViewById(R.id.temp_image);
                tempImageView.setImageBitmap(photo);
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
}