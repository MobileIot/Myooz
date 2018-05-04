package team11.mobileiot.myooz.views;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artwork;
import team11.mobileiot.myooz.models.NetworkTaskHandler;
import team11.mobileiot.myooz.models.Note;

/**
 * Created by flora on 5/3/18.
 */
public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteHolder> {
    private List<Note> list = null;

    public MyNoteAdapter(List<Note> list) {
        this.list = list;
    }

    @Override
    public MyNoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.mynote_item, parent, false);
        return new MyNoteHolder(iv);
    }

    @Override
    public void onBindViewHolder(MyNoteHolder holder, int position) {
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

class MyNoteHolder extends RecyclerView.ViewHolder {
    private TextView note,name;
    private SimpleDraweeView simpleDraweeView;

    public MyNoteHolder(View itemView) {
        super(itemView);
        note = itemView.findViewById(R.id.note_content);
        simpleDraweeView = itemView.findViewById(R.id.note_image);
        name=itemView.findViewById(R.id.artwork_name);
    }

    public void bindItem(Note src) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float) imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(src.avatar)).build());
        note.setText(src.content);
        Artwork.GetArtworkByID(src.artwork_id, new NetworkTaskHandler<Artwork>() {
            @Override
            public void onReady(Artwork result) {
                name.setText(result.name);
            }
        });
    }
}
