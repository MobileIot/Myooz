package team11.mobileiot.myooz.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import team11.mobileiot.myooz.R;

public class NewCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        NewCommentTopBar topbar = (NewCommentTopBar) findViewById(R.id.new_comment_topbar);
        topbar.setOnLeftAndRightClickListener(new NewCommentTopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                finish();
            }

            @Override
            public void OnRightButtonClick() {
                finish();
            }
        });
    }
}
