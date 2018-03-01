package team11.mobileiot.myooz.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionViewHolder> {
    private List<Comment> list = null;

    public DiscussionAdapter(List<Comment> list) {
        this.list = list;
    }

    @Override
    public DiscussionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_discussion, parent, false);
        return new DiscussionViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(DiscussionViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<?extends Comment> collection){
        int size=list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size,collection.size());
    }
}

class DiscussionViewHolder extends RecyclerView.ViewHolder {
    private TextView commentText, title,subtitle;

    public DiscussionViewHolder(final View itemView) {
        super(itemView);
        commentText = itemView.findViewById(R.id.comment_content);
        title = itemView.findViewById(R.id.comment_title);
        subtitle = itemView.findViewById(R.id.comment_subtitle);
    }

    public void bindItem(Comment src) {
        subtitle.setText(src.subtitle);
        title.setText(src.title);
        commentText.setText(src.content);
    }
}