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
import java.util.HashMap;
import java.util.Map;

import team11.mobileiot.myooz.models.LocationChangeDelegate;

/**
 * Created by Zhongyi on 27/02/2018.
 * Beacon service.
 */

public class BeaconService implements BeaconConsumer {
    private BeaconManager beaconManager;
    private Context applicationContext;
    private String lastConfirmedBeaconId;
    private LocationChangeDelegate delegate;
    Map<String, Double> beaconsMap = new HashMap<String, Double>();


    public BeaconService(Context context, LocationChangeDelegate delegate) {
        this.applicationContext = context;

        this.beaconManager = BeaconManager.getInstanceForApplication(context);
        this.beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        this.beaconManager.bind(this);
        this.delegate = delegate;
    }

    public void destroy() {
        this.beaconManager.unbind(this);
    }

    public boolean checkAvailability() {
        return this.beaconManager.checkAvailability();
    }

    private void handleBeaconsUpdate(Collection<Beacon> beacons) {
        Log.d("Meow", "Beacons count: " + beacons.size());

        for (Beacon beacon : beacons) {
            String beaconId = beacon.getBluetoothAddress();
            Double beaconDistance = beacon.getDistance();

            if (beaconsMap.containsKey(beaconId)) {
                Double oldDistance = beaconsMap.get(beaconId);
                beaconsMap.put(beaconId, oldDistance * 0.2 + beaconDistance * 0.8);
            } else {
                beaconsMap.put(beaconId, beaconDistance);
            }
        }

        Map.Entry<String, Double> nearestBeaconEntry = null;
        for (Map.Entry<String, Double> entry : beaconsMap.entrySet()) {
            if (nearestBeaconEntry == null || nearestBeaconEntry.getValue() > entry.getValue()) {
                nearestBeaconEntry = entry;
            }
        }

        String nearestBeaconId = nearestBeaconEntry.getKey();
        double nearestBeaconDistance = nearestBeaconEntry.getValue();

        Log.d("Meow", "Nearest Beacon (" + nearestBeaconId
                + ") is " + nearestBeaconDistance + " meters away.");
        if (!nearestBeaconId.equals(this.lastConfirmedBeaconId)) {
            this.lastConfirmedBeaconId = nearestBeaconId;
            this.delegate.onBeaconLocationChange(nearestBeaconId);
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
