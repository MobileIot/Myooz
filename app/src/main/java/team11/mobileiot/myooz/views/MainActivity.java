package team11.mobileiot.myooz.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.beacons.BeaconService;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.ArtworkCollection;
import team11.mobileiot.myooz.models.ArtworkCollectionRetrievalTask;
import team11.mobileiot.myooz.models.ArtworkCollectionRetrievalTaskDelegate;
import team11.mobileiot.myooz.models.LocationChangeDelegate;


public class MainActivity extends AppCompatActivity implements ArtworkCollectionRetrievalTaskDelegate, LocationChangeDelegate {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BeaconService bs;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private ArtworkCollection artworkCollection;
    private int lastArea;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_place:
                    Bundle bundle = new Bundle();
                    ArrayList<Artwork> artworks = artworkCollection.getArtworks(0, 10);
                    bundle.putParcelableArrayList("artworks", artworks);
                    fragment = new FragmentNearMe();
                    fragment.setArguments(bundle);
                    break;
                case R.id.navigation_popular:
                    fragment = new FragmentPopular();
                    break;
                case R.id.navigation_thought:
                    fragment = new FragmentMyThought();
                    break;
                default:
                    return false;
            }
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
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

        this.artworkCollection = new ArtworkCollection();
        new ArtworkCollectionRetrievalTask(this).execute();
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
                builder.setTitle(R.string.PER_BT_NE_TITLE);
                builder.setMessage(R.string.PER_BT_NE_MSG);
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
            builder.setTitle(R.string.PER_BT_NA_TITLE);
            builder.setMessage(R.string.PER_BT_NA_MSG);
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
            this.bs = new BeaconService(this.getApplicationContext(), this);
            this.verifyBluetooth();
        } else {
            this.bs = null;
        }
    }

    public void updateImageFlow() {
        ArrayList<Artwork> artworks = this.artworkCollection.getArtworks(this.lastArea, 10);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("artworks", artworks);

        fragment = new FragmentNearMe();
        fragment.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onArtworkRetrievalTaskComplete(ArrayList<Artwork> artworks) {
        this.artworkCollection.setArtworks(artworks);
        this.requestLocationAccess();
        this.updateImageFlow();
    }

    @Override
    public void onBeaconLocationChange(String beaconId) {
        this.lastArea = 1 - this.lastArea;
        this.updateImageFlow();
    }
}
