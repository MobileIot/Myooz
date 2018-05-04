package team11.mobileiot.myooz.views;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;


import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.NetworkTaskHandler;
import team11.mobileiot.myooz.models.Note;

public class NoteActivity extends AppCompatActivity {
    private Note note;

    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        note = getIntent().getParcelableExtra("note");
        final SimpleDraweeView noteImage = this.findViewById(R.id.note_image);
        final TextView commentText = this.findViewById(R.id.comment_content);
        final TextView time = this.findViewById(R.id.time);
        final TextView numkudos = this.findViewById(R.id.kudosnumber);
        final TextView profileName = this.findViewById(R.id.profile_name);
        final SimpleDraweeView profileImage = this.findViewById(R.id.profile_image);

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float) imageInfo.getHeight();
                noteImage.setAspectRatio(ratio);
            }
        };
        noteImage.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setUri(Uri.parse(note.avatar)).build());
        commentText.setText(note.content);
        time.setText("2017-11-17");
        numkudos.setText("0");

        Artist.GetArtistByID("1", new NetworkTaskHandler<Artist>() {
            @Override
            public void onReady(Artist result) {
                profileImage.setController(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(result.avatar)).build());
                profileName.setText(result.name);
            }
        });

        final TopBar topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                finish();
            }

            @Override
            public void OnRightButtonClick() {
                PopupMenu popupMenu = new PopupMenu(NoteActivity.this, topBar, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.note_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.edit_note:
                                Intent intent = new Intent(NoteActivity.this, ModifyNoteActivity.class);
                                intent.putExtra("note", note);
                                NoteActivity.this.startActivity(intent);
                                break;
                            case R.id.delete_note:
                                break;
                            case R.id.report_note:
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


    }
}