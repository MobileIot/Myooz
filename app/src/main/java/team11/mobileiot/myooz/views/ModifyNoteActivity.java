package team11.mobileiot.myooz.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import team11.mobileiot.myooz.models.Note;

/**
 * Created by flora on 5/3/18.
 */

public class ModifyNoteActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);
        ViewGroup pic=findViewById(R.id.modifynote_artworkpic);
        final int isnewnote = getIntent().getParcelableExtra("newNote");
        final String artworkid;
        final HashMap<String, String> map = new HashMap<>();
        if (isnewnote == 0) {
            Note note = getIntent().getParcelableExtra("note");
            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(this);
            simpleDraweeView.setImageURI(note.avatar);
            pic.addView(simpleDraweeView);
            artworkid=note.artwork_id;

        } else {
            byte[] bis = getIntent().getParcelableExtra("pic");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis,0,bis.length);
            ImageView imageView=new ImageView(this);
            imageView.setImageBitmap(bitmap);
            pic.addView(imageView);
            artworkid=getIntent().getParcelableExtra("artworkid");
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

        Button savebutton=findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isnewnote==1) {

                    map.put("is_public", "0");
                    RadioGroup radioGroup = findViewById(R.id.radioGroup);
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int i) {
                            int id = group.getCheckedRadioButtonId();
                            RadioButton chosed = findViewById(id);
                            if (chosed.getText().toString() == "Public") {
                                map.put("is_public", "1");
                            }
                        }
                    });
                    EditText editText = findViewById(R.id.modifynote_text);
                    map.put("content", editText.getText().toString());
                    map.put("artwork_id", artworkid);

                }else{

                }
            }
        });

    }
}
