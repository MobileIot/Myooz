package team11.mobileiot.myooz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import team11.mobileiot.myooz.beacons.BeaconService;

public class NearMe extends AppCompatActivity {
    private BeaconService bs;

    private RecyclerView recyclerView;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_place:

                    return true;
                case R.id.navigation_popular:
                    return true;
                case R.id.navigation_thought:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_near_me);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TopBar topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {

            @Override
            public void OnLeftButtonClick() {
                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnRightButtonClick() {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        List<String> data = new ArrayList<>();
        data.add("https://images.metmuseum.org/CRDImages/cl/web-large/DP102839.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DP135156.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ci/web-large/DT436.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ao/web-large/DT1276.jpg");
        data.add("https://images.metmuseum.org/CRDImages/aa/web-large/37.131.4_002Sept2014.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ep/web-large/DP287624.jpg");
        data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DT1432.jpg");

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerAdapter adapter = new RecyclerAdapter(data, getApplicationContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Interval interval = new Interval(30);
        recyclerView.addItemDecoration(interval);
        recyclerView.setAdapter(adapter);

        this.requestLocationAccess();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Meow", "coarse location permission granted");
                    this.initBeaconService(true);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.PER_DENY_TITLE);
                    builder.setMessage(R.string.PER_DENY_MSG);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // Do nothing
                        }
                    });
                    builder.show();
                    this.initBeaconService(false);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.bs.destroy();
    }

    private void requestLocationAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.PER_REQUEST_TITLE);
                builder.setMessage(R.string.PER_REQUEST_MSG);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        }, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            } else {
                this.initBeaconService(true);
            }
        } else {
            // The permission has already been written in the manifest.
            // Treat it as granted by default.
            this.initBeaconService(true);
        }
    }


    private void verifyBluetooth() {
        try {
            if (!this.bs.checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        System.exit(0);
                    }
                });
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    System.exit(0);
                }

            });
            builder.show();
        }
    }

    private void initBeaconService(Boolean permissionGranted) {
        if (permissionGranted) {
            this.bs = new BeaconService(this.getApplicationContext());
            this.verifyBluetooth();
        } else {
            this.bs = null;
        }
    }
}
