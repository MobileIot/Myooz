package team11.mobileiot.myooz.models;

import java.util.ArrayList;

/**
 * Created by Zhongyi on 28/02/2018.
 */

public interface LocationChangeDelegate {
    public void onBeaconLocationChange(String beaconId);
}
