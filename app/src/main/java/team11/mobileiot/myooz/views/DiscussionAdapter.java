package team11.mobileiot.myooz.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionViewHolder> {
    private List<Note> list = null;

    public DiscussionAdapter(List<Note> list) {
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

    public void addAll(Collection<?extends Note> collection){
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

    public void bindItem(Note src) {
        commentText.setText(src.content);
    }
}