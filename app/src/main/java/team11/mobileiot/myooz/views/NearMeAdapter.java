package team11.mobileiot.myooz.views;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;

public class NearMeAdapter extends RecyclerView.Adapter<NearMeViewHolder> {
    private List<Artwork> list = null;

    public NearMeAdapter(List<Artwork> list) {
        this.list = list;
    }

    @Override
    public NearMeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_item, parent, false);
        return new NearMeViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(NearMeViewHolder holder, int position) {
        holder.bindImage(list.get(position).imageUrl);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<? extends Artwork> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size, collection.size());
    }
}

class NearMeViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView simpleDraweeView;

    public NearMeViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = itemView.findViewById(R.id.item_simpleDraweeView);

    }

    public void bindImage(String src) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float) imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(src)).build());
    }

    public void onClick(View view) {

    }
}
