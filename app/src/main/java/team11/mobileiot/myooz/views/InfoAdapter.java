package team11.mobileiot.myooz.views;

import android.content.Intent;
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
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.Note;

public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder> {
    private List<Note> list = null;

    public InfoAdapter(List<Note> list) {
        this.list = list;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_card, parent, false);
        return new InfoViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.bindImage(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<? extends Note> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size, collection.size());
    }
}

class InfoViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView simpleDraweeView;

    public InfoViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = itemView.findViewById(R.id.info_card_pic);

    }

    public void bindImage(final Note note) {
        String src = note.avatar;
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                //float ratio = imageInfo.getWidth() / (float) imageInfo.getHeight();
                //simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(src)).build());
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(simpleDraweeView.getContext(), NoteActivity.class);
                intent.putExtra("note", note);
                simpleDraweeView.getContext().startActivity(intent);
            }
        });
    }
}
