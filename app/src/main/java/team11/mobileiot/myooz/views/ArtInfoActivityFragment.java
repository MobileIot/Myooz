package team11.mobileiot.myooz.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team11.mobileiot.myooz.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtInfoActivityFragment extends Fragment {

    public ArtInfoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_art_info, container, false);
    }
}
