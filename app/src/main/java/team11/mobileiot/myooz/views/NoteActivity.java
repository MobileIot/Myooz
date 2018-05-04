package team11.mobileiot.myooz.views;


import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;


import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;
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
        final Button reportButton = this.findViewById(R.id.comment_report);

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

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Improve this one
                View popView = LayoutInflater.from(view.getContext()).inflate(R.layout.report_window, null);
                final PopupWindow popupWindow = new PopupWindow(popView, 1000, 1000);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                final Button cancelButton = popView.findViewById(R.id.close_button);
                final Button reportButton = popView.findViewById(R.id.report_button);
                final EditText reportText = popView.findViewById(R.id.report_text);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                reportButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reportText.setText("We will handle the report within 24 hours. Meanwhile, if you have any concern, please call 230-123-2345");
                        reportText.setKeyListener(null);
                        cancelButton.setVisibility(View.INVISIBLE);
                        reportButton.setText("OK");
                        reportButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
            }
        });

    }
}