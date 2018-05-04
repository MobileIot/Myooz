package team11.mobileiot.myooz.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;
import team11.mobileiot.myooz.models.NetworkTask;
import team11.mobileiot.myooz.models.NetworkTaskHandler;
import team11.mobileiot.myooz.models.Note;

/**
 * Created by flora on 5/3/18.
 */

public class ModifyNoteActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);
        final ImageView artworkPic=findViewById(R.id.modifynote_artworkpic);
        final int isnewnote = getIntent().getIntExtra("newNote",0);
        final String artworkid;
        final EditText editText = findViewById(R.id.modifynote_text);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);

        final Map<String, String> map = new HashMap<>();
        map.put("is_public", "0");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                int id = group.getCheckedRadioButtonId();
                RadioButton chosed = findViewById(id);
                if (chosed.getText().toString() == "Public") {
                    map.put("is_public", "1");
                } else{
                    map.put("is_public", "0");
                }
            }
        });

        if (isnewnote == 0) {
            Note note = getIntent().getParcelableExtra("note");
            new NetworkTask(new NetworkTaskHandler<Bitmap>() {
                @Override
                public void onReady(Bitmap result) {
                    artworkPic.setImageBitmap(result);
                }
            }).execute(note.avatar);
            artworkid = note.artwork_id;
            editText.setText(note.content);
        } else {
            Bitmap bitmap = getIntent().getParcelableExtra("pic");
            artworkPic.setImageBitmap(bitmap);
            artworkid = getIntent().getStringExtra("artwork_id");
        }

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

        final Button savebutton=findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isnewnote==0) {
                    Note note = getIntent().getParcelableExtra("note");
                    Note.UpdateNote(note.artwork_id, editText.getText().toString(), new NetworkTaskHandler<Boolean>() {
                        @Override
                        public void onReady(Boolean result) {
                            if (result)
                                finish();
                            else
                                Toast.makeText(ModifyNoteActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Note.CreateNote(editText.getText().toString(), artworkid, map.get("is_public"), (Bitmap) getIntent().getParcelableExtra("pic"), new NetworkTaskHandler<Boolean>() {
                        @Override
                        public void onReady(Boolean result) {
                            if (result)
                                finish();
                            else
                                Toast.makeText(ModifyNoteActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
