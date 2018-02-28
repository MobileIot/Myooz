package team11.mobileiot.myooz;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.Collection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<String> list = null;
    private Context context;

    public RecyclerAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_item, parent, false);
        return new RecyclerViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bindImage(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
    
    public void addAll(Collection<?extends String> collection){
        int size=list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size,collection.size());
    }
}

class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView simpleDraweeView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = itemView.findViewById(R.id.item_simpleDraweeView);
    }

    public void bindImage(String src) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float)imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(src)).build());
    }

}
