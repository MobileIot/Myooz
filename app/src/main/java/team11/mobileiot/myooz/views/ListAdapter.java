package team11.mobileiot.myooz.views;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artist;

/**
 * Created by flora on 5/2/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder>{
    private List<Artist> list = null;

    public ListAdapter(List<Artist> list) {
        this.list = list;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_card, parent, false);
        return new ListViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bindImage(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<? extends Artist> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size, collection.size());
    }
}


class ListViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView imageView;
    private TextView author;

    public ListViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.artist_image);
        author=itemView.findViewById(R.id.artist_name);

    }

    public void bindImage(final Artist artist) {
        imageView.setImageURI(artist.avatar);
        author.setText(artist.name);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(imageView.getContext(), ArtistNoteActivity.class);
                intent.putExtra("artist", artist);
                MainActivity.needBeaconUpdate = false;
                imageView.getContext().startActivity(intent);
            }
        });

    }
}
