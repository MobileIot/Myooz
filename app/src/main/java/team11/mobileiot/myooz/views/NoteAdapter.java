package team11.mobileiot.myooz.views;

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

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> list = null;

    public NoteAdapter(List<Note> list) {
        this.list = list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<?extends Note> collection){
        int size=list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size,collection.size());
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView commentText,numkudos,time;
    private SimpleDraweeView simpleDraweeView;

    public NoteViewHolder(View itemView) {
        super(itemView);
        commentText = itemView.findViewById(R.id.comment_content);
        simpleDraweeView = itemView.findViewById(R.id.comment_pic);
        numkudos=itemView.findViewById(R.id.kudosnumber);
        time=itemView.findViewById(R.id.time);
    }

    public void bindItem(Note src) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float)imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(src.url)).build());
        numkudos.setText(src.numkudos);
        time.setText(src.time);
        commentText.setText(src.content);
    }
}