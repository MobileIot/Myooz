package team11.mobileiot.myooz.beacons;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
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
    private String lastConfirmedBeaconId;
    private int confirmationCount = 0;
    public static final int MIN_CONFIRMATION_COUNT = 1;

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

    private void handleBeaconsUpdate(Collection<Beacon> beacons) {
        Log.d("Meow", "Beacons count: " + beacons.size());

        double minDistance = Integer.MAX_VALUE;
        Beacon nearestBeacon = null;

        for (Beacon beacon : beacons) {
            double distance = beacon.getDistance();
            if (distance < minDistance) {
                minDistance = distance;
                nearestBeacon = beacon;
            }
        }

        if (nearestBeacon == null) return;

        String beaconId = nearestBeacon.getBluetoothAddress();
        if (nearestBeacon.getDistance() > 3) return;
        if (beaconId.equals(this.lastConfirmedBeaconId)) {
            this.confirmationCount += 1;
            if (this.confirmationCount == MIN_CONFIRMATION_COUNT) {
                // Confident about an nearest beacon update
                Log.d("Meow", "Nearest Beacon (" + nearestBeacon.getBluetoothAddress()
                        + ") is " + nearestBeacon.getDistance() + " meters away.");
//                MainActivity.updateImageFlow();
            }
        } else {
            this.lastConfirmedBeaconId = beaconId;
            this.confirmationCount = 0;
        }
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
                    if (beacons.size() == 0) return;

                    handleBeaconsUpdate(beacons);
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
