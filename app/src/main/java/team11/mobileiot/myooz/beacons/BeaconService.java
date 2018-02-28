package team11.mobileiot.myooz.beacons;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Created by Zhongyi on 27/02/2018.
 * Beacon service.
 */

public class BeaconService implements BeaconConsumer {
    private BeaconManager beaconManager;
    private Context applicationContext;

    public BeaconService(Context context) {
        this.applicationContext = context;

        this.beaconManager = BeaconManager.getInstanceForApplication(context);
        this.beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        this.beaconManager.bind(this);
    }

    public void destroy() {
        this.beaconManager.unbind(this);
    }

    public boolean checkAvailability() {
        return this.beaconManager.checkAvailability();
    }

    @Override
    public void onBeaconServiceConnect() {
        final Region anyRegion = new Region("AllRegion", null, null, null);

        try {
            // start ranging for beacons.  This will provide an update once per second with the estimated
            // distance to the beacon in the didRAngeBeaconsInRegion method.
            beaconManager.startRangingBeaconsInRegion(anyRegion);
            beaconManager.addRangeNotifier(new RangeNotifier() {
                @Override
                public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                    if (beacons.size() > 0) {
                        Log.d("Meow", "Beacons count: " + beacons.size());
                        Log.d("Meow", "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        this.applicationContext.unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return this.applicationContext.bindService(intent, serviceConnection, i);
    }
}
