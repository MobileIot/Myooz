package team11.mobileiot.myooz.views;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;

public class FragmentArtInfo extends Fragment {

    public static FragmentArtInfo newInstance() {
        FragmentArtInfo fragment = new FragmentArtInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_art_info, container, false);
        Bundle arguments = getArguments();
        Artwork artwork = arguments.getParcelable("artwork");
        final SimpleDraweeView simpleDraweeView=v.findViewById(R.id.item_simpleDraweeView);
        TextView title= v.findViewById(R.id.info_title);
        TextView artist=v.findViewById(R.id.info_artist);
        TextView category=v.findViewById(R.id.info_category);
        TextView content=v.findViewById(R.id.info_content);
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float) imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(artwork.imageUrl)).build());

        title.setText(artwork.title);
        artist.setText(artwork.artistInfo);
        category.setText(artwork.category);
        content.setText("Magenta, Black, Green on Orange follows a compositional structure that Rothko explored for twenty–three years beginning in 1947. Narrowly separated, rectangular blocks of color hover in a column against a colored ground. Their edges are soft and irregular, so that when Rothko uses closely related tones, the rectangles sometimes seem barely to coalesce out of the ground, concentrations of its substance. The green bar in Magenta, Black, Green on Orange, on the other hand, appears to vibrate against the orange around it, creating an optical flicker. In fact the canvas is full of gentle movement, as blocks emerge and recede, and surfaces breathe. Just as edges tend to fade and blur, colors are never completely flat, and the faint unevenness in their intensity, besides hinting at the artist's process in layering wash on wash, mobilizes an ambiguity, a shifting between solidity and impalpable depth.");
        return v;
    }
}
