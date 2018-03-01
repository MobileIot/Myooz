package team11.mobileiot.myooz.views;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;

public class PopularAdapter extends RecyclerView.Adapter<PopularViewHolder> {
    private List<String> list = null;
    private Context context;

    public PopularAdapter(List<String> list) {
        this.list = list;
        //this.context = context;
    }

    @Override
    public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new PopularViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(PopularViewHolder holder, int position) {
        holder.bindItem(list.get(position));
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

class PopularViewHolder extends RecyclerView.ViewHolder {
    private TextView commentText, commentLikeNum;
    private SimpleDraweeView simpleDraweeView;

    public PopularViewHolder(View itemView) {
        super(itemView);
        commentText = itemView.findViewById(R.id.comment_content);
        simpleDraweeView = itemView.findViewById(R.id.item_simpleDraweeView);
        commentLikeNum = itemView.findViewById(R.id.comment_kudos);
    }

    public void bindItem(String src) {
        commentText.setText("Some comment sentences");
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
        commentLikeNum.setText("245");
    }
}
